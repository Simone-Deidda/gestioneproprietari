package it.prova.gestioneproprietari.service.automobile;

import java.util.Date;
import java.util.List;

import it.prova.gestioneproprietari.dao.automobile.AutomobileDAO;
import it.prova.gestioneproprietari.model.Automobile;

public interface AutomobileService {
	// operazioni CRUD
	public List<Automobile> listAllAutomobili() throws Exception;

	public Automobile caricaSingolaAutomobile(Long id) throws Exception;

	public void aggiorna(Automobile automobileInstance) throws Exception;

	public void inserisciNuovo(Automobile automobileInstance) throws Exception;

	public void rimuovi(Automobile automobileInstance) throws Exception;
	
	// operazioni di Automobile
	public List<Automobile> listAllAutomobiliConInizialeCodiceFiacaleDeiProprietari(String inizialeCodiceFiscale) throws Exception;
	
	public List<Automobile> automobiliConErrori(); // proprietari minorenni

	//per injection
	public void setAutomobileDAO(AutomobileDAO automobileDAO);

}
