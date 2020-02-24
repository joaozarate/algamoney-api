package com.zarate.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zarate.algamoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
