package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import domain.Dog;

@Access(AccessType.PROPERTY)
public class AddFOMForm {

	private Dog parent;
	private Boolean boolMadre;
	private Integer dogId;

	@NotNull
	public Dog getParent() {
		return parent;
	}

	public void setParent(Dog parent) {
		this.parent = parent;
	}

	public Boolean getBoolMadre() {
		return boolMadre;
	}

	public void setBoolMadre(Boolean boolMadre) {
		this.boolMadre = boolMadre;
	}

	@NotNull
	public Integer getDogId() {
		return dogId;
	}

	public void setDogId(Integer dogId) {
		this.dogId = dogId;
	}

}
