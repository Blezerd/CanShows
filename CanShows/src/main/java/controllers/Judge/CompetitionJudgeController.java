package controllers.Judge;

import java.sql.Timestamp;
import java.util.Collection;
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

import services.CompetitionService;
import services.JudgeService;
import services.ResultService;
import controllers.AbstractController;
import domain.Competition;
import domain.Dog;
import domain.Groups;
import domain.Judge;
import domain.Result;

@Controller
@RequestMapping("/competition/judge/")
public class CompetitionJudgeController extends AbstractController {

	public CompetitionJudgeController() {
		super();
	}

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private ResultService resultService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Judge j = judgeService.findByPrincipal();
		Collection<Competition> competitions = competitionService
				.findAllCompetitionsFromJudgeById(j.getId());
		result = new ModelAndView("competition/judge/list");
		result.addObject("actor", "judge/");
		result.addObject("requestURI", "competition/judge/list.do");
		result.addObject("competitions", competitions);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		result.addObject("now", now);
		result.addObject("judge", j);
		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int competitionId) {

		ModelAndView result;
		Competition c = competitionService.findOneToShow(competitionId);

		result = new ModelAndView("competition/judge/details");
		result.addObject("competition", c);
		result.addObject("actor", "judge/");
		result.addObject("judges", c.getJudges());
		result.addObject("groups", c.getGroups());
		result.addObject("results", c.getResults());
		Collection<Dog> dogs = new HashSet<Dog>();
		for (Groups g : c.getGroups()) {
			dogs.addAll(g.getDogs());
		}
		result.addObject("dogs", dogs);
		result.addObject("requestURI", "competition/judge/details.do");
		return result;

	}

	// Judge
	@RequestMapping(value = "/judge", method = RequestMethod.GET)
	public ModelAndView judge(@RequestParam int competitionId) {
		ModelAndView result;
		Competition competition = competitionService
				.findOneToShow(competitionId);
		result = new ModelAndView("competition/judge/judge");
		result.addObject("requestURI", "competition/judge/judge.do");
		result.addObject("competition", competition);
		Collection<Dog> dogs = new HashSet<Dog>();
		for (Groups g : competition.getGroups()) {
			dogs.addAll(g.getDogs());
			for (Dog d : dogs) {
				for (Result r : d.getResults()) {
					if (r.getCompetition().equals(competition)) {
						dogs.remove(d);
					}
				}
			}
		}
		result.addObject("dogs", dogs);
		return result;
	}

	// Judge Dog
	@RequestMapping(value = "/judgeDog", method = RequestMethod.GET)
	public ModelAndView judgeDog(@RequestParam int dogId,
			@RequestParam int competitionId) {
		ModelAndView result = new ModelAndView("competition/judge/judgeDog");
		Result resultado = resultService.create(dogId, competitionId);
		result.addObject("resultado", resultado);
		return result;
	}

	@RequestMapping(value = "/judgeDog", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid @ModelAttribute Result resultado,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors() || resultado.getComment() == ""
				|| resultado.getPoints() == null
				|| resultado.getPosition() == null) {
			result = new ModelAndView("competition/judge/judgeDog");
			result.addObject("resultado", resultado);
			if (resultado.getComment() == "" || resultado.getPoints() == null
					|| resultado.getPosition() == null) {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Assert.notNull(resultado);
				resultService.save(resultado);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "judge/");
			} catch (Throwable oops) {
				result = new ModelAndView("competition/judge/judgeDog");
				result.addObject("resultado", resultado);
				if (resultado.getComment() == ""
						|| resultado.getPoints() == null
						|| resultado.getPosition() == null) {
					result.addObject("message", "register.error.params");
				} else {
					result.addObject("message", null);
				}
			}
		}
		return result;
	}
}
