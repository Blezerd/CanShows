package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Access(AccessType.PROPERTY)
public class JudgeForm extends ActorForm {

	private Integer judgeNumber;

	@NotNull
	@Column(unique = true)
	public Integer getJudgeNumber() {
		return judgeNumber;
	}

	public void setJudgeNumber(Integer judgeNumber) {
		this.judgeNumber = judgeNumber;
	}

}
