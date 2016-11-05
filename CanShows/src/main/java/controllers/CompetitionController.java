package controllers;

import java.sql.Timestamp;
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
import services.CompetitionService;
import services.JudgeService;
import domain.Competition;
import domain.Dog;
import domain.Groups;
import domain.Judge;

@Controller
@RequestMapping("/competition/")
public class CompetitionController extends AbstractController {

	public CompetitionController() {
		super();
	}

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private JudgeService judgeService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Judge p = null;
		if (SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().iterator().next().getAuthority()
				.contains("JUDGE")) {
			UserAccount user = (UserAccount) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			for (Authority a : user.getAuthorities()) {
				if (a.getAuthority().equals(Authority.JUDGE)) {
					p = judgeService.findByPrincipal();
				}
			}
		}
		Collection<Competition> competitions = competitionService
				.findAllCompetitionsAvailables();
		result = new ModelAndView("competition/list");
		result.addObject("actor", "");
		result.addObject("requestURI", "competition/list.do");
		result.addObject("competitions", competitions);
		result.addObject("judge", p);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		result.addObject("now", now);
		return result;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int competitionId) {

		ModelAndView result;
		Competition c = competitionService.findOneToShow(competitionId);

		result = new ModelAndView("competition/details");
		result.addObject("competition", c);
		result.addObject("judges", c.getJudges());
		result.addObject("groups", c.getGroups());
		result.addObject("results", c.getResults());
		Collection<Dog> dogs = new HashSet<Dog>();
		for (Groups g : c.getGroups()) {
			dogs.addAll(g.getDogs());
		}
		result.addObject("dogs", dogs);
		result.addObject("requestURI", "competition/details.do");
		return result;

	}
}
