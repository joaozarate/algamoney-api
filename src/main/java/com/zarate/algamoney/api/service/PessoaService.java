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
		Optional<Pessoa> entity = repository.findById(codigo);

		if (entity.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}

		BeanUtils.copyProperties(pessoa, entity.get(), "codigo");
		return repository.save(entity.get());
	}

}
