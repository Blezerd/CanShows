package controllers.Participant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParticipantService;
import controllers.AbstractController;
import domain.Participant;

@Controller
@RequestMapping("/participant/participant/")
public class ParticipantParticipantController extends AbstractController {

	@Autowired
	private ParticipantService participantService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Participant yo = participantService.findByPrincipal();
		Collection<Participant> friends = yo.getFriends();

		result = new ModelAndView("participant/participant/list");
		result.addObject("actor", "participant/");
		result.addObject("requestURI", "participant/participant/list.do");
		result.addObject("friends", friends);
		result.addObject("yo", yo);
		return result;
	}

	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView follow(@RequestParam int participantId,
			@RequestParam String text) {
		ModelAndView result;
		try {
			Assert.notNull(participantId);

			// Aqui se añade a la lista de amigos
			Participant newFriend = participantService.findOne(participantId);
			participantService.addFriend(newFriend);

			Participant p = participantService.findByPrincipal();
			result = new ModelAndView("participant/participant/list");
			result.addObject("participant", p);

		} catch (Throwable oops) {
			Participant p = participantService.findByPrincipal();
			result = new ModelAndView("participant/participant/list");
			result.addObject("participant", p);
		}

		return result;
	}

	@RequestMapping(value = "/unFollow", method = RequestMethod.GET)
	public ModelAndView unFollow(@RequestParam int participantId) {
		ModelAndView result;
		try {
			Assert.notNull(participantId);

			// Aqui se añade a la lista de amigos
			Participant newFriend = participantService.findOne(participantId);
			participantService.deleteFriend(newFriend);

			Participant p = participantService.findByPrincipal();
			result = new ModelAndView("participant/participant/list");
			result.addObject("participant", p);

		} catch (Throwable oops) {
			Participant p = participantService.findByPrincipal();
			result = new ModelAndView("participant/participant/list");
			result.addObject("participant", p);
		}

		return result;
	}

}
