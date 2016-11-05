package controllers.Participant;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FolderService;
import services.MessageService;
import services.ParticipantService;
import controllers.AbstractController;
import domain.Folder;
import domain.Message;
import domain.Participant;

@Controller
@RequestMapping("/folder/participant/")
public class FolderParticipantController extends AbstractController {

	public FolderParticipantController() {
		super();
	}

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private MessageService messageService;

	// List
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView detailsFolders(@RequestParam int folderId) {
		ModelAndView result;

		
		Collection<Message> messages = messageService
				.findAllByFolderId(folderId);
		result = new ModelAndView("folder/participant/details");
		result.addObject("actor", "participant/");
		
		result.addObject("requestURI", "folder/participant/details.do");
		result.addObject("messages", messages);

		return result;
	}

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listFolders() {
		ModelAndView result;

		Participant p = participantService.findByPrincipal();
		Collection<Folder> folders = folderService
				.findNameFolderParticipant(p.getId());
		result = new ModelAndView("folder/participant/list");
		result.addObject("actor", "participant/");
		result.addObject("participant", p);
		result.addObject("requestURI", "folder/participant/list.do");
		result.addObject("folders", folders);

		return result;
	}
}
