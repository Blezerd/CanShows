package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ResultRepository;
import domain.Dog;
import domain.Participant;
import domain.Result;

@Service
@Transactional
public class ResultService {

	@Autowired
	private ResultRepository resultRepository;

	@Autowired
	private DogService dogService;

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private JudgeService judgeService;

	public Collection<Result> findAllFromParticipantDog(int dogId) {
		Dog dog = dogService.findOneToShow(dogId);
		Participant p = participantService.findByPrincipal();
		Assert.isTrue(dog.getParticipant().equals(p));
		if (dog.getResults().isEmpty()) {
			return new HashSet<Result>();
		} else {
			return dog.getResults();
		}
	}

	public Collection<Result> findResultsForFriendDog(int dogId) {
		Dog dog = dogService.findOneToShow(dogId);
		return dog.getResults();
	}

	public Result create(int dogId, int competitionId) {
		Result result = new Result();
		result.setCompetition(competitionService.findOneToShow(competitionId));
		result.setDog(dogService.findOneToShow(dogId));
		result.setJudge(judgeService.findByPrincipal());
		return result;
	}

	public void save(Result resultado) {
		Assert.isTrue(resultado.getComment() != ""
				|| resultado.getPoints() != null
				|| resultado.getPosition() != null);
		resultRepository.saveAndFlush(resultado);
		Dog dog = resultado.getDog();
		dog.setTotalPoints(dog.getTotalPoints() + resultado.getPoints());
		dogService.saveAndFlush(dog);
	}

}
