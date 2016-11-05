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

import services.ParticipantService;
import controllers.AbstractController;
import domain.Participant;
import forms.SearchForm;

@Controller
@RequestMapping("/search/participant/")
public class SearchParticipantController extends AbstractController {

	@Autowired
	private ParticipantService participantService;

	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public ModelAndView buscar() {
		ModelAndView result;
		SearchForm searchForm = new SearchForm();
		result = new ModelAndView("search/participant/buscar");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/buscar", method = RequestMethod.POST, params = "search")
	public ModelAndView buscar(@Valid SearchForm searchForm,
			BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {

			result = new ModelAndView("search/participant/buscar");
			result.addObject("searchForm", searchForm);

		} else {
			try {
				Assert.notNull(searchForm);
				String text = searchForm.getText();
				Collection<Participant> participants = new HashSet<Participant>();
				participants = participantService.getAllParticipants(text);
				Participant yo = participantService.findByPrincipal();
				result = new ModelAndView("search/participant/buscar");
				result.addObject("participants", participants);
				result.addObject("searchForm", searchForm);
				result.addObject("text", text);
				result.addObject("yo", yo);

			} catch (Throwable oops) {
				System.out.println(oops.getLocalizedMessage());
				result = new ModelAndView("search/participant/buscar");
				result.addObject("searchForm", searchForm);

			}
		}
		return result;
	}

}
