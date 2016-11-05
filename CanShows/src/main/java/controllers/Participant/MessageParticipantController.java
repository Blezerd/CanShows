package controllers.Participant;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import services.ParticipantService;
import controllers.AbstractController;
import domain.Message;
import domain.Participant;
import forms.MessageForm;

@Controller
@RequestMapping("/message/participant/")
public class MessageParticipantController extends AbstractController {

	public MessageParticipantController() {
		super();
	}

	@Autowired
	private MessageService messageService;

	@Autowired
	private ParticipantService participantService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Participant p = participantService.findByPrincipal();
		Collection<Message> messages = messageService
				.findAllMessageFromParticipantById(p.getId());
		result = new ModelAndView("folder/participant/list");
		result.addObject("actor", "participant/");

		result.addObject("requestURI", "folder/participant/list.do");
		result.addObject("messages", messages);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message m = messageService.create();
		MessageForm messageForm = messageService.constructCreate(m);
		result = new ModelAndView("message/participant/edit");
		result.addObject("messageForm", messageForm);
		result.addObject("actor", "participant/");
		Collection<Participant> participants = participantService.findAll();
		participants.remove(participantService.findByPrincipal());
		result.addObject("participants", participants);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageForm messageForm,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			String message = "message.commit.error";
			result = new ModelAndView("message/participant/edit");
			result.addObject("messageForm", messageForm);
			result.addObject("actor", "participant/");
			if (messageForm.getParticipant() == null
					|| messageForm.getSubject() == null
					|| messageForm.getBody() == null) {
				message = "register.error.params";
			}
			result.addObject("modelAttribute", "messageForm");
			result.addObject("message", message);

			Collection<Participant> participants = participantService.findAll();
			result.addObject("participants", participants);

		} else {
			try {
				Assert.notNull(messageForm);
				Message message = messageService.reconstruct(messageForm);
				messageService.save(message);
				result = new ModelAndView("redirect:/folder/participant/list.do");
			} catch (Throwable oops) {

				result = new ModelAndView("message/participant/edit");
				result.addObject("messageForm", messageForm);
				result.addObject("actor", "participant/");
				String message = "message.commit.error";
				if (messageForm.getParticipant() == null
						|| messageForm.getBody() == null
						|| messageForm.getSubject() == null) {
					message = "register.error.params";
				}
				result.addObject("message", message);
				result.addObject("modelAttribute", "messageForm");

				Collection<Participant> participants = participantService
						.findAll();
				result.addObject("participants", participants);

			}

		}

		return result;
	}

}
