package com.zarate.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zarate.algamoney.api.model.Categoria;
import com.zarate.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

//	private static final String LOCATION = "Location";

	@Autowired
	private CategoriaRepository repository;

	@GetMapping
	public List<Categoria> listar() {
		return repository.findAll();
	}

	@PostMapping
//	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(@RequestBody Categoria entity, HttpServletResponse response) {

		Categoria categoriaCriada = repository.save(entity);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(entity.getCodigo()).toUri();
//		response.setHeader(LOCATION, uri.toASCIIString());

		return ResponseEntity.created(uri).body(categoriaCriada);

	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarByCodigo(@PathVariable Long codigo) {
//		return repository.findById(codigo).map(
//				categoria -> ResponseEntity.ok(categoria)).orElse(ResponseEntity.notFound().build());
		Optional<Categoria> categoria = repository.findById(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}

}
