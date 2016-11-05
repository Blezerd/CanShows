package controllers.Participant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ResultService;
import controllers.AbstractController;
import domain.Result;


@Controller
@RequestMapping("/result/participant/")
public class ResultParticipantController extends AbstractController {

	public ResultParticipantController() {
		super();
	}

	@Autowired
	private ResultService resultService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int dogId) {
		ModelAndView result;

		Collection<Result> results = resultService
				.findAllFromParticipantDog(dogId);
		result = new ModelAndView("result/participant/list");
		result.addObject("actor", "participant/");
		result.addObject("friend", "");
		result.addObject("requestURI", "result/participant/list.do");
		result.addObject("results", results);
		result.addObject("dogId", dogId);
		return result;
	}
	
	// List
		@RequestMapping(value = "/listFriend", method = RequestMethod.GET)
		public ModelAndView listFriend(@RequestParam int dogId) {
			ModelAndView result;

			Collection<Result> results = resultService
					.findResultsForFriendDog(dogId);
			result = new ModelAndView("result/participant/listFriend");
			result.addObject("actor", "participant/");
			result.addObject("friend", "Friend");
			result.addObject("requestURI", "result/participant/listFriend.do");
			result.addObject("results", results);
			result.addObject("dogId", dogId);
			return result;
		}

}
