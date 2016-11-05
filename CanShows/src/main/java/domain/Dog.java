package domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "totalPoints"),@Index(columnList = "sex"),
		@Index(columnList = "canParticipate")})
public class Dog extends DomainEntity {

	public Dog() {
		super();
		results = new HashSet<Result>();
		groups = new HashSet<Groups>();
	}

	private String name;
	private String nickname;
	private Integer weight;
	private Double height;
	private String sex;
	private Integer totalPoints;
	private Boolean canParticipate;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@NotNull
	@Min(1)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@NotNull
	@Min(0)
	@Digits(fraction = 2, integer = 3)
	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	@NotNull
	@Min(0)
	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	@NotNull
	public Boolean getCanParticipate() {
		return canParticipate;
	}

	public void setCanParticipate(Boolean canParticipate) {
		this.canParticipate = canParticipate;
	}

	@NotNull
	@Pattern(regexp = "^MALE|FEMALE$")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	private Request request;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	private Collection<Result> results;

	@Valid
	@OneToMany(mappedBy = "dog")
	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}

	private Participant participant;

	@Valid
	@ManyToOne(optional = false)
	public Participant getParticipant() {
		return participant;
	}

	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	private Breed breed;

	@Valid
	@ManyToOne(optional = false)
	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	private Dog father;

	@Valid
	@ManyToOne(optional = true)
	public Dog getFather() {
		return father;
	}

	public void setFather(Dog father) {
		this.father = father;
	}

	private Dog mother;

	@Valid
	@ManyToOne(optional = true)
	public Dog getMother() {
		return mother;
	}

	public void setMother(Dog mother) {
		this.mother = mother;
	}

	private Collection<Groups> groups;

	@Valid
	@ManyToMany
	public Collection<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Collection<Groups> groups) {
		this.groups = groups;
	}
}
