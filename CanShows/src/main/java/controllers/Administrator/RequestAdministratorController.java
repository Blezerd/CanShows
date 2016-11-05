package controllers.Administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import controllers.AbstractController;
import domain.Request;
import forms.RequestAdminForm;

@Controller
@RequestMapping("/request/administrator")
public class RequestAdministratorController extends AbstractController {

	public RequestAdministratorController() {
		super();
	}

	@Autowired
	private RequestService requestService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Request> requests;
		requests = requestService.findAll();
		result = new ModelAndView("request/administrator/list");
		result.addObject("requests", requests);
		result.addObject("actor", "administrator/");
		result.addObject("requestURI", "request/administrator/list.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int requestId) {
		Assert.notNull(requestId);

		ModelAndView result;

		Request request = requestService.findOneToEdit(requestId);
		RequestAdminForm requestAdminForm = requestService
				.constructEditForm(request);

		result = new ModelAndView("request/administrator/edit");
		result.addObject("actor", "administrator/");
		result.addObject("requestAdminForm", requestAdminForm);
		result.addObject("admin", "Admin");
		result.addObject("dog", request.getDog());
		result.addObject("modelAttribute", "requestAdminForm");
		result.addObject("requestId", requestId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveedit")
	public ModelAndView saveEdit(@Valid RequestAdminForm requestAdminForm,
			BindingResult binding) {
		ModelAndView result = null;

		boolean rejected = false;

		if (requestAdminForm.getStatus() == null) {
			rejected = false;
		} else if (requestAdminForm.getStatus().equals("REJECTED")) {
			if (requestAdminForm.getComment().isEmpty()
					|| requestAdminForm.getComment() == null) {
				rejected = true;
			}
		}

		if (binding.hasErrors() || rejected == true) {

			String message = "request.commit.error";
			result = new ModelAndView("request/administrator/edit");
			Request request = requestService.findOneToEdit(requestAdminForm
					.getId());

			if (requestAdminForm.getStatus() == null) {
				message = "register.error.params";
				requestAdminForm = requestService.constructEditForm(request);
			} else if (requestAdminForm.getStatus().equals("REJECTED")) {
				if (requestAdminForm.getComment().isEmpty()
						|| requestAdminForm.getComment() == null) {
					message = "request.commit.comment";
					requestAdminForm = requestService
							.constructEditForm(request);

				}
			}

			result.addObject("requestAdminForm", requestAdminForm);
			result.addObject("actor", "administrator/");
			result.addObject("modelAttribute", "requestAdminForm");
			result.addObject("requestId", requestAdminForm.getId());
			result.addObject("admin", "Admin");
			result.addObject("dog", request.getDog());
			result.addObject("message", message);

		} else {
			try {
				result = new ModelAndView("redirect:list.do");
				requestService.saveEdit(requestAdminForm);

			} catch (Throwable oops) {

				String message = "request.commit.error";

				result = new ModelAndView("request/administrator/edit");
				if (requestAdminForm.getStatus() == null) {
					message = "register.error.params";
				} else if (requestAdminForm.getStatus().equals("REJECTED")) {
					if (requestAdminForm.getComment().isEmpty()
							|| requestAdminForm.getComment() == null) {
						message = "request.commit.comment";

					}
				}
				Request request = requestService.findOneToEdit(requestAdminForm
						.getId());
				requestAdminForm = requestService.constructEditForm(request);
				result.addObject("requestAdminForm", requestAdminForm);
				result.addObject("actor", "administrator/");
				result.addObject("modelAttribute", "requestAdminForm");
				result.addObject("admin", "Admin");
				result.addObject("dog", request.getDog());
				result.addObject("requestId", requestAdminForm.getId());
				result.addObject("message", message);
			}
		}
		return result;
	}
}
