package com.zarate.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zarate.algamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
