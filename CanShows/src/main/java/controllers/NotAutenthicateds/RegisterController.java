package controllers.NotAutenthicateds;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.JudgeService;
import services.OrganiserService;
import services.ParticipantService;
import controllers.AbstractController;
import domain.Administrator;
import domain.Judge;
import domain.Organiser;
import domain.Participant;
import forms.ActorForm;
import forms.CustomerForm;
import forms.JudgeForm;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private JudgeService judgeService;

	public RegisterController() {
		super();
	}

	// Participant

	@RequestMapping(value = "/registerParticipant", method = RequestMethod.GET)
	public ModelAndView registerParticipant() {
		ModelAndView result;
		CustomerForm participantForm = new CustomerForm();
		result = new ModelAndView("register/registerParticipant");
		result.addObject("actor", "participant");
		result.addObject("Admin", "Participant");
		result.addObject("participantForm", participantForm);
		result.addObject("modelAttribute", "participantForm");

		return result;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/registerParticipant", method = RequestMethod.POST, params = "save")
	public ModelAndView saveParticipant(
			@Valid @ModelAttribute CustomerForm participantForm,
			BindingResult binding) {
		ModelAndView result;
		Boolean contraseña = false;

		if (participantForm.getPassword() != null
				&& participantForm.getPassword2() != null) {
			contraseña = participantForm.getPassword2().equals(
					participantForm.getPassword());
		}

		Integer mescc = participantForm.getCreditCard().getExpirationMonth();
		Integer añocc = participantForm.getCreditCard().getExpirationYear();
		int mes = new Date(System.currentTimeMillis()).getMonth() + 1;
		int año = new Date(System.currentTimeMillis()).getYear() + 1900;

		if (binding.hasErrors()
				|| !contraseña
				|| !participantForm.getIsCondition()
				|| participantForm.getPassword().length() == 0
				|| participantService.userRegistered(participantForm
						.getUsername())
				|| administratorService.userRegistered(participantForm
						.getUsername())
				|| organiserService.userRegistered(participantForm
						.getUsername())
				|| judgeService.userRegistered(participantForm.getUsername())) {

			result = new ModelAndView("register/registerParticipant");
			result.addObject("participantForm", participantForm);
			result.addObject("actor", "participant");
			result.addObject("Admin", "Participant");
			result.addObject("modelAttribute", "participantForm");

			if (participantForm.getCreditCard().getExpirationMonth() == null
					|| participantForm.getCreditCard().getExpirationYear() == null
					|| participantForm.getPassword().length() == 0) {
				result.addObject("message", "register.error.params");
			} else {
				if (mescc < mes) {
					if (!(añocc > año)) {
						result.addObject("message",
								"register.commit.error.creditCard");
					}
				} else if (mescc == mes) {
					if (!(añocc >= año)) {
						result.addObject("message",
								"register.commit.error.creditCard");
					}

				} else if (mescc > mes) {
					if (!(añocc >= año)) {
						result.addObject("message",
								"register.commit.error.creditCard");
					}
				}
			}
			if (!contraseña) {
				result.addObject("message", "register.commit.password");
			} else if (!participantForm.getIsCondition()) {
				result.addObject("message", "register.commit.condition");
			} else if (participantService.userRegistered(participantForm
					.getUsername())
					|| administratorService.userRegistered(participantForm
							.getUsername())
					|| organiserService.userRegistered(participantForm
							.getUsername())
					|| judgeService.userRegistered(participantForm
							.getUsername())) {
				result.addObject("message",
						"register.commit.duplicatedUsername");

			} else {
				result.addObject("message", null);
			}

		} else {
			try {

				Participant participant = participantService
						.reconstruct(participantForm);
				participantService.save(participant);

				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (Throwable oops) {

				result = new ModelAndView("register/registerParticipant");
				result.addObject("participantForm", participantForm);
				result.addObject("actor", "participant");
				result.addObject("Admin", "Participant");
				result.addObject("modelAttribute", "participantForm");

				if (participantForm.getCreditCard().getExpirationMonth() == null
						|| participantForm.getCreditCard().getExpirationYear() == null) {
					result.addObject("message", "register.error.params");
				} else {
					if (mescc < mes) {
						if (!(añocc > año)) {
							result.addObject("message",
									"register.commit.error.creditCard");
						}
					} else if (mescc == mes) {
						if (!(añocc >= año)) {
							result.addObject("message",
									"register.commit.error.creditCard");
						}

					} else if (mescc > mes) {
						if (!(añocc >= año)) {
							result.addObject("message",
									"register.commit.error.creditCard");
						}
					}
				}
				if (participantService.userRegistered(participantForm
						.getUsername())
						|| administratorService.userRegistered(participantForm
								.getUsername())
						|| organiserService.userRegistered(participantForm
								.getUsername())
						|| judgeService.userRegistered(participantForm
								.getUsername())) {
					result.addObject("message",
							"register.commit.duplicatedUsername");

				}
				if (!participantForm.getIsCondition()) {
					result.addObject("message", "register.commit.condition");
				}

			}
		}
		return result;
	}

	// //Admin

	@RequestMapping(value = "/registerAdmin", method = RequestMethod.GET)
	public ModelAndView registerAdmin() {
		ModelAndView result;
		ActorForm adminForm = new ActorForm();
		result = new ModelAndView("register/registerAdmin");
		result.addObject("actor", "admin");
		result.addObject("Admin", "Admin");
		result.addObject("adminForm", adminForm);
		result.addObject("modelAttribute", "adminForm");

		return result;
	}

	@RequestMapping(value = "/registerAdmin", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdmin(@Valid @ModelAttribute ActorForm adminForm,
			BindingResult binding) {
		ModelAndView result;
		Boolean contraseña = false;

		if (adminForm.getPassword() != null && adminForm.getPassword2() != null) {
			contraseña = adminForm.getPassword2().equals(
					adminForm.getPassword());
		}

		if (binding.hasErrors() || !contraseña || !adminForm.getIsCondition()
				|| adminForm.getName() == "" || adminForm.getSurname() == ""
				|| adminForm.getEmail() == ""
				|| adminForm.getNationality() == ""
				|| adminForm.getPhone() == "") {
			System.out.println(binding.toString());
			result = new ModelAndView("register/registerAdmin");
			result.addObject("adminForm", adminForm);
			result.addObject("actor", "admin");
			result.addObject("Admin", "Admin");
			result.addObject("modelAttribute", "adminForm");

			if (!contraseña) {
				result.addObject("message", "register.commit.password");
			} else if (!adminForm.getIsCondition()) {
				result.addObject("message", "register.commit.condition");
			} else if (adminForm.getName() == ""
					|| adminForm.getSurname() == ""
					|| adminForm.getEmail() == ""
					|| adminForm.getNationality() == ""
					|| adminForm.getPhone() == "") {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}

		} else {
			try {

				Administrator administrator = administratorService
						.reconstruct(adminForm);
				administratorService.save(administrator);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (Throwable oops) {

				result = new ModelAndView("register/registerAdmin");
				result.addObject("adminForm", adminForm);
				result.addObject("actor", "admin");
				result.addObject("Admin", "Admin");
				result.addObject("modelAttribute", "adminForm");

				if (participantService.userRegistered(adminForm.getUsername())
						|| administratorService.userRegistered(adminForm
								.getUsername())
						|| organiserService.userRegistered(adminForm
								.getUsername())
						|| judgeService.userRegistered(adminForm.getUsername())) {
					result.addObject("message",
							"register.commit.duplicatedUsername");

				} else if (!adminForm.getIsCondition()) {
					result.addObject("message", "register.commit.condition");
				} else if (adminForm.getName() == ""
						|| adminForm.getSurname() == ""
						|| adminForm.getEmail() == ""
						|| adminForm.getNationality() == ""
						|| adminForm.getPhone() == "") {
					result.addObject("message", "register.error.params");
				}
			}
		}
		return result;
	}

	// Organiser

	@RequestMapping(value = "/registerOrganiser", method = RequestMethod.GET)
	public ModelAndView registerOrganiser() {
		ModelAndView result;
		CustomerForm organiserForm = new CustomerForm();
		result = new ModelAndView("register/registerOrganiser");
		result.addObject("actor", "organiser");
		result.addObject("Admin", "Organiser");
		result.addObject("organiserForm", organiserForm);
		result.addObject("modelAttribute", "organiserForm");
		return result;
	}

	// Edition ----------------------------------------------------------------

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/registerOrganiser", method = RequestMethod.POST, params = "save")
	public ModelAndView saveOrganiser(
			@Valid @ModelAttribute CustomerForm organiserForm,
			BindingResult binding) {
		ModelAndView result;
		Boolean contraseña = false;

		if (organiserForm.getPassword() != null
				&& organiserForm.getPassword2() != null) {
			contraseña = organiserForm.getPassword2().equals(
					organiserForm.getPassword());
		}

		Integer mescc = organiserForm.getCreditCard().getExpirationMonth();
		Integer añocc = organiserForm.getCreditCard().getExpirationYear();
		int mes = new Date(System.currentTimeMillis()).getMonth() + 1;
		int año = new Date(System.currentTimeMillis()).getYear() + 1900;

		if (binding.hasErrors() || !contraseña
				|| !organiserForm.getIsCondition()) {
			System.out.println(binding.toString());

			result = new ModelAndView("register/registerOrganiser");
			result.addObject("organiserForm", organiserForm);
			result.addObject("actor", "organiser");
			result.addObject("Admin", "Organiser");
			result.addObject("modelAttribute", "organiserForm");
			if (organiserForm.getCreditCard().getExpirationMonth() == null
					|| organiserForm.getCreditCard().getExpirationYear() == null) {
				result.addObject("message", "register.error.params");
			} else {
				if (mescc < mes) {
					if (!(añocc > año)) {
						result.addObject("message",
								"register.commit.error.creditCard");
					}
				} else if (mescc == mes) {
					if (!(añocc >= año)) {
						result.addObject("message",
								"register.commit.error.creditCard");
					}

				} else if (mescc > mes) {
					if (!(añocc >= año)) {
						result.addObject("message",
								"register.commit.error.creditCard");
					}
				}
			}
			if (!contraseña) {
				result.addObject("message", "register.commit.password");
			} else if (!organiserForm.getIsCondition()) {
				result.addObject("message", "register.commit.condition");
			} else {
				result.addObject("message", null);
			}

		} else {
			try {

				Organiser organiser = organiserService
						.reconstruct(organiserForm);
				Assert.notNull(organiser);
				organiserService.save(organiser);

				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (Throwable oops) {

				result = new ModelAndView("register/registerOrganiser");
				result.addObject("organiserForm", organiserForm);
				result.addObject("actor", "organiser");
				result.addObject("Admin", "Organiser");
				result.addObject("modelAttribute", "organiserForm");

				if (organiserForm.getCreditCard().getExpirationMonth() == null
						|| organiserForm.getCreditCard().getExpirationYear() == null) {
					result.addObject("message", "register.error.params");
				} else {
					if (mescc < mes) {
						if (!(añocc > año)) {
							result.addObject("message",
									"register.commit.error.creditCard");
						}
					} else if (mescc == mes) {
						if (!(añocc >= año)) {
							result.addObject("message",
									"register.commit.error.creditCard");
						}

					} else if (mescc > mes) {
						if (!(añocc >= año)) {
							result.addObject("message",
									"register.commit.error.creditCard");
						}

					}
				}
				if (participantService.userRegistered(organiserForm
						.getUsername())
						|| administratorService.userRegistered(organiserForm
								.getUsername())
						|| organiserService.userRegistered(organiserForm
								.getUsername())
						|| judgeService.userRegistered(organiserForm
								.getUsername())) {
					result.addObject("message",
							"register.commit.duplicatedUsername");

				}
				if (!organiserForm.getIsCondition()) {
					result.addObject("message", "register.commit.condition");
				}

			}
		}
		return result;
	}

	// Judge

	@RequestMapping(value = "/registerJudge", method = RequestMethod.GET)
	public ModelAndView registerJudge() {
		ModelAndView result;
		JudgeForm judgeForm = new JudgeForm();
		result = new ModelAndView("register/registerJudge");
		result.addObject("actor", "judge");
		result.addObject("Admin", "Judge");
		result.addObject("judgeForm", judgeForm);
		result.addObject("modelAttribute", "judgeForm");

		return result;
	}

	@RequestMapping(value = "/registerJudge", method = RequestMethod.POST, params = "save")
	public ModelAndView saveJudge(@Valid @ModelAttribute JudgeForm judgeForm,
			BindingResult binding) {
		ModelAndView result;
		Boolean contraseña = false;

		if (judgeForm.getPassword() != null && judgeForm.getPassword2() != null) {
			contraseña = judgeForm.getPassword2().equals(
					judgeForm.getPassword());
		}

		if (binding.hasErrors() || !contraseña || !judgeForm.getIsCondition()) {

			result = new ModelAndView("register/registerJudge");
			result.addObject("judgeForm", judgeForm);
			result.addObject("actor", "judge");
			result.addObject("Admin", "Judge");
			result.addObject("modelAttribute", "judgeForm");

			if (!contraseña) {
				result.addObject("message", "register.commit.password");
			} else if (!judgeForm.getIsCondition()) {
				result.addObject("message", "register.commit.condition");
			} else {
				result.addObject("message", null);
			}

		} else {
			try {

				Judge judge = judgeService.reconstruct(judgeForm);
				judgeService.save(judge);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (Throwable oops) {

				result = new ModelAndView("register/registerJudge");
				result.addObject("judgeForm", judgeForm);
				result.addObject("actor", "judge");
				result.addObject("Admin", "Judge");
				result.addObject("modelAttribute", "judgeForm");

				if (participantService.userRegistered(judgeForm.getUsername())
						|| administratorService.userRegistered(judgeForm
								.getUsername())
						|| organiserService.userRegistered(judgeForm
								.getUsername())
						|| judgeService.userRegistered(judgeForm.getUsername())) {
					result.addObject("message",
							"register.commit.duplicatedUsername");

				}
				if (!judgeForm.getIsCondition()) {
					result.addObject("message", "register.commit.condition");
				}
				if (judgeService.isNumberRegistered(judgeForm.getJudgeNumber())) {
					result.addObject("message", "register.commit.judgeNumber");
				}
			}
		}
		return result;
	}
}
