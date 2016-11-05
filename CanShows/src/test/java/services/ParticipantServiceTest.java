package services;

import java.util.Collection;

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
import domain.CreditCard;
import domain.Dog;
import domain.Groups;
import domain.Message;
import domain.Participant;
import forms.CustomerForm;
import forms.MessageForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ParticipantServiceTest extends AbstractTest {

	@Autowired
	private DogService dogService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private MessageService messageService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	// Este método comprueba que se envia un mensaje a un usuario
	@Test
	public void testSendMessage() {
		authenticate("participant1");

		Participant receiver = participantService.findOne(10);
		MessageForm messageForm = new MessageForm();
		messageForm.setParticipant(receiver);
		messageForm.setBody("mensaje");
		messageForm.setSubject("subject");
		Message m = messageService.reconstruct(messageForm);
		messageService.save(m);

	}

	// Este método comprueba que se inscribe correctamente a un perro a un
	// concurso
	@Test
	public void testInscribeDog() {
		authenticate("participant1");
		Dog d = dogService.findOneToShow(84);
		Competition c = competitionService.findOneToShow(78);
		Groups a = null;
		for (Groups b : c.getGroups()) {
			if (b.getBreed().equals(d.getBreed())) {
				a = b;
			}
		}
		competitionService.joinDogToGroupAndCompetition(c.getId(), a, d);
		Assert.isTrue(a.getDogs().size() > 0);

	}

	// Este método comprueba que se elimina a un perro si no tiene aceptada la
	// petición
	@Test
	public void testDeleteDog() {
		authenticate("participant1");

		Participant principal = participantService.findByPrincipal();
		Dog d = dogService.findOneToShow(92);
		dogService.delete(d.getId());
		Assert.isTrue(dogService.findAllDogsFromParticipantById(
				principal.getId()).size() == 3);

	}

	// Este método comprueba que se deja de seguir a un amigo
	@Test
	public void testUnFollowFriend() {
		authenticate("participant1");

		Participant principal = participantService.findByPrincipal();
		Participant p = participantService.findOne(10);
		participantService.deleteFriend(p);
		Assert.isTrue(principal.getFriends().size() == 0);

	}

	// Este método comprueba que se empieza a seguir a un amigo
	@Test
	public void testFollowFriend() {
		authenticate("participant2");

		Participant principal = participantService.findByPrincipal();
		Participant p = participantService.findOne(9);
		participantService.addFriend(p);
		Assert.isTrue(principal.getFriends().size() == 1);

	}

	// Este método comprueba que se lista la lista de amigos de un participante
	@Test
	public void testListFriends() {
		authenticate("participant1");

		Participant principal = participantService.findByPrincipal();
		Assert.isTrue(principal.getFriends().size() == 1);

	}

	// Este método comprueba que se devuelven los perros del participante
	// , y se pueden consultar los resultados
	@Test
	public void testFindHistoricOwnDogs() {
		authenticate("participant1");
		Collection<Dog> dogs;
		Participant p = participantService.findByPrincipal();
		dogs = dogService.findAllDogsFromParticipantById(p.getId());
		Assert.isTrue(dogs.size() == 4);
		Assert.isTrue(dogs.iterator().next().getResults().iterator().next()
				.getPosition().equals(1));
	}

	// Este método comprueba que se devuelven los perros del participante
	// , y se puede consultar la línea de sangre
	@Test
	public void testFindBloodOwnDogs() {
		authenticate("participant1");
		Collection<Dog> dogs;
		Participant p = participantService.findByPrincipal();
		dogs = dogService.findAllDogsFromParticipantById(p.getId());

		Assert.isTrue(dogs.size() == 4);
		for (Dog d : dogs) {
			if (d.getNickname().equals("Croque"))
				Assert.isTrue(d.getFather().getNickname().equals("Paul"));
		}

	}

	// Este método comprueba que se devuelve el participante que contiene la
	// cadena
	// que se ha escrito
	@Test
	public void testSearchParticipant() {
		authenticate("participant1");
		Collection<Participant> participants;
		String text = "jul";
		participants = participantService.getAllParticipants(text);
		Assert.isTrue(participants.size() == 1);
	}

	// Este método comprueba que se devuelve el perro que contiene la
	// cadena
	// que se ha escrito
	@Test
	public void testSearchDog() {
		authenticate("participant1");
		Collection<Dog> dogs;
		String text = "pau";
		dogs = dogService.getAllDogs(text);
		Assert.isTrue(dogs.size() == 1);
	}

	// Este método comprueba que un organizador se registra correctamente si los
	@Test
	public void saveWithGoodsParam() {
		CustomerForm participantForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		participantForm.setCreditCard(creditCard);
		participantForm.setEmail("alexbalmar@gmail.com");
		participantForm.setName("Alejandro");
		participantForm.setNationality("Narnia");
		participantForm.setPhone("954357875");
		participantForm.setSurname("Ballesteros");
		participantForm.setIsCondition(true);
		participantForm.setUsername("blezerd");
		participantForm.setPassword("croqueta");
		participantForm.setPassword2("croqueta");

		Participant participant = participantService
				.reconstruct(participantForm);
		participantService.save(participant);

		Assert.isTrue(participantService.findAll().size() == 3);

	}

	// TEST NEGATIVOS

	// Este método comprueba que no se puede eliminar un perro que tiene
	// la petición aceptada
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBadDeleteDog() {
		authenticate("participant1");

		Participant principal = participantService.findByPrincipal();
		Dog d = dogService.findOneToShow(84);
		dogService.delete(d.getId());
		Assert.isTrue(dogService.findAllDogsFromParticipantById(
				principal.getId()).size() == 2);

	}

	// Este método comprueba que un organizador no se puede registrar
	// correctamente
	// si ya existe otro con ese mismo nombre de usuario
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveExistingParticipant() {
		CustomerForm participantForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		participantForm.setCreditCard(creditCard);
		participantForm.setEmail("alexbalmar@gmail.com");
		participantForm.setName("Alejandro");
		participantForm.setNationality("Narnia");
		participantForm.setPhone("954357875");
		participantForm.setSurname("Ballesteros");
		participantForm.setIsCondition(true);
		participantForm.setUsername("participant1");
		participantForm.setPassword("croqueta");
		participantForm.setPassword2("croqueta");

		Participant participant = participantService
				.reconstruct(participantForm);
		participantService.save(participant);

	}

	// Este método comprueba que un organizador no se puede registrar si el año
	// de
	// su tarjeta de crédito no es válido
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadCreditCardParam() {
		CustomerForm participantForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2013);
		creditCard.setCvv(989);
		participantForm.setCreditCard(creditCard);
		participantForm.setEmail("alexbalmar@gmail.com");
		participantForm.setName("Alejandro");
		participantForm.setNationality("Narnia");
		participantForm.setPhone("954357875");
		participantForm.setSurname("Ballesteros");
		participantForm.setIsCondition(true);
		participantForm.setUsername("blezerd1");
		participantForm.setPassword("croqueta");
		participantForm.setPassword2("croqueta");

		Participant participant = participantService
				.reconstruct(participantForm);
		participantService.save(participant);

	}

	// Este método comprueba que un organizador no se puede registrar si los dos
	// campos
	// de la contraseña no concuerdan
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadPasswordsParam() {
		CustomerForm participantForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		participantForm.setCreditCard(creditCard);
		participantForm.setEmail("alexbalmar@gmail.com");
		participantForm.setName("Alejandro");
		participantForm.setNationality("Narnia");
		participantForm.setPhone("954357875");
		participantForm.setSurname("Ballesteros");
		participantForm.setIsCondition(true);
		participantForm.setUsername("blezerd2");
		participantForm.setPassword("croqueta");
		participantForm.setPassword2("croqueta2");

		Participant participant = participantService
				.reconstruct(participantForm);
		participantService.save(participant);

	}

	// Este método comprueba que un organizador no se puede registrar no ha
	// aceptado
	// los términos y condiciones
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadConditionParam() {
		CustomerForm participantForm = new CustomerForm();

		CreditCard creditCard = new CreditCard();
		creditCard.setBrandName("Alejandro");
		creditCard.setHolderName("BBVA");
		creditCard.setNumber("5540500001000004");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2015);
		creditCard.setCvv(989);
		participantForm.setCreditCard(creditCard);
		participantForm.setEmail("alexbalmar@gmail.com");
		participantForm.setName("Alejandro");
		participantForm.setNationality("Narnia");
		participantForm.setPhone("954357875");
		participantForm.setSurname("Ballesteros");
		participantForm.setIsCondition(false);
		participantForm.setUsername("blezerd3");
		participantForm.setPassword("croqueta");
		participantForm.setPassword2("croqueta");

		Participant participant = participantService
				.reconstruct(participantForm);
		participantService.save(participant);

	}

	// Este método comprueba que salta la excepción al guardar un participante
	// como
	// nulo
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveNullParam() {

		participantService.save(null);

	}

	// Este método comprueba que salta la excepción al dejar los parámetros
	// vacíos
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void saveEmptyParam() {

		participantService.save(new Participant());

	}

	// Este método comprueba que no se inscribe correctamente a un perro a un
	// concurso
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testInscribeDogBad() {
		authenticate("participant1");
		Dog d = dogService.findOneToShow(84);
		Competition c = competitionService.findOneToShow(78);
		Groups a = null;
		for (Groups b : c.getGroups()) {
			if (b.getBreed().equals(d.getBreed())) {
				a = b;
			}
		}
		competitionService.joinDogToGroupAndCompetition(null, a, d);
		Assert.isTrue(a.getDogs().size() > 0);

	}

	// Este método comprueba que no se envia un mensaje a un usuario
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testSendMessageBad() {
		authenticate("participant1");

		Participant receiver = participantService.findOne(10);
		MessageForm messageForm = new MessageForm();
		messageForm.setParticipant(receiver);
		messageForm.setBody("");
		messageForm.setSubject("");
		Message m = messageService.reconstruct(messageForm);
		messageService.save(m);

	}

}
