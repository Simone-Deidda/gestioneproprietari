package it.prova.gestioneproprietari.dao.proprietario;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneproprietari.model.Proprietario;

public class ProprietarioDAOImpl implements ProprietarioDAO {
	private EntityManager entityManager;
	
	@Override
	public List<Proprietario> list() throws Exception {
		return entityManager.createQuery("FROM Proprietario", Proprietario.class).getResultList();
	}

	@Override
	public Proprietario get(Long id) throws Exception {
		return null;
	}

	@Override
	public void update(Proprietario o) throws Exception {
	}

	@Override
	public void insert(Proprietario o) throws Exception {
	}

	@Override
	public void delete(Proprietario o) throws Exception {
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
