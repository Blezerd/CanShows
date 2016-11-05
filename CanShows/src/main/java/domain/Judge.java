package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Judge extends Actor {

	public Judge() {
		super();
		competitions = new HashSet<Competition>();
		results = new HashSet<Result>();
	}

	private Integer judgeNumber;

	@NotNull
	@Column(unique = true)
	public Integer getJudgeNumber() {
		return judgeNumber;
	}

	public void setJudgeNumber(Integer judgeNumber) {
		this.judgeNumber = judgeNumber;
	}

	private Collection<Competition> competitions;

	@Valid
	@ManyToMany
	public Collection<Competition> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Collection<Competition> competitions) {
		this.competitions = competitions;
	}

	private Collection<Result> results;

	@Valid
	@OneToMany(mappedBy = "judge")
	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}
}
