package services;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Administrator;
import domain.Dog;
import domain.Participant;
import domain.Request;
import forms.RequestAdminForm;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private DogService dogService;

	public Collection<Request> findAll() {
		Collection<Request> res = requestRepository.findAll();
		return res;
	}

	public Request findOneToEdit(int requestId) {
		return requestRepository.findOne(requestId);
	}

	public RequestAdminForm constructEditForm(Request request) {
		RequestAdminForm res = new RequestAdminForm();
		res.setStatus(request.getStatus());
		res.setDescription(request.getDescription());
		res.setTitle(request.getTitle());
		res.setId(request.getId());
		return res;
	}

	public void saveEdit(RequestAdminForm requestAdminForm) {

		Administrator administrator = administratorService.findByPrincipal();
		Request request = this.reconstructAdmin(requestAdminForm);

		Assert.isTrue(!request.getStatus().equals("PENDING"));

		if (request.getAdministrator().equals(administrator)) {
			request.setStatus(requestAdminForm.getStatus());
			if (request.getStatus().equals("REJECTED")) {
				Assert.isTrue(!requestAdminForm.getComment().isEmpty()
						&& !(requestAdminForm.getComment() == null));
				request.setComment(requestAdminForm.getComment());
			} else if (request.getStatus().equals("ACCEPTED")) {
				request.setComment(requestAdminForm.getComment());
			}

			this.saveEditRequest(request);

			if (request.getStatus().equals("ACCEPTED")) {
				Participant participant = request.getDog().getParticipant();
				Collection<Dog> dogs = participant.getDogs();
				for (Dog d : dogs) {
					d.setCanParticipate(true);
					dogService.updateAll(d);
				}
				participant.setDogs(dogs);
				participantService.update(participant);
			}

		} else {
			throw new IllegalArgumentException("Not Principal");
		}
	}

	public Request reconstructAdmin(RequestAdminForm requestAdminForm) {
		Request r = new Request();
		r = this.findOneToEdit(requestAdminForm.getId());

		r.setComment(requestAdminForm.getComment());
		r.setStatus(requestAdminForm.getStatus());

		return r;
	}

	public void saveEditRequest(Request request) {
		
		checkPrincipalAdmin();

		if (request.getStatus().equals("REJECTED")
				&& request.getComment().isEmpty()) {
			throw new IllegalArgumentException();
		}

		requestRepository.saveAndFlush(request);
	}

	public Collection<Request> findByParticipant(int participantId) {
		Assert.notNull(participantId);
		return requestRepository.findByParticipant(participantId);
	}

	public Request create() {
		Request r = new Request();
		return r;
	}

	public RequestForm constructCreate(Request r) {
		RequestForm res = new RequestForm();
		return res;

	}

	public Request reconstruct(RequestForm requestForm) {
		Request r = new Request();
		r.setTitle(requestForm.getTitle());
		r.setComment(requestForm.getComment());
		r.setDescription(requestForm.getDescription());
		r.setAdministrator(requestForm.getAdministrator());
		r.setDog(requestForm.getDog());
		return r;

	}

	public void save(Request request) {
		Assert.notNull(request);
		if (request.getTitle().isEmpty()) {
			throw new IllegalArgumentException("Empty");
		}
		Integer aleatorio = RandomUtils.nextInt();
		request.setCode(request.getTitle() + aleatorio.toString());
		request.setStatus("PENDING");
		request.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		request.getDog().setParticipant(participantService.findByPrincipal());
		Dog dog = dogService.findOneToShow(request.getDog().getId());
		dog.setRequest(request);
		dogService.saveAndFlush(dog);
		//requestRepository.save(request);

	}
	
	private void checkPrincipalAdmin() {
		Assert.notNull(administratorService.findByPrincipal());
		
	}

}
