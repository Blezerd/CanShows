package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BreedRepository;
import domain.Breed;
import domain.Dog;
import domain.Groups;
import forms.BreedForm;

@Service
@Transactional
public class BreedService {

	@Autowired
	private BreedRepository breedRepository;

	public Collection<Breed> findAllBreeds() {
		return breedRepository.findAll();
	}

	public Breed findOne(int id) {
		Assert.notNull(id);
		return breedRepository.findOne(id);
	}

	public Breed reconstruct(BreedForm breedForm) {
		Breed breed = new Breed();
		breed.setDescription(breedForm.getDescription());
		breed.setName(breedForm.getName());
		breed.setDogs(new HashSet<Dog>());
		breed.setGroups(new HashSet<Groups>());
		return breed;
	}

	public void save(Breed breed) {
		Assert.notNull(breed);
		Assert.isTrue(breed.getName() != "" && breed.getDescription() != "");
		Assert.isTrue(this.nameBreedAvailable(breed.getName()));
		breedRepository.save(breed);

	}

	public boolean nameBreedAvailable(String name) {
		Boolean res = false;
		if (breedRepository.getBreedByName(name) == null) {
			res = true;
		}
		return res;
	}

	public Breed findOneToDetails(int breedId) {
		return breedRepository.findOne(breedId);
	}

}
