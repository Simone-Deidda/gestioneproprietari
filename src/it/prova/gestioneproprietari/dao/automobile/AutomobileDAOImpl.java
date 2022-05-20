package it.prova.gestioneproprietari.dao.automobile;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneproprietari.model.Automobile;

public class AutomobileDAOImpl implements AutomobileDAO {
	private EntityManager entityManager;

	// <<<<<<<<<< Operazioni CRUD >>>>>>>>>>

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

	// <<<<<<<<<< Operazioni di Automobile >>>>>>>>>>

	@Override
	public List<Automobile> listAllByCodiceFiscaleProprietarioStartsWith(String inizialeCodiceFiscale) {
		TypedQuery<Automobile> query = entityManager.createQuery(
				"SELECT a FROM Automobile a JOIN a.proprietario p WHERE p.codiceFiscale LIKE ?1", Automobile.class);
		return query.setParameter(1, inizialeCodiceFiscale + "%").getResultList();
	}

	@Override
	public List<Automobile> listAllByProprietariMinorenni() {
		//TypedQuery<Automobile> query = entityManager.createQuery("SELECT a FROM sAutomobile a JOIN a.proprietario p WHERE p.dataNascita > SUBDATE(NOW(), INTERVAL 18 YEAR)", Automobile.class);
		TypedQuery<Automobile> query = entityManager.createQuery("SELECT a FROM Automobile a JOIN a.proprietario p WHERE p.dataNascita >= '2004-01-01'", Automobile.class);
		return query.getResultList();
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
