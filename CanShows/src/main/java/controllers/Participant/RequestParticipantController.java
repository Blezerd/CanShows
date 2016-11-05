package controllers.Participant;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.ParticipantService;
import services.RequestService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Dog;
import domain.Participant;
import domain.Request;
import forms.RequestForm;

@Controller
@RequestMapping("/request/participant/")
public class RequestParticipantController extends AbstractController {

	public RequestParticipantController() {
		super();
	}

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private RequestService requestService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Request> requests;
		Participant p = participantService.findByPrincipal();
		requests = requestService.findByParticipant(p.getId());

		result = new ModelAndView("request/participant/list");
		result.addObject("requests", requests);
		result.addObject("actor", "participant/");
		result.addObject("requestURI", "request/participant/list.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Request r = requestService.create();

		boolean puedeRegistrar = participantService.puedeRegistrar();

		RequestForm requestForm = requestService.constructCreate(r);
		result = new ModelAndView("request/participant/edit");
		result.addObject("puedeRegistrar", puedeRegistrar);
		result.addObject("requestForm", requestForm);
		result.addObject("actor", "participant/");
		Collection<Administrator> administrators = administratorService
				.findAll();
		result.addObject("administrators", administrators);
		Collection<Dog> dogs = participantService.findByPrincipal().getDogs();
		Collection<Dog> dogsAvailables = new HashSet<Dog>();
		for (Dog a : dogs) {
			if (a.getRequest() == null) {
				dogsAvailables.add(a);
			}
		}
		result.addObject("dogs", dogsAvailables);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RequestForm requestForm,
			BindingResult binding) {
		ModelAndView result;

		boolean puedeRegistrar = participantService.puedeRegistrar();

		if (binding.hasErrors()) {
			String message = "request.commit.error";
			result = new ModelAndView("request/participant/edit");
			result.addObject("requestForm", requestForm);
			result.addObject("actor", "participant/");
			if (requestForm.getAdministrator() == null
					|| requestForm.getTitle() == null
					|| requestForm.getDescription() == null) {
				message = "register.error.params";
			}
			result.addObject("modelAttribute", "requestForm");
			result.addObject("message", message);
			result.addObject("puedeRegistrar", puedeRegistrar);
			Collection<Administrator> administrators = administratorService
					.findAll();
			result.addObject("administrators", administrators);
			Collection<Dog> dogs = participantService.findByPrincipal()
					.getDogs();
			Collection<Dog> dogsAvailables = new HashSet<Dog>();
			for (Dog a : dogs) {
				if (a.getRequest() == null) {
					dogsAvailables.add(a);
				}
			}
			result.addObject("dogs", dogsAvailables);
		} else {
			try {
				Assert.notNull(requestForm);
				Request request = requestService.reconstruct(requestForm);
				requestService.save(request);
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {

				result = new ModelAndView("request/participant/edit");
				result.addObject("requestForm", requestForm);
				result.addObject("actor", "participant/");
				String message = "request.commit.error";
				if (requestForm.getAdministrator() == null
						|| requestForm.getTitle() == null
						|| requestForm.getDescription() == null) {
					message = "register.error.params";
				}
				result.addObject("message", message);
				result.addObject("modelAttribute", "requestForm");
				result.addObject("puedeRegistrar", puedeRegistrar);
				Collection<Administrator> administrators = administratorService
						.findAll();
				result.addObject("administrators", administrators);
				Collection<Dog> dogs = participantService.findByPrincipal()
						.getDogs();
				Collection<Dog> dogsAvailables = new HashSet<Dog>();
				for (Dog a : dogs) {
					if (a.getRequest() == null) {
						dogsAvailables.add(a);
					}
				}
				result.addObject("dogs", dogsAvailables);
			}

		}

		return result;
	}

}
