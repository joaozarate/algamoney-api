package com.zarate.algamoney.api.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.resource.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);

}
