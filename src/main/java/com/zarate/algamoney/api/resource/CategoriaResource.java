package com.zarate.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zarate.algamoney.api.model.Categoria;
import com.zarate.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	private static final String LOCATION = "Location";
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

}
