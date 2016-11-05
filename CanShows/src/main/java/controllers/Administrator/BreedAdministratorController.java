package controllers.Administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BreedService;
import controllers.AbstractController;
import domain.Breed;
import forms.BreedForm;

@Controller
@RequestMapping("/breed/administrator")
public class BreedAdministratorController extends AbstractController {

	public BreedAdministratorController() {
		super();
	}

	@Autowired
	private BreedService breedService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;

		BreedForm breedForm = new BreedForm();

		result = new ModelAndView("breed/administrator/create");
		result.addObject("breedForm", breedForm);

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Breed> breeds;
		breeds = breedService.findAllBreeds();
		result = new ModelAndView("breed/administrator/list");
		result.addObject("breeds", breeds);
		result.addObject("actor", "administrator/");
		result.addObject("requestURI", "breed/administrator/list.do");

		return result;

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid BreedForm breedForm,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors() || breedForm.getName() == ""
				|| breedForm.getDescription() == "") {

			String message = "request.commit.error";
			result = new ModelAndView("breed/administrator/create");

			if (breedForm.getName() == "" || breedForm.getDescription() == "") {
				message = "register.error.params";
			}

			result.addObject("breedForm", breedForm);
			result.addObject("message", message);

		} else {
			try {
				result = new ModelAndView("redirect:list.do");
				Breed breed = breedService.reconstruct(breedForm);
				breedService.save(breed);

			} catch (Throwable oops) {

				String message = "request.commit.error";

				result = new ModelAndView("breed/administrator/create");

				if (breedForm.getName() == ""
						|| breedForm.getDescription() == "") {
					message = "register.error.params";
				} else if (!breedService
						.nameBreedAvailable(breedForm.getName())) {
					message = "breed.raza.ya.registrada";
				}

				result.addObject("breedForm", breedForm);
				result.addObject("message", message);

			}
		}
		return result;
	}
}
