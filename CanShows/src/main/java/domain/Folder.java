package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Embeddable
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	public Folder() {
		super();
		messages = new HashSet<Message>();
	}

	private String name;

	@NotBlank
	@Length(min = 5, max = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Collection<Message> messages;

	@Valid
	@ManyToMany(mappedBy = "folders")
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> hashSet) {
		this.messages = hashSet;
	}

	private Participant participant;

	@Valid
	@ManyToOne
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}
