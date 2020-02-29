package com.zarate.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zarate.algamoney.api.event.RecursoCriadoEvent;
import com.zarate.algamoney.api.model.Categoria;
import com.zarate.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar() {
		return repository.findAll();
	}

	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria entity, HttpServletResponse response) {

		Categoria categoriaCriada = repository.save(entity);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaCriada.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);

	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarByCodigo(@PathVariable Long codigo) {
//		return repository.findById(codigo).map(
//				categoria -> ResponseEntity.ok(categoria)).orElse(ResponseEntity.notFound().build());
		Optional<Categoria> categoria = repository.findById(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long codigo) {
		repository.deleteById(codigo);
	}

}
