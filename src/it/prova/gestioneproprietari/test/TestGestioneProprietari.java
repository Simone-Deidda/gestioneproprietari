package it.prova.gestioneproprietari.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneproprietari.dao.EntityManagerUtil;
import it.prova.gestioneproprietari.model.Automobile;
import it.prova.gestioneproprietari.model.Proprietario;
import it.prova.gestioneproprietari.service.MyServiceFactory;
import it.prova.gestioneproprietari.service.automobile.AutomobileService;
import it.prova.gestioneproprietari.service.proprietario.ProprietarioService;

public class TestGestioneProprietari {

	public static void main(String[] args) {
		ProprietarioService proprietarioService = MyServiceFactory.getProprietarioServiceInstance();
		AutomobileService automobileService = MyServiceFactory.getAutomobileServiceInstance();

		try {

			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testInserimentoProprietario(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testInserimentoAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testCercaProprietarioPerId(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testCercaAutomobilePerId(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testRimozioneProprietario(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testRimozioneAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testModificaProprietario(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testModificaAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testContaQuantiProprietariConAutomobileImmatricolataDa(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testRicercaQuanteAutomobiliDatoInizialeCodiceFiscaleProprietario(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");
			
			testRicercaQuanteAutomobiliConErroriDiEtaProprietari(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	public static void testInserimentoProprietario(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testInserimentoProprietario: INIZIO >>>>>>>>");

		Proprietario nuovoProprietario = new Proprietario("Marco", "Marco", "MMMRRR10C99I",
				new SimpleDateFormat("dd-MM-yyyy").parse("10-03-1999"));

		proprietarioService.inserisciNuovo(nuovoProprietario);

		if (nuovoProprietario.getId() == null) {
			throw new RuntimeException("testInserimentoProprietario fallito.");
		}

		System.out.println("<<<<<<<< testInserimentoProprietario: FINE >>>>>>>>\n");
	}

	public static void testInserimentoAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testInserimentoAutomobile: INIZIO >>>>>>>>");

		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testInserimentoAutomobile fallito: non ci sono proprietari a cui collegarci.");

		Automobile nuovaAutomobile = new Automobile("Lancia", "Y", "RR789UY", 2015);
		nuovaAutomobile.setProprietario(listaProprietariPresenti.get(0));

		automobileService.inserisciNuovo(nuovaAutomobile);

		if (nuovaAutomobile.getId() == null) {
			throw new RuntimeException("testInserimentoAutomobile fallito.");
		}
		if (nuovaAutomobile.getProprietario() == null) {
			throw new RuntimeException("testInserimentoAutomobile fallito: non ha collegato il Proprietario");
		}

		System.out.println("<<<<<<<< testInserimentoProprietario: FINE >>>>>>>>\n");
	}

	public static void testRimozioneProprietario(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testRimozioneProprietario: INIZIO >>>>>>>>");

		Proprietario nuovoProprietario = new Proprietario("Ale", "Ale", "AAALLL20D99I",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-04-1999"));

		proprietarioService.inserisciNuovo(nuovoProprietario);
		Long id = nuovoProprietario.getId();

		proprietarioService.rimuovi(nuovoProprietario);
		if (proprietarioService.caricaSingoloProprietario(id) != null) {
			throw new RuntimeException("testRimozioneProprietario fallito: record non cancellato ");
		}

		System.out.println("<<<<<<<< testRimozioneProprietario: FINE >>>>>>>>\n");
	}

	public static void testRimozioneAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testRimozioneAutomobile: INIZIO >>>>>>>>");

		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testRimozioneAutomobile fallito: non ci sono proprietari a cui collegarci.");

		Automobile nuovaAutomobile = new Automobile("Mercedes", "Benz", "RR789UY", 2020);
		nuovaAutomobile.setProprietario(listaProprietariPresenti.get(0));

		automobileService.inserisciNuovo(nuovaAutomobile);
		Long id = nuovaAutomobile.getId();
		automobileService.rimuovi(nuovaAutomobile);

		if (automobileService.caricaSingolaAutomobile(id) != null) {
			throw new RuntimeException("testRimozioneAutomobile fallito: record non cancellato ");
		}

		System.out.println("<<<<<<<< testRimozioneAutomobile: FINE >>>>>>>>\n");
	}

	public static void testCercaProprietarioPerId(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testCercaProprietarioPerId: INIZIO >>>>>>>>");

		Proprietario nuovoProprietario = new Proprietario("Pino", "Pino", "PPPNNN11C98I",
				new SimpleDateFormat("dd-MM-yyyy").parse("11-03-1998"));
		proprietarioService.inserisciNuovo(nuovoProprietario);

		if (!proprietarioService.caricaSingoloProprietario(nuovoProprietario.getId()).equals(nuovoProprietario)) {
			throw new RuntimeException("testCercaProprietarioPerId fallito.");
		}

		System.out.println("<<<<<<<< testCercaProprietarioPerId: FINE >>>>>>>>\n");
	}

	public static void testCercaAutomobilePerId(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testCercaAutomobilePerId: INIZIO >>>>>>>>");

		Automobile nuovaAutomobile = new Automobile("Fiat", "500", "JU258ED", 1960);

		automobileService.inserisciNuovo(nuovaAutomobile);
		if (!automobileService.caricaSingolaAutomobile(nuovaAutomobile.getId()).equals(nuovaAutomobile)) {
			throw new RuntimeException("testCercaAutomobilePerId fallito.");
		}

		System.out.println("<<<<<<<< testCercaAutomobilePerId: FINE >>>>>>>>\n");
	}

	public static void testModificaProprietario(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testModificaProprietario: INIZIO >>>>>>>>");

		List<Proprietario> listaProprietari = proprietarioService.listAllProprietari();
		if (listaProprietari.isEmpty())
			throw new RuntimeException("testModificaProprietario fallito: non ci sono proprietari nel DB.");

		String nuovoNome = "nuovonome";
		Proprietario primoProprietario = listaProprietari.get(0);
		primoProprietario.setNome(nuovoNome);

		proprietarioService.aggiorna(primoProprietario);
		if (!proprietarioService.caricaSingoloProprietario(primoProprietario.getId()).getNome().equals(nuovoNome)) {
			throw new RuntimeException("testModificaProprietario fallito.");
		}

		System.out.println("<<<<<<<< testModificaProprietario: FINE >>>>>>>>\n");
	}

	public static void testModificaAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testModificaAutomobile: INIZIO >>>>>>>>");

		List<Automobile> listaAutomobili = automobileService.listAllAutomobili();
		if (listaAutomobili.isEmpty())
			throw new RuntimeException("testModificaAutomobile fallito: non ci sono automobili nel DB.");

		String nuovaMarca = "nuovamarca";
		Automobile primaAutomobile = listaAutomobili.get(0);
		primaAutomobile.setMarca(nuovaMarca);

		automobileService.aggiorna(primaAutomobile);
		if (!automobileService.caricaSingolaAutomobile(primaAutomobile.getId()).getMarca().equals(nuovaMarca)) {
			throw new RuntimeException("testModificaAutomobile fallito.");
		}

		System.out.println("<<<<<<<< testCercaAutomobilePerId: FINE >>>>>>>>\n");
	}

	public static void testContaQuantiProprietariConAutomobileImmatricolataDa(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println("\n<<<<<<<< testContaProprietarioConAutomobileImmatricolataDa: INIZIO >>>>>>>>");

		List<Proprietario> listaProprietari = proprietarioService.listAllProprietari();
		if (listaProprietari.isEmpty())
			throw new RuntimeException(
					"testContaProprietarioConAutomobileImmatricolataDa fallito: non ci sono proprietari a cui collegarci.");

		Integer dataImmatricolazioneMinima = 2017;
		Proprietario primoProprietario = listaProprietari.get(0);
		Proprietario secondoProprietario = listaProprietari.get(1);

		Automobile nuovAutomobile = new Automobile("marca10", "modello10", "AA111AA", 2022);
		nuovAutomobile.setProprietario(primoProprietario);
		automobileService.inserisciNuovo(nuovAutomobile);
		Automobile nuovAutomobile2 = new Automobile("marca11", "modello11", "BB222BB", 2020);
		nuovAutomobile2.setProprietario(secondoProprietario);
		automobileService.inserisciNuovo(nuovAutomobile2);

		Integer totaleProprietariTrovati = proprietarioService
				.contaQuantiProprietariConAutomobileImmatricolataDa(dataImmatricolazioneMinima);

		if (totaleProprietariTrovati < 2) {
			throw new RuntimeException(
					"testContaProprietarioConAutomobileImmatricolataDa fallito: numero della ricerca non corretto.");
		}

		automobileService.rimuovi(nuovAutomobile);
		automobileService.rimuovi(nuovAutomobile2);

		System.out.println("\n<<<<<<<< testContaProprietarioConAutomobileImmatricolataDa: FINE >>>>>>>>");
	}

	public static void testRicercaQuanteAutomobiliDatoInizialeCodiceFiscaleProprietario(
			ProprietarioService proprietarioService, AutomobileService automobileService) throws Exception {

		String inizialeCodiceFiscale = "AB";
		Proprietario nuovoProprietario = new Proprietario("nome100", "cognome100", inizialeCodiceFiscale + "C14A14",
				new Date());
		Proprietario nuovoProprietario2 = new Proprietario("nome101", "cognome101", inizialeCodiceFiscale + "Z15B14",
				new Date());

		Automobile nuovaAutomobile = new Automobile("marca100", "modello100", "QQ777QQ", 2000);
		nuovaAutomobile.setProprietario(nuovoProprietario);
		proprietarioService.inserisciNuovo(nuovoProprietario);
		automobileService.inserisciNuovo(nuovaAutomobile);

		Automobile nuovaAutomobile2 = new Automobile("marca100", "modello100", "QQ777QQ", 2000);
		nuovaAutomobile2.setProprietario(nuovoProprietario2);
		proprietarioService.inserisciNuovo(nuovoProprietario2);
		automobileService.inserisciNuovo(nuovaAutomobile2);

		List<Automobile> listaAutomobiliTrovate = automobileService
				.listAllAutomobiliConInizialeCodiceFiacaleDeiProprietari(inizialeCodiceFiscale);
		System.out.println(listaAutomobiliTrovate.size());
		if (listaAutomobiliTrovate.size() < 2) {
			throw new RuntimeException(
					"testRicercaQuanteAutomobiliDatoInizialeCodiceFiscaleProprietario fallito: numero della ricerca non corretto.");
		}

		automobileService.rimuovi(nuovaAutomobile);
		automobileService.rimuovi(nuovaAutomobile2);
		proprietarioService.rimuovi(nuovoProprietario);
		proprietarioService.rimuovi(nuovoProprietario2);
	}

	public static void testRicercaQuanteAutomobiliConErroriDiEtaProprietari(
			ProprietarioService proprietarioService, AutomobileService automobileService) throws Exception {

		Proprietario nuovoProprietario = new Proprietario("nome1000", "cognome1000", "ZZC14A14",
				new SimpleDateFormat("dd-MM-yyyy").parse("10-03-2020"));
		Proprietario nuovoProprietario2 = new Proprietario("nome1001", "cognome1001", "PPZ15B14",
				new SimpleDateFormat("dd-MM-yyyy").parse("10-03-2018"));
		
		Automobile nuovaAutomobile = new Automobile("marca1000", "modello1000", "QQ777QQ", 2000);
		nuovaAutomobile.setProprietario(nuovoProprietario);
		proprietarioService.inserisciNuovo(nuovoProprietario);
		automobileService.inserisciNuovo(nuovaAutomobile);

		Automobile nuovaAutomobile2 = new Automobile("marca1010", "modello1010", "JJ888JJ", 2000);
		nuovaAutomobile2.setProprietario(nuovoProprietario2);
		proprietarioService.inserisciNuovo(nuovoProprietario2);
		automobileService.inserisciNuovo(nuovaAutomobile2);

		List<Automobile> listaAutomobiliTrovate = automobileService
				.automobiliConErrori(new SimpleDateFormat("dd-MM-yyyy").parse("10-03-2004"));
		System.out.println(listaAutomobiliTrovate.size());
		if (listaAutomobiliTrovate.size() < 2) {
			throw new RuntimeException(
					"testRicercaQuanteAutomobiliConErroriDiEtaProprietari fallito: numero della ricerca non corretto.");
		}

		automobileService.rimuovi(nuovaAutomobile);
		automobileService.rimuovi(nuovaAutomobile2);
		proprietarioService.rimuovi(nuovoProprietario);
		proprietarioService.rimuovi(nuovoProprietario2);
	}

}
