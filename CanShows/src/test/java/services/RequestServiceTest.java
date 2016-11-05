package services;

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
import domain.Dog;
import domain.Participant;
import domain.Request;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RequestServiceTest extends AbstractTest {

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private DogService dogService;

	// Este método comprueba que se guarda correctamente un cambio en el status
	// de la petición
	// al ser manejada por el administrador
	@Test
	public void testManageRequestRejected() {
		authenticate("admin");
		Request r;
		r = requestService.findOneToEdit(93);
		r.setStatus("REJECTED");
		r.setComment("Comentario");
		requestService.saveEditRequest(r);

		Assert.isTrue(requestService.findOneToEdit(93).getStatus()
				.equals("REJECTED"));

	}

	// Este método comprueba que se guarda correctamente un cambio en el status
	// de la petición
	// al ser manejada por el administrador
	@Test
	public void testManageRequestAccepted() {
		authenticate("admin");
		Request r;
		r = requestService.findOneToEdit(93);
		r.setStatus("ACCEPTED");
		r.setComment("Comentario");
		requestService.saveEditRequest(r);

		Assert.isTrue(requestService.findOneToEdit(93).getStatus()
				.equals("ACCEPTED"));

	}

	// Este método comprueba que se crea una petición correctamente
	@Test
	public void testCreateWithGoodsParam() {
		authenticate("participant1");
		Administrator admin = administratorService.findOne(8);
		Participant p = participantService.findByPrincipal();
		Request request = new Request();
		Dog dog = dogService.findOneToShow(94);
		request.setTitle("Perro");
		request.setDescription("Descripción 1");
		request.setAdministrator(admin);
		request.setDog(dog);
		requestService.save(request);
		Assert.isTrue(requestService.findByParticipant(p.getId()).size() == 4);

	}

	// Negativos

	// Este método comprueba que no se puede modificar el status de una petición
	// aceptada
	@Test(expected = NullPointerException.class)
	@Rollback(value = true)
	public void testBadManageRequestAccepted() {
		authenticate("admin");
		Request r;
		r = requestService.findOneToEdit(42);
		r.setStatus("ACCEPTED");

		requestService.saveEditRequest(r);

		Assert.isTrue(requestService.findOneToEdit(42).getStatus()
				.equals("ACCEPTED"));

	}

	// Este método comprueba que no se puede modificar el status de una petición
	// no aceptada
	@Test(expected = NullPointerException.class)
	@Rollback(value = true)
	public void testBadManageRequestRejected() {
		authenticate("admin");
		Request r;
		r = requestService.findOneToEdit(42);
		r.setStatus("REJECTED");

		requestService.saveEditRequest(r);

		Assert.isTrue(requestService.findOneToEdit(42).getStatus()
				.equals("REJECTED"));

	}

	// Este método comprueba que se no se crea una petición si los datos no son
	// correctos
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void testBadCreateWithGoodsParam() {
		authenticate("participant1");
		Administrator admin = administratorService.findOne(8);
		Participant p = participantService.findByPrincipal();
		Request request = new Request();
		Dog dog = dogService.findOneToShow(94);
		request.setTitle("");
		request.setDescription("Descripción 1");
		request.setAdministrator(admin);
		request.setDog(dog);
		requestService.save(request);
		Assert.isTrue(requestService.findByParticipant(p.getId()).size() == 4);

	}

	// Este método comprueba que se no se crea una petición si los datos no son
	// correctos (no se selecciona administrador)
	@Test(expected = ConstraintViolationException.class)
	@Rollback(value = true)
	public void testBad2CreateWithGoodsParam() {
		authenticate("participant1");
		Participant p = participantService.findByPrincipal();
		Request request = new Request();
		Dog dog = dogService.findOneToShow(94);
		request.setTitle("Perro");
		request.setDescription("Descripción 1");

		request.setDog(dog);
		requestService.save(request);
		Assert.isTrue(requestService.findByParticipant(p.getId()).size() == 4);

	}

	// Este método comprueba que el registro de una petición nulo
	// no se efectua correctamente.
	@Test(expected = IllegalArgumentException.class)
	@Rollback(value = true)
	public void saveNullParamRequest() {
		authenticate("participant1");
		requestService.save(null);

	}

	// Este método comprueba que el registro de una petición vacía no se
	// efectua correctamente.
	@Test(expected = NullPointerException.class)
	@Rollback(value = true)
	public void saveEmptyParamRequest() {
		authenticate("participant1");
		requestService.save(new Request());

	}

}
