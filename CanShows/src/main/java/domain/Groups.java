package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Groups extends DomainEntity {

	public Groups() {
		super();
		dogs = new HashSet<Dog>();
	}

	private Integer numberOfDogs;
	private Integer ring;

	@NotNull
	@Min(1)
	public Integer getNumberOfDogs() {
		return numberOfDogs;
	}

	public void setNumberOfDogs(Integer numberOfDogs) {
		this.numberOfDogs = numberOfDogs;
	}

	@NotNull
	@Min(1)
	public Integer getRing() {
		return ring;
	}

	public void setRing(Integer ring) {
		this.ring = ring;
	}

	private Competition competition;

	@Valid
	@ManyToOne
	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(Competition competition) {
		this.competition = competition;
	}

	private Breed breed;

	@Valid
	@ManyToOne(optional = false)
	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	private Collection<Dog> dogs;

	@Valid
	@ManyToMany(mappedBy = "groups")
	public Collection<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(Collection<Dog> dogs) {
		this.dogs = dogs;
	}
}
