package services;

import java.util.Collection;
import java.util.HashSet;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DogRepository;
import domain.Breed;
import domain.Dog;
import domain.Groups;
import domain.Participant;
import domain.Result;
import forms.AddFOMForm;
import forms.DogForm;

@Service
@Transactional
public class DogService {

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private ParticipantService participantService;

	public Collection<Dog> findDogWithPoints() {

		return dogRepository.findDogsWithPoints();

	}

	public Collection<Dog> getAllDogs(String text) {
		Collection<Dog> res = new HashSet<Dog>();
		Collection<Dog> name = dogRepository.findSearchDogName(text);
		Collection<Dog> surname = dogRepository.findSearchDogNickName(text);
		for (Dog a : name) {
			if (!res.contains(a))
				res.add(a);
		}
		for (Dog a : surname) {
			if (!res.contains(a))
				res.add(a);
		}
		return res;
	}

	public Collection<Dog> findAllDogsFromParticipantById(int id) {
		return dogRepository.findAllByParticipantId(id);
	}

	public Dog findOneToShow(int dogId) {

		return dogRepository.findOne(dogId);
	}

	public void updateAll(Dog d) {
		Assert.isTrue(d.getCanParticipate());
		dogRepository.saveAndFlush(d);
	}

	public DogForm constructCreate() {
		DogForm res = new DogForm();
		res.setHeight(0.0);
		res.setWeight(0);
		return res;
	}

	public Dog reconstruct(DogForm dogForm) {
		Dog dog = new Dog();
		dog.setBreed(dogForm.getBreed());
		dog.setCanParticipate(false);
		dog.setFather(null);
		dog.setGroups(new HashSet<Groups>());
		dog.setHeight(dogForm.getHeight());
		dog.setMother(null);
		dog.setName(dogForm.getName());
		dog.setNickname(dogForm.getNickname());
		Participant p = participantService.findByPrincipal();
		dog.setParticipant(p);
		dog.setResults(new HashSet<Result>());
		dog.setSex(dogForm.getSex());
		dog.setTotalPoints(0);
		dog.setWeight(dogForm.getWeight());
		return dog;
	}

	public void save(Dog dog) {
		Assert.isTrue(dog.getBreed() != null || dog.getHeight() != null
				|| dog.getName() != "" || dog.getNickname() != ""
				|| dog.getSex() != "" || dog.getWeight() != null);
		checkprincipal(dog);
		dogRepository.save(dog);

	}

	private void checkprincipal(Dog dog) {
		Assert.isTrue(dog.getParticipant().equals(
				participantService.findByPrincipal()));
	}

	public void saveAndFlush(Dog dog) {
		dogRepository.saveAndFlush(dog);
	}

	public AddFOMForm constructAddParentForm(int dogId) {
		AddFOMForm res = new AddFOMForm();
		res.setDogId(dogId);
		return res;
	}

	public Collection<Dog> findAllDogsFromBreedAndSex(Dog dog, Breed breed,
			String string) {
		Collection<Dog> dogs = dogRepository.findAllDogsFromBreedAndSex(
				breed.getName(), string);
		Collection<Dog> res = new HashSet<Dog>();
		for (Dog a : dogs) {
			if (!a.equals(dog)) {
				if (a.getFather() != dog && a.getMother() != dog) {
					res.add(a);
				}
			}
		}
		return res;
	}

	public Dog reconstructAddParent(AddFOMForm addFOMForm) {
		Dog dog = dogRepository.findOne(addFOMForm.getDogId());
		if (addFOMForm.getBoolMadre() == true) {
			dog.setMother(addFOMForm.getParent());
		} else if (addFOMForm.getBoolMadre() == false) {
			dog.setFather(addFOMForm.getParent());
		} else {
			throw new IllegalAddException("No es padre ni madre");
		}

		return dog;
	}

	public void delete(int dogId) {
		Dog dog = dogRepository.findOne(dogId);
		Assert.isTrue(dog.getRequest().getStatus().equals("REJECTED")
				|| dog.getGroups().size() == 0);
		dogRepository.delete(dog);

	}

	public Collection<Dog> findAllDogsFromBreedAndParticipant(Breed breed) {
		Participant p = participantService.findByPrincipal();
		return dogRepository.findAllDogsFromBreed(breed.getName(), p.getId());
	}

}
