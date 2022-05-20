package it.prova.gestioneproprietari.dao.proprietario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneproprietari.model.Proprietario;

public class ProprietarioDAOImpl implements ProprietarioDAO {
	private EntityManager entityManager;

	// <<<<<<<<<< Operazioni CRUD >>>>>>>>>>

	@Override
	public List<Proprietario> list() throws Exception {
		return entityManager.createQuery("FROM Proprietario", Proprietario.class).getResultList();
	}

	@Override
	public Proprietario get(Long id) throws Exception {
		return entityManager.find(Proprietario.class, id);
	}

	@Override
	public void update(Proprietario o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}

		o = entityManager.merge(o);
	}

	@Override
	public void insert(Proprietario o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(o);
	}

	@Override
	public void delete(Proprietario o) throws Exception {
		if (o == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.remove(entityManager.merge(o));
	}

	// <<<<<<<<<< Operazioni di Proprietario >>>>>>>>>>

	@Override
	public Integer countAllWithDataImmatricolazioneAutomobileGreaterThen(Integer dataImmatricolazione) {
		TypedQuery<Proprietario> query = entityManager.createQuery(
				"SELECT distinct p FROM Proprietario p JOIN p.automobili a WHERE a.annoImmatricolazione > ?1",
				Proprietario.class);
		return query.setParameter(1, dataImmatricolazione).getResultList().size();
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
