package controllers.Participant;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionService;
import services.DogService;
import services.ParticipantService;
import controllers.AbstractController;
import domain.Competition;
import domain.Dog;
import domain.Groups;
import domain.Participant;
import forms.CompetitionDogForm;
import forms.CompetitionGroupForm;

@Controller
@RequestMapping("/competition/participant/")
public class CompetitionParticipantController extends AbstractController {

	public CompetitionParticipantController() {
		super();
	}

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private DogService dogService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Participant p = participantService.findByPrincipal();
		Collection<Competition> competitions = competitionService.findAllCompetitionsFromParticipantById(p.getId());
		result = new ModelAndView("competition/participant/list");
		result.addObject("actor", "participant/");
		result.addObject("participant", p);
		result.addObject("requestURI", "competition/participant/list.do");
		result.addObject("competitions", competitions);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		result.addObject("now", now);
		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int competitionId) {

		ModelAndView result;
		Competition c = competitionService.findOneToShow(competitionId);

		result = new ModelAndView("competition/participant/details");
		result.addObject("competition", c);
		result.addObject("actor", "participant/");
		result.addObject("judges", c.getJudges());
		result.addObject("groups", c.getGroups());
		result.addObject("results", c.getResults());
		Collection<Dog> dogs = new HashSet<Dog>();
		for (Groups g : c.getGroups()) {
			dogs.addAll(g.getDogs());
		}
		result.addObject("dogs", dogs);
		result.addObject("calle", "\"" + c.getAdress() + "\"");
		result.addObject("requestURI", "competition/participant/details.do");
		return result;
	}

	// Join competition

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView join(@RequestParam int competitionId) {
		ModelAndView result;
		Competition c = competitionService.findOneToShow(competitionId);
		CompetitionGroupForm competitionGroupForm = competitionService.constructCompGroupForm(competitionId);
		result = new ModelAndView("competition/participant/join");
		result.addObject("competitionGroupForm", competitionGroupForm);
		result.addObject("groups", c.getGroups());
		return result;
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CompetitionGroupForm competitionGroupForm, BindingResult binding) {
		ModelAndView result;
		boolean lleno = competitionGroupForm.getGrupo().getNumberOfDogs()
				.equals(competitionGroupForm.getGrupo().getDogs().size());
		if (binding.hasErrors() || competitionGroupForm.getGrupo() == null) {
			String message = "request.commit.error";
			Competition c = competitionService.findOneToShow(competitionGroupForm.getCompId());
			result = new ModelAndView("competition/participant/join");
			result.addObject("competitionGroupForm", competitionGroupForm);
			result.addObject("groups", c.getGroups());
			if (competitionGroupForm.getGrupo() == null) {
				message = "register.error.params";
			}
			result.addObject("message", message);
			result.addObject("lleno", lleno);
		} else {
			try {

				CompetitionDogForm competitionDogForm = competitionService.constructCompGroupForm(competitionGroupForm);
				result = new ModelAndView("competition/participant/joinCompetition");
				result.addObject("competitionDogForm", competitionDogForm);
				Groups grupo = competitionGroupForm.getGrupo();
				Collection<Dog> dogs = dogService.findAllDogsFromBreedAndParticipant(grupo.getBreed());
				Collection<Dog> dogsAvailables = new HashSet<Dog>();
				for (Dog a : dogs) {
					if (!grupo.getDogs().contains(a)) {
						dogsAvailables.add(a);
					}
				}
				result.addObject("dogs", dogsAvailables);
				result.addObject("lleno", lleno);
				return result;

			} catch (Throwable oops) {
				String message = "request.commit.error";
				Competition c = competitionService.findOneToShow(competitionGroupForm.getCompId());
				result = new ModelAndView("competition/participant/join");
				result.addObject("competitionGroupForm", competitionGroupForm);
				result.addObject("groups", c.getGroups());
				result.addObject("lleno", lleno);
				if (competitionGroupForm.getGrupo() == null) {
					message = "register.error.params";
				}
				result.addObject("message", message);
			}

		}

		return result;
	}

	@RequestMapping(value = "/joinCompetition", method = RequestMethod.POST, params = "save")
	public ModelAndView saveJoinCompetition(@Valid CompetitionDogForm competitionDogForm, BindingResult binding) {
		ModelAndView result;
		boolean lleno = competitionDogForm.getGrupo().getNumberOfDogs()
				.equals(competitionDogForm.getGrupo().getDogs().size());
		if (binding.hasErrors() || competitionDogForm.getGrupo() == null || competitionDogForm.getDog() == null
				|| competitionDogForm.getCompId() == null) {
			String message = "request.commit.error";

			result = new ModelAndView("competition/participant/joinCompetition");
			result.addObject("competitionDogForm", competitionDogForm);
			result.addObject("lleno", lleno);
			Groups grupo = competitionDogForm.getGrupo();
			Collection<Dog> dogs = dogService.findAllDogsFromBreedAndParticipant(grupo.getBreed());
			Collection<Dog> dogsAvailables = new HashSet<Dog>();
			for (Dog a : dogs) {
				if (!grupo.getDogs().contains(a)) {
					dogsAvailables.add(a);
				}
			}
			result.addObject("dogs", dogsAvailables);
			if (competitionDogForm.getGrupo() == null || competitionDogForm.getDog() == null
					|| competitionDogForm.getCompId() == null) {
				message = "register.error.params";
			}
			result.addObject("message", message);

		} else {
			try {

				competitionService.joinDogToGroupAndCompetition(competitionDogForm.getCompId(),
						competitionDogForm.getGrupo(), competitionDogForm.getDog());
				result = new ModelAndView("redirect:list.do");

			} catch (Throwable oops) {
				String message = "request.commit.error";

				result = new ModelAndView("competition/participant/joinCompetition");
				result.addObject("lleno", lleno);
				result.addObject("competitionDogForm", competitionDogForm);
				Groups grupo = competitionDogForm.getGrupo();
				Collection<Dog> dogs = dogService.findAllDogsFromBreedAndParticipant(grupo.getBreed());
				Collection<Dog> dogsAvailables = new HashSet<Dog>();
				for (Dog a : dogs) {
					if (!grupo.getDogs().contains(a)) {
						dogsAvailables.add(a);
					}
				}
				result.addObject("dogs", dogsAvailables);
				if (competitionDogForm.getGrupo() == null || competitionDogForm.getDog() == null
						|| competitionDogForm.getCompId() == null) {
					message = "register.error.params";
				}
				result.addObject("message", message);
			}

		}

		return result;
	}
}
