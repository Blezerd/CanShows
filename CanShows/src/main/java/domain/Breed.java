package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "name") })
public class Breed extends DomainEntity {

	public Breed() {
		super();
		dogs = new HashSet<Dog>();
		groups = new HashSet<Groups>();
	}

	private String name;
	private String description;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@Length(min = 20, max = 10000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Collection<Groups> groups;

	@Valid
	@OneToMany(mappedBy = "breed")
	public Collection<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Collection<Groups> groups) {
		this.groups = groups;
	}

	private Collection<Dog> dogs;

	@Valid
	@OneToMany(mappedBy = "breed")
	public Collection<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(Collection<Dog> dogs) {
		this.dogs = dogs;
	}

}
