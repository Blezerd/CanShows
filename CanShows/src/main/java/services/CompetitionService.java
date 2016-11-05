package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CompetitionRepository;
import repositories.DogRepository;
import repositories.GroupRepository;
import domain.Competition;
import domain.Dog;
import domain.Groups;
import domain.Judge;
import domain.Participant;
import domain.Result;
import forms.CompetitionAddGroupForm;
import forms.CompetitionAddJudgeForm;
import forms.CompetitionGroupForm;
import forms.CompetitionOrganiserForm;

@Service
@Transactional
public class CompetitionService {

	@Autowired
	private CompetitionRepository competitionRepository;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private OrganiserService organiserService;

	public Competition findOneToShow(int competitionId) {
		return competitionRepository.findOne(competitionId);
	}

	public Collection<Competition> findAllCompetitionsFromJudgeById(int id) {
		return competitionRepository.findAllByJudgeId(id);
	}

	public Collection<Competition> findAllCompetitionsAvailables() {
		Collection<Competition> res = competitionRepository.findAll();
		Collection<Competition> resu = new HashSet<Competition>();
		Date now = new Date(System.currentTimeMillis());
		for (Competition c : res) {
			if (c.getInscriptionLimitDate().after(now)) {
				resu.add(c);
			}
		}
		return resu;
	}

	public Collection<Competition> findAllCompetitionsFromParticipantById(int id) {
		Collection<Competition> res = new HashSet<Competition>();
		Participant p = participantService.findByPrincipal();
		for (Dog dog : p.getDogs()) {
			for (Groups groups : dog.getGroups()) {
				res.add(groups.getCompetition());
			}
		}
		return res;
	}

	public CompetitionGroupForm constructCompGroupForm(int id) {
		CompetitionGroupForm res = new CompetitionGroupForm();
		res.setCompId(id);
		return res;
	}

	public forms.CompetitionDogForm constructCompGroupForm(
			CompetitionGroupForm competitionGroupForm) {
		forms.CompetitionDogForm res = new forms.CompetitionDogForm();
		res.setCompId(competitionGroupForm.getCompId());
		res.setGrupo(competitionGroupForm.getGrupo());
		return res;
	}

	public void joinDogToGroupAndCompetition(Integer compId, Groups grupo,
			Dog dog) {
		Assert.notNull(compId);
		Competition c = this.findOneToShow(compId);
		Collection<Groups> gruposPerro = dog.getGroups();
		Collection<Dog> perrosGrupo = grupo.getDogs();

		perrosGrupo.add(dog);
		grupo.setDogs(perrosGrupo);
		grupo.setCompetition(c);
		groupRepository.saveAndFlush(grupo);

		gruposPerro.add(grupo);
		dog.setGroups(gruposPerro);
		dogRepository.saveAndFlush(dog);

	}

	public CompetitionOrganiserForm constructCompOrgForm() {
		return new CompetitionOrganiserForm();
	}

	public Competition reconstructOrganiserForm(
			CompetitionOrganiserForm competitionOrganiserForm) {
		Competition competition = new Competition();
		competition.setAdress(competitionOrganiserForm.getAdress());
		competition.setCelebrationDate(competitionOrganiserForm
				.getCelebrationDate());
		competition.setFirstPrize(competitionOrganiserForm.getFirstPrize());
		competition.setGroups(new HashSet<Groups>());
		competition.setInscriptionLimitDate(competitionOrganiserForm
				.getInscriptionLimitDate());
		competition.setInscriptionPrice(competitionOrganiserForm
				.getInscriptionPrice());
		competition.setJudges(new HashSet<Judge>());
		competition.setMinimumPoints(competitionOrganiserForm
				.getMinimumPoints());
		competition.setOrganiser(organiserService.findByPrincipal());
		competition.setResults(new HashSet<Result>());
		competition.setResultsPublished(false);
		competition.setSecondPrize(competitionOrganiserForm.getSecondPrize());
		competition.setThirdPrize(competitionOrganiserForm.getThirdPrize());
		competition.setTitle(competitionOrganiserForm.getTitle());
		competition.setType(competitionOrganiserForm.getType());

		return competition;
	}

	public void saveCreate(Competition competition) {
		Date now = new Date(System.currentTimeMillis());
		Assert.isTrue(competition.getAdress() != ""
				|| competition.getCelebrationDate() != null
				|| competition.getFirstPrize() != ""
				|| competition.getInscriptionLimitDate() != null
				|| competition.getInscriptionPrice() != null
				|| competition.getMinimumPoints() != null
				|| competition.getSecondPrize() != ""
				|| competition.getThirdPrize() != ""
				|| competition.getTitle() != "" || competition.getType() != "");
		if (competition.getCelebrationDate().before(
				competition.getInscriptionLimitDate())) {
			throw new IllegalArgumentException("Incorrect Dates");
		} else if (competition.getCelebrationDate().before(now)
				|| competition.getInscriptionLimitDate().before(now)) {
			throw new IllegalArgumentException("Incorrect Dates");
		}
		competitionRepository.saveAndFlush(competition);
	}

	public CompetitionOrganiserForm constructCompOrgFormEdit(
			Competition competition) {
		CompetitionOrganiserForm competitionOrganiserForm = new CompetitionOrganiserForm();
		competitionOrganiserForm.setAdress(competition.getAdress());
		competitionOrganiserForm.setCelebrationDate(competition
				.getCelebrationDate());
		competitionOrganiserForm.setFirstPrize(competition.getFirstPrize());
		competitionOrganiserForm.setInscriptionLimitDate(competition
				.getInscriptionLimitDate());
		competitionOrganiserForm.setInscriptionPrice(competition
				.getInscriptionPrice());
		competitionOrganiserForm.setMinimumPoints(competition
				.getMinimumPoints());
		competitionOrganiserForm.setSecondPrize(competition.getSecondPrize());
		competitionOrganiserForm.setThirdPrize(competition.getThirdPrize());
		competitionOrganiserForm.setTitle(competition.getTitle());
		competitionOrganiserForm.setType(competition.getType());
		return competitionOrganiserForm;
	}

	public Competition reconstructOrganiserFormEdit(
			CompetitionOrganiserForm competitionOrganiserForm) {
		Competition competition = null;
		for (Competition c : organiserService.findByPrincipal()
				.getCompetitions()) {
			if (c.getTitle().contains(competitionOrganiserForm.getTitle())) {
				competition = c;
			}
		}
		competition.setCelebrationDate(competitionOrganiserForm
				.getCelebrationDate());
		competition.setFirstPrize(competitionOrganiserForm.getFirstPrize());
		competition.setInscriptionLimitDate(competitionOrganiserForm
				.getInscriptionLimitDate());
		competition.setSecondPrize(competitionOrganiserForm.getSecondPrize());
		competition.setThirdPrize(competitionOrganiserForm.getThirdPrize());
		return competition;
	}

	public void saveEdit(Competition competition) {
		Date now = new Date(System.currentTimeMillis());
		Assert.isTrue(competition.getAdress() != ""
				|| competition.getCelebrationDate() != null
				|| competition.getFirstPrize() != ""
				|| competition.getInscriptionLimitDate() != null
				|| competition.getInscriptionPrice() != null
				|| competition.getMinimumPoints() != null
				|| competition.getSecondPrize() != ""
				|| competition.getThirdPrize() != ""
				|| competition.getTitle() != "" || competition.getType() != "");
		if (competition.getCelebrationDate().before(
				competition.getInscriptionLimitDate())) {
			throw new IllegalArgumentException("Incorrect Dates");
		} else if (competition.getCelebrationDate().before(now)
				|| competition.getInscriptionLimitDate().before(now)) {
			throw new IllegalArgumentException("Incorrect Dates");
		}
		competitionRepository.save(competition);
	}

	public CompetitionAddJudgeForm constructAddJudgeForm(Competition competition) {
		CompetitionAddJudgeForm res = new CompetitionAddJudgeForm();
		res.setId(competition.getId());
		return res;

	}

	public Competition reconstructAddJudge(
			CompetitionAddJudgeForm competitionAddJudgeForm) {
		Competition c = competitionRepository.findOne(competitionAddJudgeForm
				.getId());
		Collection<Judge> judges = c.getJudges();
		judges.add(competitionAddJudgeForm.getJudge());
		c.setJudges(judges);
		Judge j = judgeService.findOne(competitionAddJudgeForm.getJudge()
				.getId());
		Collection<Competition> com = j.getCompetitions();
		com.add(c);
		judgeService.save(j);
		return c;
	}

	public void saveJudge(Competition competitiona) {
		competitionRepository.saveAndFlush(competitiona);
	}

	public void publish(int competitionId) {
		Competition c = competitionRepository.findOne(competitionId);
		c.setResultsPublished(true);
		competitionRepository.saveAndFlush(c);
	}

	public void delete(int competitionId) {
		Competition c = competitionRepository.findOne(competitionId);
		for (Judge a : c.getJudges()) {
			Collection<Competition> com = a.getCompetitions();
			com.remove(c);
			a.setCompetitions(com);
			judgeService.save(a);
		}
		competitionRepository.delete(c);
	}

	public Competition findOneByTitle(String title) {
		return competitionRepository.findOneByTitle(title);
	}

	public boolean exist(String title) {
		Boolean res = true;
		if (competitionRepository.findOneByTitle(title) == null) {
			res = false;
		}
		return res;
	}

	public CompetitionAddGroupForm constructAddGroupForm(Competition competition) {
		CompetitionAddGroupForm res = new CompetitionAddGroupForm();
		res.setId(competition.getId());
		return res;
	}

	public Competition reconstructAddGroup(
			CompetitionAddGroupForm competitionAddGroupForm) {
		Competition c = competitionRepository.findOne(competitionAddGroupForm
				.getId());
		Collection<Groups> groups = c.getGroups();
		Groups g = new Groups();
		g.setBreed(competitionAddGroupForm.getBreed());
		g.setCompetition(c);
		g.setDogs(new HashSet<Dog>());
		g.setNumberOfDogs(competitionAddGroupForm.getNumberOfDogs());
		g.setRing(competitionAddGroupForm.getRing());
		groups.add(g);
		c.setGroups(groups);
		return c;
	}

	public void saveGroup(Competition competitiona) {
		competitionRepository.saveAndFlush(competitiona);
	}

}
