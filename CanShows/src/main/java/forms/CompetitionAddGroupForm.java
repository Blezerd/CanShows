package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Breed;

@Access(AccessType.PROPERTY)
public class CompetitionAddGroupForm {

	private Integer id;
	private Breed breed;
	private Integer numberOfDogs;
	private Integer ring;

	@NotNull
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

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

}
