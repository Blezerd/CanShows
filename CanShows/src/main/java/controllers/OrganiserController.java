package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.OrganiserService;
import domain.Organiser;


@Controller
@RequestMapping("/organiser")
public class OrganiserController extends AbstractController{
	
	public OrganiserController(){
		super();
	}
	
	@Autowired
	private OrganiserService organiserService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Organiser> organisers = new HashSet<Organiser>();
		organisers = organiserService.findAll(); 

		result = new ModelAndView("organiser/list");
		result.addObject("organisers", organisers);
		result.addObject("actor", "");
		result.addObject("requestURI", "organiser/list.do");
		return result;

	}

}
