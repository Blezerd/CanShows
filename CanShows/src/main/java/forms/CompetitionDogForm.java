package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import domain.Dog;
import domain.Groups;

@Access(AccessType.PROPERTY)
public class CompetitionDogForm {

	private Integer compId;
	private Groups grupo;
	private Dog dog;

	@NotNull
	public Integer getCompId() {
		return compId;
	}

	public void setCompId(Integer compId) {
		this.compId = compId;
	}

	@NotNull
	public Groups getGrupo() {
		return grupo;
	}

	public void setGrupo(Groups grupo) {
		this.grupo = grupo;
	}

	@NotNull
	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}
}
