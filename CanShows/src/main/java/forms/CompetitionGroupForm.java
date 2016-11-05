package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

import domain.Groups;

@Access(AccessType.PROPERTY)
public class CompetitionGroupForm {

	private Integer compId;
	private Groups grupo;

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
}
