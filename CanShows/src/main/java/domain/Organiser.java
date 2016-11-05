package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Organiser extends Customer {

	public Organiser() {
		super();
		setCompetitions(new HashSet<Competition>());
	}

	private Collection<Competition> competitions;

	@Valid
	@OneToMany(mappedBy = "organiser")
	public Collection<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Collection<Competition> competitions) {
		this.competitions = competitions;
	}

}
