package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	public Message() {
		super();
		folders = new HashSet<Folder>();
	}

	private Date moment;
	private String subject;
	private String body;

	@NotBlank
	@Length(min = 1, max = 200)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@NotBlank
	@Length(min = 1, max = 20)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	private Participant sender;

	@NotNull
	@ManyToOne(optional = false)
	public Participant getSender() {
		return sender;
	}

	public void setSender(Participant sender) {
		this.sender = sender;
	}

	private Participant receiver;

	@NotNull
	@ManyToOne(optional = false)
	public Participant getReceiver() {
		return receiver;
	}

	public void setReceiver(Participant receiver) {
		this.receiver = receiver;
	}

	private Collection<Folder> folders;

	@Valid
	@ManyToMany
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}
}
