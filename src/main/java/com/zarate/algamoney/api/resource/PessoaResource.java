package com.zarate.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zarate.algamoney.api.event.RecursoCriadoEvent;
import com.zarate.algamoney.api.model.Pessoa;
import com.zarate.algamoney.api.repository.PessoaRepository;
import com.zarate.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private PessoaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> listar() {
		return repository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarByCodigo(@PathVariable Long codigo) {

		Optional<Pessoa> pessoa = repository.findById(codigo);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa entity, HttpServletResponse response) {

		repository.save(entity);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, entity.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).build();

	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		repository.deleteById(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa ) {
		Pessoa entity = service.atualizar(codigo, pessoa);
		return ResponseEntity.ok(entity);
	}

}
