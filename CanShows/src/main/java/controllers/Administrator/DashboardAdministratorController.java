package controllers.Administrator;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Competition;
import domain.Dog;
import domain.Judge;
import domain.Organiser;
import services.AdministratorService;
import services.CompetitionService;
import services.JudgeService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------
	public DashboardAdministratorController() {
		super();
	}

	// Services ---------------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private CompetitionService competitionService;

	@RequestMapping(value = "/organisersWithCompetition", method = RequestMethod.GET)
	public ModelAndView organisersWithCompetiton() {

		ModelAndView result;
		Collection<Organiser> organisers = new HashSet<Organiser>();

		organisers = administratorService.findOrganisersWithCompetition();
		result = new ModelAndView("dashboard/administrator/organisersWithCompetition");
		result.addObject("organisers", organisers);
		result.addObject("option", 1);
		result.addObject("requestURI", "dashboard/administrator/organisersWithCompetition.do");

		return result;

	}

	@RequestMapping(value = "/bestDog", method = RequestMethod.GET)
	public ModelAndView dogsWithPoints() {

		ModelAndView result;
		Dog dog = new Dog();

		dog = administratorService.findBestDog();
		result = new ModelAndView("dashboard/administrator/bestDog");
		result.addObject("dog", dog);
		result.addObject("option", 2);
		result.addObject("results", dog.getResults());
		result.addObject("requestURI", "dashboard/administrator/bestDog.do");

		return result;

	}

	@RequestMapping(value = "/judgeWithCompetitions", method = RequestMethod.GET)
	public ModelAndView judgeWithCompetitions() {

		ModelAndView result;
		Collection<Judge> judges = new HashSet<Judge>();

		judges = judgeService.findAll();
		result = new ModelAndView("dashboard/administrator/judgeWithCompetitions");
		result.addObject("judges", judges);
		result.addObject("option", 3);
		result.addObject("requestURI", "dashboard/administrator/judgeWithCompetitions.do");

		return result;

	}

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public ModelAndView getPages() {
		ModelAndView result = new ModelAndView("dashboard/administrator/map");
		result.addObject("option", 4);
		result.addObject("requestURI", "dashboard/administrator/map.do");
		Collection<String> calles = new LinkedList<String>();
		Collection<Competition> competicionesFuturas = competitionService.findAllCompetitionsNotStarted();
		for (Competition c : competicionesFuturas) {
			calles.add("\""+c.getAdress()+"\"");
		}
		result.addObject("calles", calles);
		return result;
	}

}
