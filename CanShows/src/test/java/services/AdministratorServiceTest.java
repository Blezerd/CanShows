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
import domain.Administrator;
import domain.Breed;
import domain.Dog;
import domain.Judge;
import domain.Organiser;
import domain.Request;
import forms.ActorForm;
import forms.JudgeForm;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private BreedService breedService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	// Este método comprueba que se muestran las peticiones en el sistema
	@Test
	public void testFindAllRequest() {
		authenticate("admin");
		Collection<Request> requests;
		requests = requestService.findAll();
		Assert.isTrue(requests.size() == 5);

	}

	// Este método comprueba que se muestra correctamente el mejor perro de
	// todos con sus
	// datos correspondientes
	@Test
	public void testFindBestDog() {
		authenticate("admin");
		Dog d;
		d = administratorService.findBestDog();

		Assert.isTrue(d.getWeight().equals(21));
	}

	// Este método comprueba que se muestran los jueces registrados en el
	// sistema con el
	// número de perros de los que ha rellenado sus resultados
	@Test
	public void testFindJudgesWithResults() {
		authenticate("admin");
		Collection<Judge> judges;
		judges = judgeService.findAll();

		Assert.isTrue(judges.iterator().next().getName().equals("Patricia"));
		Assert.isTrue(judges.iterator().next().getCompetitions().size() == 3);
	}

	// Este método comprueba que se muestran los organizadores registrados en el
	// sistema con el número de competiciones que organiza
	@Test
	public void testFindAllOrganiserWithCompetitions() {
		authenticate("admin");
		Collection<Organiser> organisers;
		organisers = administratorService.findOrganisersWithCompetition();
		Assert.isTrue(organisers.size() == 2);
		Assert.isTrue(organisers.iterator().next().getName().equals("Rafael"));
	}

	// Este método comprueba que el registro de un administrador con buenos
	// datos se efectua correctamente.
	@Test
	public void saveWithGoodsParam() {
		authenticate("admin");
		ActorForm administratorForm = new ActorForm();

		administratorForm.setEmail("joaquin@gmail.com");
		administratorForm.setName("Joaquin");

		administratorForm.setPhone("954357875");
		administratorForm.setSurname("Ballesteros");
		administratorForm.setIsCondition(true);
		administratorForm.setUsername("joqaa");
		administratorForm.setPassword("joqaa");
		administratorForm.setPassword2("joqaa");
		administratorForm.setNationality("Spanish");

		Administrator administrator = administratorService
				.reconstruct(administratorForm);
		administratorService.save(administrator);

		Assert.isTrue(administratorService.findAll().size() == 2);

	}

	// Este método comprueba que se registra una nueva raza en el sistema
	@Test
	public void testRegisterBreedWithGoodsParam() {
		authenticate("admin");
		Breed breed = new Breed();
		breed.setName("Raza nueva");
		breed.setDescription("Buena descripción mejor nueva");
		breedService.save(breed);
		Assert.isTrue(breedService.findAllBreeds().size() == 54);

	}

	// Negative Test

	// Este método comprueba que no se registra una nueva raza en el sistema si
	// el título está vacío.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBadRegiserBreed() {
		authenticate("admin");
		Breed breed = new Breed();
		breed.setName("");
		breed.setDescription("Buena descripción mejor nueva");
		breedService.save(breed);
		Assert.isTrue(breedService.findAllBreeds().size() == 54);
	}

	// Este método comprueba que no se registra una nueva raza en el sistema si
	// el título está vacío.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBadRegiserBreed2() {
		authenticate("admin");
		Breed breed = new Breed();
		breed.setName("Raza nuevisima");
		breed.setDescription("");
		breedService.save(breed);
		Assert.isTrue(breedService.findAllBreeds().size() == 54);
	}

	// Este método comprueba que el registro de un administrador que ya existe
	// no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveExistingAdmin() {
		authenticate("admin");
		ActorForm administratorForm = new ActorForm();

		administratorForm.setEmail("pacoleon@gmail.com");
		administratorForm.setName("Paco");

		administratorForm.setPhone("657896321");
		administratorForm.setSurname("León");
		administratorForm.setIsCondition(true);
		administratorForm.setUsername("admin");
		administratorForm.setPassword("admin");
		administratorForm.setPassword2("admin");
		administratorForm.setNationality("Spanish");

		Administrator administrator = administratorService
				.reconstruct(administratorForm);
		administratorService.save(administrator);

	}

	// Este método comprueba que el registro de un administrador que escribe mal
	// su contraseña no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadPasswordsParam() {
		authenticate("admin");
		ActorForm adminForm = new ActorForm();
		adminForm.setEmail("alexbalmar@gmail.com");
		adminForm.setName("Alejandro");
		adminForm.setPhone("954357875");
		adminForm.setSurname("Ballesteros");
		adminForm.setIsCondition(true);
		adminForm.setUsername("blezerd2");
		adminForm.setPassword("croqueta");
		adminForm.setPassword2("croqueta2");
		adminForm.setNationality("Spanish");

		Administrator administrator = administratorService
				.reconstruct(adminForm);
		administratorService.save(administrator);

	}

	// Este método comprueba que el registro de un administrador que no acepta
	// los terminos de uso no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadConditionParam() {
		authenticate("admin");
		ActorForm adminForm = new ActorForm();
		adminForm.setEmail("alexbalmar@gmail.com");
		adminForm.setName("Alejandro");
		adminForm.setPhone("954357875");
		adminForm.setSurname("Ballesteros");
		adminForm.setIsCondition(false);
		adminForm.setUsername("blezerd2");
		adminForm.setPassword("croqueta");
		adminForm.setPassword2("croqueta");
		adminForm.setNationality("Spanish");

		Administrator administrator = administratorService
				.reconstruct(adminForm);
		administratorService.save(administrator);

	}

	// Este método comprueba que el registro de un administrador nulo
	// no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveNullParam() {
		authenticate("admin");
		administratorService.save(null);

	}

	// Este método comprueba que el registro de un administrador vacio no se
	// efectua correctamente.
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void saveEmptyParam() {
		authenticate("admin");
		administratorService.save(new Administrator());

	}

	// Este método comprueba que el registro de un juez con buenos
	// datos se efectua correctamente.
	@Test
	public void saveWithGoodsParamJudge() {
		authenticate("admin");
		JudgeForm judgeForm = new JudgeForm();

		judgeForm.setEmail("joaquin@gmail.com");
		judgeForm.setName("Joaquin");

		judgeForm.setPhone("954357875");
		judgeForm.setSurname("Ballesteros");
		judgeForm.setIsCondition(true);
		judgeForm.setUsername("joqaa");
		judgeForm.setPassword("joqaa");
		judgeForm.setPassword2("joqaa");
		judgeForm.setNationality("Spanish");
		judgeForm.setJudgeNumber(7523);

		Judge judge = judgeService.reconstruct(judgeForm);
		judgeService.save(judge);

		Assert.isTrue(judgeService.findAll().size() == 3);

	}

	// Negative Test

	// Este método comprueba que el registro de un juez que ya existe
	// no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveExistingJudge() {
		authenticate("admin");
		JudgeForm judgeForm = new JudgeForm();

		judgeForm.setEmail("pacoleon@gmail.com");
		judgeForm.setName("Paco");

		judgeForm.setPhone("657896321");
		judgeForm.setSurname("León");
		judgeForm.setIsCondition(true);
		judgeForm.setUsername("judge1");
		judgeForm.setPassword("judge1");
		judgeForm.setPassword2("judge1");
		judgeForm.setNationality("Spanish");
		judgeForm.setJudgeNumber(9875);

		Judge judge = judgeService.reconstruct(judgeForm);
		judgeService.save(judge);

	}

	// Este método comprueba que el registro de un administrador que escribe mal
	// su contraseña no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadPasswordsParamJudge() {
		authenticate("admin");
		JudgeForm judgeForm = new JudgeForm();
		judgeForm.setEmail("alexbalmar@gmail.com");
		judgeForm.setName("Alejandro");
		judgeForm.setPhone("954357875");
		judgeForm.setSurname("Ballesteros");
		judgeForm.setIsCondition(true);
		judgeForm.setUsername("blezerd2");
		judgeForm.setPassword("croqueta");
		judgeForm.setPassword2("croqueta2");
		judgeForm.setNationality("Spanish");
		judgeForm.setJudgeNumber(8756);

		Judge judge = judgeService.reconstruct(judgeForm);
		judgeService.save(judge);

	}

	// Este método comprueba que el registro de un administrador que no acepta
	// los terminos de uso no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveBadConditionParamJudge() {
		authenticate("admin");
		JudgeForm judgeForm = new JudgeForm();
		judgeForm.setEmail("alexbalmar@gmail.com");
		judgeForm.setName("Alejandro");
		judgeForm.setPhone("954357875");
		judgeForm.setSurname("Ballesteros");
		judgeForm.setIsCondition(false);
		judgeForm.setUsername("blezerd2");
		judgeForm.setPassword("croqueta");
		judgeForm.setPassword2("croqueta");
		judgeForm.setNationality("Spanish");
		judgeForm.setJudgeNumber(78521);

		Judge judge = judgeService.reconstruct(judgeForm);
		judgeService.save(judge);

	}

	// Este método comprueba que el registro de un administrador nulo
	// no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveNullParamJudge() {
		authenticate("admin");
		judgeService.save(null);

	}

	// Este método comprueba que el registro de un administrador vacio no se
	// efectua correctamente.
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void saveEmptyParamJudge() {
		authenticate("admin");
		judgeService.save(new Judge());

	}
	// -------------

}
