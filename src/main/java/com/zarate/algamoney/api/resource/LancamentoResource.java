package com.zarate.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zarate.algamoney.api.event.RecursoCriadoEvent;
import com.zarate.algamoney.api.exception.PessoaInexistenteInativaException;
import com.zarate.algamoney.api.handler.AlgamoneyExceptionHandler.Erro;
import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.model.Pessoa;
import com.zarate.algamoney.api.repository.LancamentoRepository;
import com.zarate.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private LancamentoService service;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource message;

	@GetMapping
	public List<Lancamento> listar() {
		return repository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarByCodigo(@PathVariable Long codigo) {
		Optional<Lancamento> lancamento = repository.findById(codigo);
		return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Lancamento entity, HttpServletResponse response) {
		service.salvar(entity);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, entity.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@ExceptionHandler({PessoaInexistenteInativaException.class})
	public ResponseEntity<Object> handlePessoaInexistenteInativaException(PessoaInexistenteInativaException ex) {
		String userMessage = message.getMessage("lancamento.pessoa.inexistente-ou-invalida", null, LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(userMessage, ex.toString()));
		return ResponseEntity.badRequest().body(erros);
	}

}
