package com.planner.empresarial.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.planner.empresarial.model.Cargo;
import com.planner.empresarial.model.Categoria;
import com.planner.empresarial.repository.filter.CargoFilter;
import com.planner.empresarial.service.NegocioException;
import com.planner.empresarial.util.jpa.Transactional;

public class Cargos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cargo guardar(Cargo cargo) {
		return manager.merge(cargo);
	}

	@Transactional
	public void remover(Cargo cargo) {
		try {
			cargo = porId(cargo.getId());
			manager.remove(cargo);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}

	public Cargo porId(Long id) {
		return this.manager.find(Cargo.class, id);
	}

	public Cargo findDescricao(String descricao) {
		try {
			
			return this.manager.createQuery("from Cargo where upper(descricao)"
					+ " like :descricao",Cargo.class)
					.setParameter("descricao", descricao.toUpperCase())
					.getSingleResult();
			
		} catch (NoResultException nre) {
			return null;
		}
		
	}
	
	

	public List<Cargo> porDescricao(String descricao) {
		return this.manager.createQuery("from Cargo " + "where upper(nome) like :nome", Cargo.class)
				.setParameter("descricao", descricao.toUpperCase() + "%").getResultList();
	}
	
	public List<Cargo> listOfCargos() {
		return manager.createQuery("from Cargo where descricao != null", 
				Cargo.class).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<Cargo> filtrados(CargoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cargo.class);

		if ((!(null == filtro.getId()))) {
			criteria.add(Restrictions.eq("id", filtro.getId()));
		}
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("descricao")).list();
	}
	
	public List<Cargo> cadastrados() {
				
		return this.manager.createQuery("from Cargo", Cargo.class)
				.getResultList();
		
	}

}