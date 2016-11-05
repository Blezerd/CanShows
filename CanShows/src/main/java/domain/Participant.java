package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Embeddable
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "name"),@Index(columnList = "surname"),
		@Index(columnList = "email")})
public class Participant extends Customer {

	public Participant() {
		super();
		dogs = new HashSet<Dog>();
		friends = new HashSet<Participant>();
	}

	private Collection<Dog> dogs;

	@Valid
	@OneToMany(mappedBy = "participant")
	public Collection<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(Collection<Dog> dogs) {
		this.dogs = dogs;
	}

	private Collection<Folder> folders;

	@Valid
	@OneToMany(mappedBy = "participant")
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> inbox) {
		this.folders = inbox;
	}

	private Collection<Participant> friends;

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Participant> getFriends() {
		return friends;
	}

	public void setFriends(Collection<Participant> friends) {
		this.friends = friends;
	}
}
