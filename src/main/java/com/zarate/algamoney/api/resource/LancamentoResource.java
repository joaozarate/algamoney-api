package com.zarate.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zarate.algamoney.api.event.RecursoCriadoEvent;
import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.model.Pessoa;
import com.zarate.algamoney.api.repository.LancamentoRepository;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

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
		repository.save(entity);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, entity.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
