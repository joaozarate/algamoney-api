package com.zarate.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import com.zarate.algamoney.api.model.Lancamento;
import com.zarate.algamoney.api.model.Lancamento_;
import com.zarate.algamoney.api.resource.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter filter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarResticoes(filter, builder, root);

		criteria.where(predicates);

		TypedQuery<Lancamento> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	private Predicate[] criarResticoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.isNotEmpty(filter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.DESCRICAO)),
					"%" + filter.getDescricao().toLowerCase() + "%"));
		}

		if (filter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), filter.getDataVencimentoDe()));
		}

		if (filter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Lancamento_.DATA_VENCIMENTO), filter.getDataVencimentoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
