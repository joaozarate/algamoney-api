package com.zarate.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zarate.algamoney.api.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
