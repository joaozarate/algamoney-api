package com.zarate.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.zarate.algamoney.api.model.Pessoa;
import com.zarate.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa entity = buscarPessoaByCodigo(codigo);
		BeanUtils.copyProperties(pessoa, entity, "codigo");
		return repository.save(entity);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {

		Pessoa entity = buscarPessoaByCodigo(codigo);
		entity.getEndereco().setAtivo(ativo);
		repository.save(entity);

	}

	private Pessoa buscarPessoaByCodigo(Long codigo) {
		Optional<Pessoa> entity = repository.findById(codigo);

		if (entity.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return entity.get();
	}

}
