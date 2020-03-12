package com.zarate.algamoney.api.repository.lancamento;

import java.util.List;

import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.resource.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	List<Lancamento> filtrar(LancamentoFilter filter);

}
