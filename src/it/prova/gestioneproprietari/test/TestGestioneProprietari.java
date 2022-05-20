package it.prova.gestioneproprietari.test;

import java.text.SimpleDateFormat;
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

			// testInserimentoProprietario(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			// testInserimentoAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testRimozioneProprietario(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testRimozioneAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testCercaProprietarioPerId(proprietarioService, automobileService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testCercaAutomobilePerId(proprietarioService, automobileService);
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
		System.out.println(nuovoProprietario);
		System.out.println(proprietarioService.caricaSingoloProprietario(nuovoProprietario.getId()));

		if (!proprietarioService.caricaSingoloProprietario(nuovoProprietario.getId()).equals(nuovoProprietario)) {
			throw new RuntimeException("testCercaProprietarioPerId fallito.");
		}

		proprietarioService.rimuovi(nuovoProprietario);

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

		automobileService.rimuovi(nuovaAutomobile);

		System.out.println("<<<<<<<< testCercaAutomobilePerId: FINE >>>>>>>>\n");
	}

}
