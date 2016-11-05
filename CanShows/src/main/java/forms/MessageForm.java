package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import domain.Participant;

@Access(AccessType.PROPERTY)
public class MessageForm {
	
	
	private String subject;
	private String body;
	private Participant participant;
	
	
	@NotBlank
	@Length(min = 1, max = 20)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@NotBlank
	@Length(min = 1, max = 200)
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@NotNull
	public Participant getParticipant() {
		return participant;
	}
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

}
