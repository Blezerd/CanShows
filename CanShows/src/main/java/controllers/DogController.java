package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccount;
import services.DogService;
import services.ParticipantService;
import domain.Dog;
import domain.Participant;

@Controller
@RequestMapping("/dog")
public class DogController extends AbstractController {

	public DogController() {
		super();
	}

	@Autowired
	private DogService dogService;

	@Autowired
	private ParticipantService participantService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Dog> dogs = new HashSet<Dog>();
		dogs = dogService.findDogWithPoints();
		result = new ModelAndView("dog/list");
		result.addObject("dogs", dogs);

		Participant p = null;
		if (SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().iterator().next().getAuthority()
				.contains("PARTICIPANT")) {
			UserAccount user = (UserAccount) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			for (Authority a : user.getAuthorities()) {
				if (a.getAuthority().equals(Authority.PARTICIPANT)) {
					p = participantService.findByPrincipal();
				}
			}
		}
		result.addObject("participant", p);
		result.addObject("requestURI", "dog/list.do");
		return result;

	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int dogId) {

		ModelAndView result;
		Dog dog = dogService.findOneToShow(dogId);

		result = new ModelAndView("dog/details");
		result.addObject("dog", dog);
		result.addObject("results", dog.getResults());
		result.addObject("requestURI", "dog/details.do");
		return result;

	}
}
