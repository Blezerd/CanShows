package services;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.PopulateDatabase;
import domain.Competition;
import domain.CreditCard;
import domain.Groups;
import domain.Judge;
import domain.Organiser;
import forms.CustomerForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class OrganiserServiceTest extends AbstractTest {

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private BreedService breedService;

	@Autowired
	private CompetitionService competitionService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	// Este método comprueba que se muestran los organizadores registrados en el
	// sistema
	@Test
	public void testFindAllOrganisers() {

		Collection<Organiser> organisers;
		organisers = organiserService.findAll();
		Assert.isTrue(organisers.size() == 2);

	}

	// Este método comprueba que se puede añadir un juez a un competición
	@Test
	public void testAddJudgeCompetition() {
		authenticate("organiser1");
		Competition c = competitionService.findOneToShow(78);
		Judge j = judgeService.findOne(96);
		Collection<Judge> judges = new HashSet<Judge>();
		judges.add(j);

		c.setJudges(judges);
		competitionService.saveJudge(c);
		Assert.isTrue(c.getJudges().size() == 1);

	}

	// Este método comprueba que se muestran los organizadores registrados en el
	// sistema
	@Test
	public void testFindAllOrganisersJudge() {
		authenticate("judge1");
		Collection<Organiser> organisers;
		organisers = organiserService.findAll();
		Assert.isTrue(organisers.size() == 2);

	}

	// Este método comprueba que se muestran los organizadores registrados en el
	// sistema
	@Test
	public void testFindAllOrganisersOrganiser() {
		authenticate("organiser1");

		Collection<Organiser> organisers;
		organisers = organiserService.findAll();
		Assert.isTrue(organisers.size() == 2);

	}

	// Este método comprueba que se muestran los organizadores registrados en el
	// sistema
	@Test
	public void testFindAllOrganisersParticipant() {
		authenticate("participant1");

		Collection<Organiser> organisers;
		organisers = organiserService.findAll();
		Assert.isTrue(organisers.size() == 2);

	}

	// Este método comprueba que se muestran los organizadores registrados en el
	// sistema
	@Test
	public void testFindAllOrganisersAdmin() {
		authenticate("admin");

		Collection<Organiser> organisers;
		organisers = organiserService.findAll();
		Assert.isTrue(organisers.size() == 2);

	}

	// Este método comprueba que se borra la competición si no tiene perros
	// apuntados en ella
	@Test
	public void testDeleteCompetition() {
		authenticate("organiser1");

		Competition c = competitionService.findOneToShow(78);
		competitionService.delete(c.getId());

		Assert.isTrue(competitionService.findAllCompetitionsAvailables().size() == 2);

	}

//	// Este método comprueba que se crea un grupo en la competición
//	@Test
//	public void testCreateGroup() {
//		authenticate("organiser1");
//
//		Competition c = competitionService.findOneToShow(78);
//		Groups g = new Groups();
//		Collection<Groups> groupsc = new HashSet<Groups>();
//
//		g.setBreed(breedService.findOne(14));
//		g.setCompetition(c);
//		g.setRing(88);
//		g.setNumberOfDogs(120);
//		
//		groupsc.add(g);
//
//		competitionService.saveGroup(c);
//
//		Assert.isTrue(groupsc.size() == 5);
//
//	}

	// Este método comprueba que un organizador se registra correctamente si los
	// si los datos son correctos
	@Test
	public void saveWithGoodsParam() {
		CustomerForm organiserForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		organiserForm.setCreditCard(creditCard);
		organiserForm.setEmail("alexbalmar@gmail.com");
		organiserForm.setName("Alejandro");
		organiserForm.setNationality("Narnia");
		organiserForm.setPhone("954357875");
		organiserForm.setSurname("Ballesteros");
		organiserForm.setIsCondition(true);
		organiserForm.setUsername("blezerd");
		organiserForm.setPassword("croqueta");
		organiserForm.setPassword2("croqueta");

		Organiser organiser = organiserService.reconstruct(organiserForm);
		organiserService.save(organiser);

		Assert.isTrue(organiserService.findAll().size() == 3);

	}

	// TEST NEGATIVOS

	// Este método comprueba que un organizador no se puede registrar
	// correctamente
	// si ya existe otro con ese mismo nombre de usuario
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveExistingOrganiser() {
		CustomerForm organiserForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		organiserForm.setCreditCard(creditCard);
		organiserForm.setEmail("alexbalmar@gmail.com");
		organiserForm.setName("Alejandro");
		organiserForm.setNationality("Narnia");
		organiserForm.setPhone("954357875");
		organiserForm.setSurname("Ballesteros");
		organiserForm.setIsCondition(true);
		organiserForm.setUsername("organiser1");
		organiserForm.setPassword("croqueta");
		organiserForm.setPassword2("croqueta");

		Organiser organiser = organiserService.reconstruct(organiserForm);
		organiserService.save(organiser);

	}

	// Este método comprueba que no se crea un grupo en la competición
	// si los datos no son correctos
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBadCreateGroup() {
		authenticate("organiser1");

		Competition c = competitionService.findOneToShow(78);
		Groups g = new Groups();
		Collection<Groups> groupsc = new HashSet<Groups>();

		g.setBreed(breedService.findOne(16));
		g.setCompetition(c);
		//g.setRing(88);
		g.setNumberOfDogs(120);
		groupsc.add(g);

		competitionService.saveGroup(c);

		Assert.isTrue(c.getGroups().size() == 2);

	}

	// Este método comprueba que se borra la competición si no tiene perros
	// apuntados en ella
	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(value = true)
	public void testBadDeleteCompetition() {
		authenticate("organiser1");

		Competition c = competitionService.findOneToShow(74);
		competitionService.delete(c.getId());

		Assert.isTrue(competitionService.findAllCompetitionsAvailables().size() == 2);

	}

	// Este método comprueba que un organizador no se puede registrar si el año
	// de
	// su tarjeta de crédito no es válido
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadCreditCardParam() {
		CustomerForm organiserForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2013);
		creditCard.setCvv(989);
		organiserForm.setCreditCard(creditCard);
		organiserForm.setEmail("alexbalmar@gmail.com");
		organiserForm.setName("Alejandro");
		organiserForm.setNationality("Narnia");
		organiserForm.setPhone("954357875");
		organiserForm.setSurname("Ballesteros");
		organiserForm.setIsCondition(true);
		organiserForm.setUsername("blezerd1");
		organiserForm.setPassword("croqueta");
		organiserForm.setPassword2("croqueta");

		Organiser organiser = organiserService.reconstruct(organiserForm);
		organiserService.save(organiser);

	}

	// Este método comprueba que un organizador no se puede registrar si los dos
	// campos
	// de la contraseña no concuerdan
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadPasswordsParam() {
		CustomerForm organiserForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		organiserForm.setCreditCard(creditCard);
		organiserForm.setEmail("alexbalmar@gmail.com");
		organiserForm.setName("Alejandro");
		organiserForm.setNationality("Narnia");
		organiserForm.setPhone("954357875");
		organiserForm.setSurname("Ballesteros");
		organiserForm.setIsCondition(true);
		organiserForm.setUsername("blezerd2");
		organiserForm.setPassword("croqueta");
		organiserForm.setPassword2("croqueta2");

		Organiser organiser = organiserService.reconstruct(organiserForm);
		organiserService.save(organiser);

	}

	// Este método comprueba que un organizador no se puede registrar no ha
	// aceptado
	// los términos y condiciones
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadConditionParam() {
		CustomerForm organiserForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		organiserForm.setCreditCard(creditCard);
		organiserForm.setEmail("alexbalmar@gmail.com");
		organiserForm.setName("Alejandro");
		organiserForm.setNationality("Narnia");
		organiserForm.setPhone("954357875");
		organiserForm.setSurname("Ballesteros");
		organiserForm.setIsCondition(false);
		organiserForm.setUsername("blezerd3");
		organiserForm.setPassword("croqueta");
		organiserForm.setPassword2("croqueta");

		Organiser organiser = organiserService.reconstruct(organiserForm);
		organiserService.save(organiser);

	}

	// Este método comprueba que salta la excepción al guardar un autor como
	// nulo
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveNullParam() {

		organiserService.save(null);

	}

	// Este método comprueba que salta la excepción al dejar los parámetros
	// vacíos
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void saveEmptyParam() {

		organiserService.save(new Organiser());

	}

}
