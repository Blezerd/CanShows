package domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList = "title") })
public class Competition extends DomainEntity {

	public Competition() {
		super();
		judges = new HashSet<Judge>();
		results = new HashSet<Result>();
		groups = new HashSet<Groups>();
	}

	private String title;
	private String type;
	private String adress;
	private Double inscriptionPrice;
	private String firstPrize;
	private String secondPrize;
	private String thirdPrize;
	private Integer minimumPoints;
	private Boolean resultsPublished;
	private Date celebrationDate;
	private Date inscriptionLimitDate;

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@Pattern(regexp = "^WORK|BEAUTY$")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotBlank
	@Length(min = 5, max = 200)
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@NotNull
	@Min(0)
	public Double getInscriptionPrice() {
		return inscriptionPrice;
	}

	public void setInscriptionPrice(Double inscriptionPrice) {
		this.inscriptionPrice = inscriptionPrice;
	}

	@NotBlank
	public String getFirstPrize() {
		return firstPrize;
	}

	public void setFirstPrize(String firstPrize) {
		this.firstPrize = firstPrize;
	}

	@NotBlank
	public String getSecondPrize() {
		return secondPrize;
	}

	public void setSecondPrize(String secondPrize) {
		this.secondPrize = secondPrize;
	}

	@NotBlank
	public String getThirdPrize() {
		return thirdPrize;
	}

	public void setThirdPrize(String thirdPrize) {
		this.thirdPrize = thirdPrize;
	}

	@NotNull
	@Min(0)
	public Integer getMinimumPoints() {
		return minimumPoints;
	}

	public void setMinimumPoints(Integer minimumPoints) {
		this.minimumPoints = minimumPoints;
	}

	@NotNull
	public Boolean getResultsPublished() {
		return resultsPublished;
	}

	public void setResultsPublished(Boolean resultsPublished) {
		this.resultsPublished = resultsPublished;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCelebrationDate() {
		return celebrationDate;
	}

	public void setCelebrationDate(Date celebrationDate) {
		this.celebrationDate = celebrationDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getInscriptionLimitDate() {
		return inscriptionLimitDate;
	}

	public void setInscriptionLimitDate(Date inscriptionLimitDate) {
		this.inscriptionLimitDate = inscriptionLimitDate;
	}

	private Organiser organiser;

	@Valid
	@ManyToOne(optional = false)
	public Organiser getOrganiser() {
		return organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

	private Collection<Judge> judges;

	@Valid
	@ManyToMany(mappedBy = "competitions")
	public Collection<Judge> getJudges() {
		return judges;
	}

	public void setJudges(Collection<Judge> judges) {
		this.judges = judges;
	}

	private Collection<Result> results;

	@Valid
	@OneToMany(mappedBy = "competition")
	public Collection<Result> getResults() {
		return results;
	}

	public void setResults(Collection<Result> results) {
		this.results = results;
	}

	private Collection<Groups> groups;

	@Valid
	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	public Collection<Groups> getGroups() {
		return groups;
	}

	public void setGroups(Collection<Groups> groups) {
		this.groups = groups;
	}

}
