package controllers.Participant;

import java.util.Collection;

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
import services.DogService;
import services.ParticipantService;
import controllers.AbstractController;
import domain.Breed;
import domain.Dog;
import domain.Participant;
import forms.AddFOMForm;
import forms.DogForm;

@Controller
@RequestMapping("/dog/participant/")
public class DogParticipantController extends AbstractController {

	public DogParticipantController() {
		super();
	}

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private DogService dogService;

	@Autowired
	private BreedService breedService;

	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		Participant p = participantService.findByPrincipal();
		Collection<Dog> dogs = dogService.findAllDogsFromParticipantById(p
				.getId());
		result = new ModelAndView("dog/participant/list");
		result.addObject("actor", "participant/");
		result.addObject("friend", "");
		result.addObject("participant", p);
		result.addObject("requestURI", "dog/participant/list.do");
		result.addObject("dogs", dogs);

		return result;
	}

	@RequestMapping(value = "/listFriend", method = RequestMethod.GET)
	public ModelAndView listFriend(@RequestParam int friendId) {
		ModelAndView result;

		Participant p = participantService.findByPrincipal();
		Collection<Dog> dogs = dogService
				.findAllDogsFromParticipantById(friendId);
		result = new ModelAndView("dog/participant/listFriend");
		result.addObject("actor", "participant/");
		result.addObject("friend", "Friend");
		result.addObject("participant", p);
		result.addObject("requestURI", "dog/participant/listFriend.do");
		result.addObject("dogs", dogs);

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = new ModelAndView("dog/participant/create");
		DogForm dogForm = dogService.constructCreate();
		result.addObject("dogForm", dogForm);
		Collection<Breed> breeds = breedService.findAllBreeds();
		result.addObject("breeds", breeds);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid @ModelAttribute DogForm dogForm,
			BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors() || dogForm.getBreed() == null
				|| dogForm.getHeight() == null || dogForm.getName() == ""
				|| dogForm.getNickname() == "" || dogForm.getSex() == ""
				|| dogForm.getWeight() == null) {

			result = new ModelAndView("dog/participant/create");
			result.addObject("dogForm", dogForm);
			Collection<Breed> breeds = breedService.findAllBreeds();
			result.addObject("breeds", breeds);
			if (dogForm.getBreed() == null || dogForm.getHeight() == null
					|| dogForm.getName() == "" || dogForm.getNickname() == ""
					|| dogForm.getSex() == "" || dogForm.getWeight() == null) {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Dog dog = dogService.reconstruct(dogForm);
				dogService.save(dog);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "participant/");
			} catch (Throwable oops) {
				result = new ModelAndView("dog/participant/create");
				result.addObject("dogForm", dogForm);
				Collection<Breed> breeds = breedService.findAllBreeds();
				result.addObject("breeds", breeds);
				if (dogForm.getBreed() == null || dogForm.getHeight() == null
						|| dogForm.getName() == ""
						|| dogForm.getNickname() == ""
						|| dogForm.getSex() == ""
						|| dogForm.getWeight() == null) {
					result.addObject("message", "register.error.params");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}

	// AddMother
	@RequestMapping(value = "/addMother", method = RequestMethod.GET)
	public ModelAndView addMother(@RequestParam int dogId) {
		ModelAndView result = new ModelAndView("dog/participant/addMother");
		Dog dog = dogService.findOneToShow(dogId);
		AddFOMForm addForm = dogService.constructAddParentForm(dogId);
		addForm.setBoolMadre(true);
		result.addObject("addFOMForm", addForm);
		Collection<Dog> hembras = dogService.findAllDogsFromBreedAndSex(dog,
				dog.getBreed(), "FEMALE");

		result.addObject("hembras", hembras);
		result.addObject("addMother", true);
		return result;
	}

	@RequestMapping(value = "/addMother", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAddMother(
			@Valid @ModelAttribute AddFOMForm addFOMForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors() || addFOMForm.getDogId() == null
				|| addFOMForm.getBoolMadre() == null
				|| addFOMForm.getParent() == null) {

			result = new ModelAndView("dog/participant/addMother");
			result.addObject("addMother", true);
			result.addObject("addFOMForm", addFOMForm);
			Dog dog = dogService.findOneToShow(addFOMForm.getDogId());
			Collection<Dog> hembras = dogService.findAllDogsFromBreedAndSex(
					dog, dog.getBreed(), "FEMALE");
			result.addObject("hembras", hembras);
			if (addFOMForm.getDogId() == null
					|| addFOMForm.getBoolMadre() == null
					|| addFOMForm.getParent() == null) {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Dog dog = dogService.reconstructAddParent(addFOMForm);
				dogService.saveAndFlush(dog);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "participant/");
			} catch (Throwable oops) {

				result = new ModelAndView("dog/participant/addMother");
				result.addObject("addMother", true);
				result.addObject("addFOMForm", addFOMForm);
				Dog dog = dogService.findOneToShow(addFOMForm.getDogId());
				Collection<Dog> hembras = dogService
						.findAllDogsFromBreedAndSex(dog, dog.getBreed(),
								"FEMALE");
				result.addObject("hembras", hembras);
				if (addFOMForm.getDogId() == null
						|| addFOMForm.getBoolMadre() == null
						|| addFOMForm.getParent() == null) {
					result.addObject("message", "register.error.params");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}

	// AddFather
	@RequestMapping(value = "/addFather", method = RequestMethod.GET)
	public ModelAndView addFather(@RequestParam int dogId) {
		ModelAndView result = new ModelAndView("dog/participant/addFather");
		Dog dog = dogService.findOneToShow(dogId);
		AddFOMForm addForm = dogService.constructAddParentForm(dogId);
		addForm.setBoolMadre(false);
		result.addObject("addFOMForm", addForm);
		Collection<Dog> machos = dogService.findAllDogsFromBreedAndSex(dog,
				dog.getBreed(), "MALE");
		result.addObject("machos", machos);
		result.addObject("addMother", false);
		return result;
	}

	@RequestMapping(value = "/addFather", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAddFather(
			@Valid @ModelAttribute AddFOMForm addFOMForm, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors() || addFOMForm.getDogId() == null
				|| addFOMForm.getBoolMadre() == null
				|| addFOMForm.getParent() == null) {

			result = new ModelAndView("dog/participant/addFather");
			result.addObject("addMother", false);
			result.addObject("addFOMForm", addFOMForm);
			Dog dog = dogService.findOneToShow(addFOMForm.getDogId());
			Collection<Dog> machos = dogService.findAllDogsFromBreedAndSex(dog,
					dog.getBreed(), "MALE");
			result.addObject("machos", machos);
			if (addFOMForm.getDogId() == null
					|| addFOMForm.getBoolMadre() == null
					|| addFOMForm.getParent() == null) {
				result.addObject("message", "register.error.params");
			} else {
				result.addObject("message", null);
			}
		} else {
			try {
				Dog dog = dogService.reconstructAddParent(addFOMForm);
				dogService.saveAndFlush(dog);
				result = new ModelAndView("redirect:list.do");
				result.addObject("actor", "participant/");
			} catch (Throwable oops) {

				result = new ModelAndView("dog/participant/addFather");
				result.addObject("addMother", false);
				result.addObject("addFOMForm", addFOMForm);
				Dog dog = dogService.findOneToShow(addFOMForm.getDogId());
				Collection<Dog> machos = dogService.findAllDogsFromBreedAndSex(
						dog, dog.getBreed(), "MALE");
				result.addObject("machos", machos);
				if (addFOMForm.getDogId() == null
						|| addFOMForm.getBoolMadre() == null
						|| addFOMForm.getParent() == null) {
					result.addObject("message", "register.error.params");
				} else {
					result.addObject("message", null);
				}

			}
		}
		return result;
	}
	
	//Delete
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int dogId) {
			ModelAndView result = new ModelAndView("redirect:list.do");
			result.addObject("actor", "participant/");
			Assert.notNull(dogId);
			try {
				dogService.delete(dogId);
			} catch (Throwable oops) {
				result.addObject("message", "contest.commit.error");
			}
			return result;
		}
}
