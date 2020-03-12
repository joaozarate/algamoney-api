package com.zarate.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
