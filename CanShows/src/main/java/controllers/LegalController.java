package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legal")
public class LegalController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public LegalController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------

	@RequestMapping("/legal")
	public ModelAndView legal() {
		ModelAndView result;

		result = new ModelAndView("legal/legal");

		return result;
	}

}
