package controllers.Organiser;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BreedService;
import services.CompetitionService;
import services.JudgeService;
import services.OrganiserService;
import controllers.AbstractController;
import domain.Breed;
import domain.Competition;
import domain.Dog;
import domain.Groups;
import domain.Judge;
import domain.Organiser;
import forms.CompetitionAddGroupForm;
import forms.CompetitionAddJudgeForm;
import forms.CompetitionOrganiserForm;

@Controller
@RequestMapping("/competition/organiser/")
public class CompetitionOrganiserController extends AbstractController {

	public CompetitionOrganiserController() {
		super();
	}

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private BreedService breedService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Organiser j = organiserService.findByPrincipal();
		Collection<Competition> competitions = j.getCompetitions();
		result = new ModelAndView("competition/organiser/list");
		result.addObject("actor", "organiser/");
		result.addObject("organiser", j);
		result.addObject("requestURI", "competition/organiser/list.do");
		result.addObject("competitions", competitions);
		Date now = new Date(System.currentTimeMillis());
		result.addObject("now", now);
		result.addObject("yo", true);
		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int competitionId) {

		ModelAndView result;
		Competition c = competitionService.findOneToShow(competitionId);

		result = new ModelAndView("competition/organiser/details");
		result.addObject("competition", c);
		result.addObject("actor", "organiser/");
		result.addObject("judges", c.getJudges());
		result.addObject("groups", c.getGroups());
		result.addObject("results", c.getResults());
		Collection<Dog> dogs = new HashSet<Dog>();
		for (Groups g : c.getGroups()) {
			dogs.addAll(g.getDogs());
		}
		Integer perros = dogs.size();
		result.addObject("dogs", dogs);
		result.addObject("perros", perros);
		result.addObject("requestURI", "competition/organiser/details.do");
		Date now = new Date(System.currentTimeMillis());
		result.addObject("now", now);
		result.addObject("calle","\""+c.getAdress()+"\"");
		return result;

	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = new ModelAndView("competition/organiser/create");
		CompetitionOrganiserForm competitionOrganiserForm = competitionService
				.constructCompOrgForm();
		result.addObject("competitionOrganiserForm", competitionOrganiserForm);
		result.addObject("create", true);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(
			@Valid @ModelAttribute CompetitionOrganiserForm competitionOrganiserForm,
			BindingResult binding) {
		Date now = new Date(System.currentTimeMillis());
		ModelAndView result;

		if (binding.hasErrors()
				|| competitionService
						.exist(competitionOrganiserForm.getTitle())
				|| competitionOrganiserForm.getAdress() == ""
				|| competitionOrganiserForm.getCelebrationDate() == null
				|| competitionOrganiserForm.getFirstPrize() == ""
				|| competitionOrganiserForm.getInscriptionLimitDate() == null
				|| competitionOrganiserForm.getInscriptionPrice() == null
				|| competitionOrganiserForm.getMinimumPoints() == null
				|| competitionOrganiserForm.getSecondPrize() == ""
				|| competitionOrganiserForm.getThirdPrize() == ""
				|| competitionOrganiserForm.getTitle() == ""
				|| competitionOrganiserForm.getType() == ""
				|| competitionOrganiserForm.getCelebrationDate().before(
						competitionOrganiserForm.getInscriptionLimitDate())
				|| competitionOrganiserForm.getCelebrationDate().before(now)
				|| competitionOrganiserForm.getInscriptionLimitDate().before(
						now)) {
			result = new ModelAndView("competition/organiser/create");
			result.addObject("competitionOrganiserForm",
					competitionOrganiserForm);
			result.addObject("create", true);
			if (competitionOrganiserForm.getAdress() == ""
					|| competitionOrganiserForm.getCelebrationDate() == null
					|| competitionOrganiserForm.getFirstPrize() == ""
					|| competitionOrganiserForm.getInscriptionLimitDate() == null
					|| competitionOrganiserForm.getInscriptionPrice() == null
					|| competitionOrganiserForm.getMinimumPoints() == null
					|| competitionOrganiserForm.getSecondPrize() == ""
					|| competitionOrganiserForm.getThirdPrize() == ""
					|| competitionOrganiserForm.getTitle() == ""
					|| competitionOrganiserForm.getType() == "") {
				result.addObject("message", "register.error.params");
			} else if (competitionService.exist(competitionOrganiserForm
					.getTitle())) {
				result.addObject("message", "competition.existe");
			} else if (competitionOrganiserForm.getCelebrationDate().before(
					competitionOrganiserForm.getInscriptionLimitDate())) {
				result.addObject("message", "competition.bad.dates");
			} else if (competitionOrganiserForm.getCelebrationDate()
					.before(now)
					|| competitionOrganiserForm.getInscriptionLimitDate()
							.before(now)) {
				result.addObject("message", "competition.past.dates");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Competition competition = competitionService
						.reconstructOrganiserForm(competitionOrganiserForm);
				competitionService.saveCreate(competition);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "organiser/");
				result.addObject("now", now);
				result.addObject("yo", true);
			} catch (Throwable oops) {
				System.out.println(oops.getLocalizedMessage());
				result = new ModelAndView("competition/organiser/create");
				result.addObject("competitionOrganiserForm",
						competitionOrganiserForm);
				result.addObject("create", true);
				if (competitionOrganiserForm.getAdress() == ""
						|| competitionOrganiserForm.getCelebrationDate() == null
						|| competitionOrganiserForm.getFirstPrize() == ""
						|| competitionOrganiserForm.getInscriptionLimitDate() == null
						|| competitionOrganiserForm.getInscriptionPrice() == null
						|| competitionOrganiserForm.getMinimumPoints() == null
						|| competitionOrganiserForm.getSecondPrize() == ""
						|| competitionOrganiserForm.getThirdPrize() == ""
						|| competitionOrganiserForm.getTitle() == ""
						|| competitionOrganiserForm.getType() == "") {
					result.addObject("message", "register.error.params");
				} else if (competitionOrganiserForm.getCelebrationDate()
						.before(competitionOrganiserForm
								.getInscriptionLimitDate())) {
					result.addObject("message", "competition.bad.dates");
				} else if (competitionService.exist(competitionOrganiserForm
						.getTitle())) {
					result.addObject("message", "competition.existe");
				} else if (competitionOrganiserForm.getCelebrationDate()
						.before(now)
						|| competitionOrganiserForm.getInscriptionLimitDate()
								.before(now)) {
					result.addObject("message", "competition.past.dates");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int competitionId) {
		ModelAndView result = new ModelAndView("competition/organiser/edit");
		CompetitionOrganiserForm competitionOrganiserForm = competitionService
				.constructCompOrgFormEdit(competitionService
						.findOneToShow(competitionId));
		result.addObject("competitionOrganiserForm", competitionOrganiserForm);
		result.addObject("create", false);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(
			@Valid @ModelAttribute CompetitionOrganiserForm competitionOrganiserForm,
			BindingResult binding) {

		ModelAndView result;
		Date now = new Date(System.currentTimeMillis());
		Boolean fechasAtras = false;
		Competition c = competitionService
				.findOneByTitle(competitionOrganiserForm.getTitle());
		if (c.getCelebrationDate().after(
				competitionOrganiserForm.getCelebrationDate())
				|| c.getInscriptionLimitDate().after(
						competitionOrganiserForm.getInscriptionLimitDate())) {
			fechasAtras = true;
		}
		if (binding.hasErrors()
				|| fechasAtras == true
				|| competitionOrganiserForm.getAdress() == ""
				|| competitionOrganiserForm.getCelebrationDate() == null
				|| competitionOrganiserForm.getFirstPrize() == ""
				|| competitionOrganiserForm.getInscriptionLimitDate() == null
				|| competitionOrganiserForm.getInscriptionPrice() == null
				|| competitionOrganiserForm.getMinimumPoints() == null
				|| competitionOrganiserForm.getSecondPrize() == ""
				|| competitionOrganiserForm.getThirdPrize() == ""
				|| competitionOrganiserForm.getTitle() == ""
				|| competitionOrganiserForm.getType() == ""
				|| competitionOrganiserForm.getCelebrationDate().before(
						competitionOrganiserForm.getInscriptionLimitDate())
				|| competitionOrganiserForm.getCelebrationDate().before(now)
				|| competitionOrganiserForm.getInscriptionLimitDate().before(
						now)) {
			result = new ModelAndView("competition/organiser/edit");
			result.addObject("competitionOrganiserForm",
					competitionOrganiserForm);
			result.addObject("create", false);
			if (competitionOrganiserForm.getAdress() == ""
					|| competitionOrganiserForm.getCelebrationDate() == null
					|| competitionOrganiserForm.getFirstPrize() == ""
					|| competitionOrganiserForm.getInscriptionLimitDate() == null
					|| competitionOrganiserForm.getInscriptionPrice() == null
					|| competitionOrganiserForm.getMinimumPoints() == null
					|| competitionOrganiserForm.getSecondPrize() == ""
					|| competitionOrganiserForm.getThirdPrize() == ""
					|| competitionOrganiserForm.getTitle() == ""
					|| competitionOrganiserForm.getType() == "") {
				result.addObject("message", "register.error.params");
			} else if (competitionOrganiserForm.getCelebrationDate().before(
					competitionOrganiserForm.getInscriptionLimitDate())) {
				result.addObject("message", "competition.bad.dates");
			} else if (fechasAtras) {
				result.addObject("message", "competition.bad.fechas");
			} else if (competitionOrganiserForm.getCelebrationDate()
					.before(now)
					|| competitionOrganiserForm.getInscriptionLimitDate()
							.before(now)) {
				result.addObject("message", "competition.past.dates");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Competition competition = competitionService
						.reconstructOrganiserFormEdit(competitionOrganiserForm);
				competitionService.saveEdit(competition);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "organiser/");
				result.addObject("yo", true);
			} catch (Throwable oops) {
				result = new ModelAndView("competition/organiser/edit");
				result.addObject("competitionOrganiserForm",
						competitionOrganiserForm);
				result.addObject("create", false);
				if (competitionOrganiserForm.getAdress() == ""
						|| competitionOrganiserForm.getCelebrationDate() == null
						|| competitionOrganiserForm.getFirstPrize() == ""
						|| competitionOrganiserForm.getInscriptionLimitDate() == null
						|| competitionOrganiserForm.getInscriptionPrice() == null
						|| competitionOrganiserForm.getMinimumPoints() == null
						|| competitionOrganiserForm.getSecondPrize() == ""
						|| competitionOrganiserForm.getThirdPrize() == ""
						|| competitionOrganiserForm.getTitle() == ""
						|| competitionOrganiserForm.getType() == "") {
					result.addObject("message", "register.error.params");
				} else if (competitionOrganiserForm.getCelebrationDate()
						.before(competitionOrganiserForm
								.getInscriptionLimitDate())) {
					result.addObject("message", "competition.bad.dates");
				} else if (competitionOrganiserForm.getCelebrationDate()
						.before(now)
						|| competitionOrganiserForm.getInscriptionLimitDate()
								.before(now)) {
					result.addObject("message", "competition.past.dates");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}

	// AddJudge
	@RequestMapping(value = "/addGroup", method = RequestMethod.GET)
	public ModelAndView addGroup(@RequestParam int competitionId) {
		ModelAndView result = new ModelAndView("competition/organiser/addGroup");
		Competition competition = competitionService
				.findOneToShow(competitionId);
		CompetitionAddGroupForm competitionAddGroupForm = competitionService
				.constructAddGroupForm(competition);
		result.addObject("competitionAddGroupForm", competitionAddGroupForm);
		Collection<Breed> all = breedService.findAllBreeds();
		Collection<Breed> breeds = new HashSet<Breed>();
		for (Groups g : competition.getGroups()) {
			for (Breed a : all) {
				if (g.getBreed() == a) {
					breeds.add(a);
				}
			}
		}
		all.removeAll(breeds);
		result.addObject("breeds", all);
		return result;
	}

	@RequestMapping(value = "/addGroup", method = RequestMethod.POST, params = "save")
	public ModelAndView saveGroup(
			@Valid @ModelAttribute CompetitionAddGroupForm competitionAddGroupForm,
			BindingResult binding) {
		Competition competition = competitionService
				.findOneToShow(competitionAddGroupForm.getId());
		ModelAndView result;
		if (binding.hasErrors() || competitionAddGroupForm.getId() == null
				|| competitionAddGroupForm.getBreed() == null
				|| competitionAddGroupForm.getNumberOfDogs() == null
				|| competitionAddGroupForm.getRing() == null) {
			result = new ModelAndView("competition/organiser/addGroup");
			result.addObject("competitionAddGroupForm", competitionAddGroupForm);
			Collection<Breed> all = breedService.findAllBreeds();
			Collection<Breed> breeds = new HashSet<Breed>();
			for (Groups g : competition.getGroups()) {
				for (Breed a : all) {
					if (g.getBreed() == a) {
						breeds.add(a);
					}
				}
			}
			all.removeAll(breeds);
			result.addObject("breeds", all);
			if (competitionAddGroupForm.getId() == null
					|| competitionAddGroupForm.getBreed() == null
					|| competitionAddGroupForm.getNumberOfDogs() == null
					|| competitionAddGroupForm.getRing() == null) {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Competition competitiona = competitionService
						.reconstructAddGroup(competitionAddGroupForm);
				competitionService.saveGroup(competitiona);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "organiser/");
				result.addObject("yo", true);
			} catch (Throwable oops) {
				result = new ModelAndView("competition/organiser/addGroup");
				result.addObject("competitionAddGroupForm",
						competitionAddGroupForm);
				Collection<Breed> all = breedService.findAllBreeds();
				Collection<Breed> breeds = new HashSet<Breed>();
				for (Groups g : competition.getGroups()) {
					for (Breed a : all) {
						if (g.getBreed() == a) {
							breeds.add(a);
						}
					}
				}
				all.removeAll(breeds);
				result.addObject("breeds", all);
				if (competitionAddGroupForm.getId() == null
						|| competitionAddGroupForm.getBreed() == null
						|| competitionAddGroupForm.getNumberOfDogs() == null
						|| competitionAddGroupForm.getRing() == null) {
					result.addObject("message", "register.error.params");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}

	// Publish
	@RequestMapping(value = "/publish", method = RequestMethod.GET)
	public ModelAndView publish(@RequestParam int competitionId) {
		ModelAndView result = new ModelAndView("redirect:list.do");
		result.addObject("actor", "organiser/");
		result.addObject("yo", true);
		Assert.notNull(competitionId);
		try {
			competitionService.publish(competitionId);
		} catch (Throwable oops) {
			result.addObject("message", "contest.commit.error");
		}
		return result;
	}

	// Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int competitionId) {
		ModelAndView result = new ModelAndView("redirect:list.do");
		result.addObject("actor", "organiser/");
		result.addObject("yo", true);
		Assert.notNull(competitionId);
		try {
			competitionService.delete(competitionId);
		} catch (Throwable oops) {
			result.addObject("message", "contest.commit.error");
		}
		return result;
	}

	// AddJudge
	@RequestMapping(value = "/addJudge", method = RequestMethod.GET)
	public ModelAndView addJudge(@RequestParam int competitionId) {
		ModelAndView result = new ModelAndView("competition/organiser/addJudge");
		Competition competition = competitionService
				.findOneToShow(competitionId);
		CompetitionAddJudgeForm competitionAddJudgeForm = competitionService
				.constructAddJudgeForm(competition);
		result.addObject("competitionAddJudgeForm", competitionAddJudgeForm);
		Collection<Judge> all = judgeService.findAll();
		Collection<Judge> judges = new HashSet<Judge>();
		for (Judge a : all) {
			if (!competition.getJudges().contains(a)) {
				judges.add(a);
			}
		}
		result.addObject("judges", judges);
		return result;
	}

	@RequestMapping(value = "/addJudge", method = RequestMethod.POST, params = "save")
	public ModelAndView saveJudge(
			@Valid @ModelAttribute CompetitionAddJudgeForm competitionAddJudgeForm,
			BindingResult binding) {
		Competition competition = competitionService
				.findOneToShow(competitionAddJudgeForm.getId());
		ModelAndView result;
		if (binding.hasErrors() || competitionAddJudgeForm.getId() == null
				|| competitionAddJudgeForm.getJudge() == null) {
			result = new ModelAndView("competition/organiser/addJudge");
			result.addObject("competitionAddJudgeForm", competitionAddJudgeForm);
			Collection<Judge> all = judgeService.findAll();
			Collection<Judge> judges = new HashSet<Judge>();
			for (Judge a : all) {
				if (!competition.getJudges().contains(a)) {
					judges.add(a);
				}
			}
			result.addObject("judges", judges);
			if (competitionAddJudgeForm.getId() == null
					|| competitionAddJudgeForm.getJudge() == null) {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Competition competitiona = competitionService
						.reconstructAddJudge(competitionAddJudgeForm);
				competitionService.saveJudge(competitiona);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "organiser/");
				result.addObject("yo", true);
			} catch (Throwable oops) {
				result = new ModelAndView("competition/organiser/addJudge");
				result.addObject("competitionAddJudgeForm",
						competitionAddJudgeForm);
				Collection<Judge> all = judgeService.findAll();
				Collection<Judge> judges = new HashSet<Judge>();
				for (Judge a : all) {
					if (!competition.getJudges().contains(a)) {
						judges.add(a);
					}
				}
				result.addObject("judges", judges);
				if (competitionAddJudgeForm.getId() == null
						|| competitionAddJudgeForm.getJudge() == null) {
					result.addObject("message", "register.error.params");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}
}
