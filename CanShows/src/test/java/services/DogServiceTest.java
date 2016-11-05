package services;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.PopulateDatabase;
import domain.Breed;
import domain.Dog;
import domain.Participant;
import forms.AddFOMForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DogServiceTest extends AbstractTest {

	@Autowired
	private DogService dogService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private BreedService breedService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	// Este método comprueba que se registra/crea un perro un perro
	@Test
	public void testRegisterDog() {

		authenticate("participant1");

		Participant principal = participantService.findByPrincipal();
		Dog d = new Dog();
		Breed b = breedService.findOne(13);
		d.setBreed(b);
		d.setName("Perrito");
		d.setNickname("perritin");
		d.setHeight(22.0);
		d.setWeight(35);
		d.setSex("MALE");
		d.setParticipant(principal);
		d.setTotalPoints(0);
		d.setCanParticipate(false);
		dogService.save(d);
		Assert.isTrue(dogService.findAllDogsFromParticipantById(
				principal.getId()).size() == 5);

	}

	// Este método comprueba que se muestran los perros registrados del
	// participante

	@Test
	public void testFindAllDogsByParticipant() {
		authenticate("participant1");
		Collection<Dog> dogs;
		Participant p = participantService.findByPrincipal();
		dogs = dogService.findAllDogsFromParticipantById(p.getId());
		Assert.isTrue(dogs.size() == 4);

	}

	// Este método comprueba que se muestran los perros registrados en el
	// sistema ordenados por puntos
	@Test
	public void testFindAllDogsByPoints() {

		Collection<Dog> dogs;
		dogs = dogService.findDogWithPoints();
		Assert.isTrue(dogs.iterator().next().getTotalPoints() == 20);

	}

	// Este método comprueba que se muestran los perros registrados en el
	// sistema ordenados por puntos
	@Test
	public void testFindAllDogsByPointsAdmin() {
		authenticate("admin");
		Collection<Dog> dogs;
		dogs = dogService.findDogWithPoints();
		Assert.isTrue(dogs.iterator().next().getTotalPoints() == 20);

	}

	// Este método comprueba que se muestran los perros registrados en el
	// sistema ordenados por puntos
	@Test
	public void testFindAllDogsByPointsParticipant() {
		authenticate("participant1");
		Collection<Dog> dogs;
		dogs = dogService.findDogWithPoints();
		Assert.isTrue(dogs.iterator().next().getTotalPoints() == 20);

	}

	// Este método comprueba que se muestran los perros registrados en el
	// sistema ordenados por puntos
	@Test
	public void testFindAllDogsByPointsOrganiser() {
		authenticate("organiser1");
		Collection<Dog> dogs;
		dogs = dogService.findDogWithPoints();
		Assert.isTrue(dogs.iterator().next().getTotalPoints() == 20);

	}

	// Este método comprueba que se elimina a un perro si no participa en ningua
	// competición
	@Test
	public void testDeleteDog() {
		authenticate("participant1");

		Participant principal = participantService.findByPrincipal();
		Dog d = dogService.findOneToShow(92);
		dogService.delete(d.getId());
		Assert.isTrue(dogService.findAllDogsFromParticipantById(
				principal.getId()).size() == 3);

	}

	// Este método comprueba que se muestran los perros registrados en el
	// sistema ordenados por puntos
	@Test
	public void testFindAllDogsByPointsJudge() {
		authenticate("judge1");
		Collection<Dog> dogs;
		dogs = dogService.findDogWithPoints();
		Assert.isTrue(dogs.iterator().next().getTotalPoints() == 20);

	}

	// Este método comprueba que se devuelve el perro que contiene la cadena
	// que se ha escrito
	@Test
	public void testSearchDogAdmin() {
		authenticate("admin");
		Collection<Dog> dogs;
		String text = "pau";
		dogs = dogService.getAllDogs(text);
		Assert.isTrue(dogs.size() == 1);
	}

	// Este método comprueba que se devuelve el perro que contiene la cadena
	// que se ha escrito
	@Test
	public void testSearchDogParticipant() {
		authenticate("participant1");
		Collection<Dog> dogs;
		String text = "pau";
		dogs = dogService.getAllDogs(text);
		Assert.isTrue(dogs.size() == 1);
	}

	// Este método comprueba que se devuelve el perro que contiene la cadena
	// que se ha escrito
	@Test
	public void testSearchDogOrganiser() {
		authenticate("organiser1");
		Collection<Dog> dogs;
		String text = "pau";
		dogs = dogService.getAllDogs(text);
		Assert.isTrue(dogs.size() == 1);
	}

	// Este método comprueba que se devuelve el perro que contiene la cadena
	// que se ha escrito
	@Test
	public void testSearchDogJudge() {
		authenticate("judge1");
		Collection<Dog> dogs;
		String text = "pau";
		dogs = dogService.getAllDogs(text);
		Assert.isTrue(dogs.size() == 1);
	}

	// Este método comprueba que se devuelve el perro que contiene la cadena
	// que se ha escrito
	@Test
	public void testSearchDog() {

		Collection<Dog> dogs;
		String text = "pau";
		dogs = dogService.getAllDogs(text);
		Assert.isTrue(dogs.size() == 1);
	}

	// Este método comprueba que se añade un padre al perro.
	@Test
	public void testAddFather() {
		Dog hijo = dogService.findOneToShow(92);
		Dog padre = dogService.findOneToShow(84);
		AddFOMForm addFomForm = dogService.constructAddParentForm(hijo.getId());
		addFomForm.setBoolMadre(false);
		addFomForm.setParent(padre);
		dogService.reconstructAddParent(addFomForm);
		dogService.saveAndFlush(hijo);
		Assert.isTrue(hijo.getFather().equals(padre));
	}

	// Este método comprueba que se añade un padre al perro.
	@Test
	public void testAddMother() {
		Dog hijo = dogService.findOneToShow(92);
		Dog padre = dogService.findOneToShow(88);
		AddFOMForm addFomForm = dogService.constructAddParentForm(hijo.getId());
		addFomForm.setBoolMadre(true);
		addFomForm.setParent(padre);
		dogService.reconstructAddParent(addFomForm);
		dogService.saveAndFlush(hijo);
		Assert.isTrue(hijo.getMother().equals(padre));
	}

	// Este método comprueba que no se añade un padre al perro.
	@Test(expected = InvalidDataAccessApiUsageException.class)
	@Rollback(value = true)
	public void testAddFatherBad() {
		Dog hijo = dogService.findOneToShow(92);

		AddFOMForm addFomForm = dogService.constructAddParentForm(hijo.getId());
		addFomForm.setBoolMadre(false);
		addFomForm.setParent(new Dog());
		dogService.reconstructAddParent(addFomForm);
		dogService.saveAndFlush(hijo);
	}

	// Este método comprueba que no se añade un padre al perro.
	@Test(expected = InvalidDataAccessApiUsageException.class)
	@Rollback(value = true)
	public void testAddMotherBad() {
		Dog hijo = dogService.findOneToShow(92);
		AddFOMForm addFomForm = dogService.constructAddParentForm(hijo.getId());
		addFomForm.setBoolMadre(true);
		addFomForm.setParent(new Dog());
		dogService.reconstructAddParent(addFomForm);
		dogService.saveAndFlush(hijo);
	}
}
