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

import com.planner.empresarial.model.Funcionario;
import com.planner.empresarial.repository.filter.FuncionarioFilter;
import com.planner.empresarial.service.NegocioException;
import com.planner.empresarial.util.jpa.Transactional;

public class Funcionarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Funcionario guardar(Funcionario funcionario) {
		return manager.merge(funcionario);
	}

	@Transactional
	public void remover(Funcionario funcionario) {
		try {
			funcionario = porId(funcionario.getId());
			manager.remove(funcionario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Cliente não pode ser excluído.");
		}
	}

	public Funcionario porId(Long id) {
		return this.manager.find(Funcionario.class, id);
	}

	public Funcionario findDescricao(String nome) {
		try {

			return this.manager
					.createQuery("from Funcionario where upper(descricao)" + " like :descricao", Funcionario.class)
					.setParameter("descricao", nome.toUpperCase()).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

	}

	public Funcionario findMatricula(String matricula) {
		try {

			return this.manager
					.createQuery("from Funcionario where upper(matricula)" + " like :matricula", Funcionario.class)
					.setParameter("matricula", matricula.toUpperCase()).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

	}

	public List<Funcionario> porNome(String nome) {
		return this.manager.createQuery("from Funcionario " + "where upper(nome) like :nome", Funcionario.class)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> filtrados(FuncionarioFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Funcionario.class);

		if ((!(null == filtro.getId()))) {
			criteria.add(Restrictions.eq("id", filtro.getId()));
		}
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if ((!(null == filtro.getCargo()))) {
			criteria.add(Restrictions.eq("cargo", filtro.getCargo()));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

	public List<Funcionario> listOfFuncionarios() {

		return manager.createQuery("from Funcionario where nome != null", 
					Funcionario.class).getResultList();

	}

}