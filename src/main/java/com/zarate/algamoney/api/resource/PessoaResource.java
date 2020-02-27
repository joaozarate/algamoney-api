package com.zarate.algamoney.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zarate.algamoney.api.model.Pessoa;
import com.zarate.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository repository;

	@GetMapping
	public List<Pessoa> listar() {
		return repository.findAll();
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarByCodigo(@PathVariable Long codigo) {

		Optional<Pessoa> pessoa = repository.findById(codigo);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();

	}

}
