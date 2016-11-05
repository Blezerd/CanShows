package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.PopulateDatabase;
import domain.Competition;
import domain.Organiser;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CompetitionTestService extends AbstractTest {

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private OrganiserService organiserService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	// Este método comprueba que se muestran las competiciones disponibles
	// registradas en el
	// sistema
	@Test
	public void testFindAllCompetitions() {

		Collection<Competition> competitions;
		competitions = competitionService.findAllCompetitionsAvailables();
		Assert.isTrue(competitions.size() == 3);

	}

	// Este método comprueba que se muestran las competiciones disponibles
	// registradas en el
	// sistema
	@Test
	public void testFindAllCompetitionsAdmin() {
		authenticate("admin");
		Collection<Competition> competitions;
		competitions = competitionService.findAllCompetitionsAvailables();
		Assert.isTrue(competitions.size() == 3);

	}

	// Este método comprueba que se muestran las competiciones disponibles
	// registradas en el
	// sistema
	@Test
	public void testFindAllCompetitionsOrganiser() {
		authenticate("organiser1");
		Collection<Competition> competitions;
		competitions = competitionService.findAllCompetitionsAvailables();
		Assert.isTrue(competitions.size() == 3);

	}

	// Este método comprueba que se muestran las competiciones disponibles
	// registradas en el
	// sistema
	@Test
	public void testFindAllCompetitionsJudge() {
		authenticate("judge1");
		Collection<Competition> competitions;
		competitions = competitionService.findAllCompetitionsAvailables();
		Assert.isTrue(competitions.size() == 3);

	}

	// Este método comprueba que se muestran las competiciones disponibles
	// registradas en el
	// sistema
	@Test
	public void testFindAllCompetitionsParticipant() {
		authenticate("participant1");
		Collection<Competition> competitions;
		competitions = competitionService.findAllCompetitionsAvailables();
		Assert.isTrue(competitions.size() == 3);

	}

	// Este método comprueba que se crea una competición correctamente
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateCompetition() {

		authenticate("organiser1");

		Organiser principal = organiserService.findByPrincipal();
		Competition c = new Competition();
		Date d = new Date(216, 11, 7);
		Date d1 = new Date(215, 11, 7);

		c.setAdress("prueba1");
		c.setType("WORK");
		c.setResultsPublished(false);
		c.setFirstPrize("15");
		c.setSecondPrize("10");
		c.setThirdPrize("5");
		c.setTitle("Orubej");
		c.setInscriptionPrice(15.0);
		c.setMinimumPoints(15);
		c.setInscriptionLimitDate(d1);
		c.setCelebrationDate(d);
		c.setOrganiser(principal);
		competitionService.saveCreate(c);
		Assert.isTrue(competitionService.findAllCompetitionsAvailables().size() == 4);

	}

	// Este método comprueba que se edita una competición correctamente
	@SuppressWarnings("deprecation")
	@Test
	public void testEditCompetition() {

		authenticate("organiser1");

		Organiser principal = organiserService.findByPrincipal();
		Competition c = competitionService.findOneToShow(78);
		Date d = new Date(216, 11, 7);
		Date d1 = new Date(215, 11, 7);

		c.setAdress("prueba1");
		c.setType("WORK");
		c.setResultsPublished(false);
		c.setFirstPrize("15");
		c.setSecondPrize("10");
		c.setThirdPrize("5");
		c.setTitle("Orubej");
		c.setInscriptionPrice(15.0);
		c.setMinimumPoints(15);
		c.setInscriptionLimitDate(d1);
		c.setCelebrationDate(d);
		c.setOrganiser(principal);
		competitionService.saveEdit(c);
		Assert.isTrue(competitionService.findAllCompetitionsAvailables().size() == 3);

	}

	// Negativos

	// // Este método comprueba que no se puede editar una competición si los
	// parámetros son incorrectos
	// @Test(expected = IllegalArgumentException.class)
	// @Rollback(value = true)
	// public void testBadEditCompetition() {
	//
	// authenticate("organiser1");
	//
	// Organiser principal = organiserService.findByPrincipal();
	// Competition c = competitionService.findOneToShow(78);
	// Date d = new Date(214, 11, 7);
	// Date d1 = new Date(214, 10, 7);
	//
	// c.setAdress("prueba1");
	// c.setType("WORK");
	// c.setResultsPublished(false);
	// c.setFirstPrize("15");
	// c.setSecondPrize("10");
	// c.setThirdPrize("5");
	// c.setTitle("Orubej");
	// c.setInscriptionPrice(15.0);
	// c.setMinimumPoints(15);
	// c.setInscriptionLimitDate(d1);
	// c.setCelebrationDate(d);
	// c.setOrganiser(principal);
	// competitionService.saveEdit(c);
	// Assert.isTrue(c.getInscriptionLimitDate().equals(d1));
	//
	// }

	// Este método comprueba que no se crea una competición si los datos no son
	// correctos
	// no se seleccina el tipo
	@SuppressWarnings("deprecation")
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void testBadCreateCompetition() {

		authenticate("organiser1");

		Organiser principal = organiserService.findByPrincipal();
		Competition c = new Competition();
		Date d = new Date(216, 11, 7);
		Date d1 = new Date(215, 11, 7);

		c.setAdress("prueba1");

		c.setResultsPublished(false);
		c.setFirstPrize("15");
		c.setSecondPrize("10");
		c.setThirdPrize("5");
		c.setTitle("Orubej");
		c.setInscriptionPrice(15.0);
		c.setMinimumPoints(15);
		c.setInscriptionLimitDate(d1);
		c.setCelebrationDate(d);
		c.setOrganiser(principal);
		competitionService.saveCreate(c);
		Assert.isTrue(competitionService.findAllCompetitionsAvailables().size() == 4);

	}

	// Este método comprueba que no se crea una competición si los datos no son
	// correctos
	// no se seleccina el tipo
	@SuppressWarnings("deprecation")
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void testBad2CreateCompetition() {

		authenticate("organiser1");

		Organiser principal = organiserService.findByPrincipal();
		Competition c = new Competition();
		Date d = new Date(216, 11, 7);
		Date d1 = new Date(215, 11, 7);

		c.setAdress("");
		c.setType("WORK");
		c.setResultsPublished(false);
		c.setFirstPrize("15");
		c.setSecondPrize("10");
		c.setThirdPrize("5");
		c.setTitle("Orubej");
		c.setInscriptionPrice(15.0);
		c.setMinimumPoints(15);
		c.setInscriptionLimitDate(d1);
		c.setCelebrationDate(d);
		c.setOrganiser(principal);
		competitionService.saveCreate(c);
		Assert.isTrue(competitionService.findAllCompetitionsAvailables().size() == 4);

	}

	// Este método comprueba que salta la excepción al guardar una competición
	// como
	// nulo
	@Test(expected = NullPointerException.class)
	@Rollback(value = true)
	public void saveNullParam() {

		competitionService.saveCreate(null);

	}

	// Este método comprueba que salta la excepción al dejar los parámetros
	// vacíos
	@Test(expected = NullPointerException.class)
	@Rollback(value = true)
	public void saveEmptyParam() {

		competitionService.saveCreate(new Competition());

	}
}
