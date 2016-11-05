package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BreedService;
import domain.Breed;
import domain.Request;

@Controller
@RequestMapping("/breed/")
public class BreedController extends AbstractController {

	public BreedController() {
		super();
	}

	@Autowired
	private BreedService breedService;

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int breedId) {

		ModelAndView result;
		Breed breed = breedService.findOneToDetails(breedId);

		result = new ModelAndView("breed/details");
		result.addObject("breed", breed);
		result.addObject("dogs", breed.getDogs());
		result.addObject("requestURI", "breed/details.do");
		return result;

	}

	
}
