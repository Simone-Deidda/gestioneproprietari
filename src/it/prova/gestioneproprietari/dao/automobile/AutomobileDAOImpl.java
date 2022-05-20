package it.prova.gestioneproprietari.dao.automobile;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneproprietari.model.Automobile;

public class AutomobileDAOImpl implements AutomobileDAO {
	private EntityManager entityManager;

	@Override
	public List<Automobile> list() throws Exception {
		return entityManager.createQuery("FROM Automobile", Automobile.class).getResultList();
	}

	@Override
	public Automobile get(Long id) throws Exception {
		return entityManager.find(Automobile.class, id);
	}

	@Override
	public void update(Automobile o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}

		o = entityManager.merge(o);
	}

	@Override
	public void insert(Automobile o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(o);
	}

	@Override
	public void delete(Automobile o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.remove(entityManager.merge(o));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
