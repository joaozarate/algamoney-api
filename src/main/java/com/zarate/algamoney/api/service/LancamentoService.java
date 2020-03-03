package com.zarate.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zarate.algamoney.api.exception.PessoaInexistenteInativaException;
import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.model.Pessoa;
import com.zarate.algamoney.api.repository.LancamentoRepository;
import com.zarate.algamoney.api.repository.PessoaRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());

		if (pessoa.isPresent() && !pessoa.get().getAtivo()) {
			throw new PessoaInexistenteInativaException();
		}

		return repository.save(lancamento);
	}

}
