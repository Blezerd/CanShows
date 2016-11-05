package services;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import utilities.PopulateDatabase;
import domain.Competition;
import domain.Dog;
import domain.Judge;
import domain.Result;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class JudgeServiceTest extends AbstractTest {

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private CompetitionService competitionService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private DogService dogService;

	@Before
	public void setUp() {
		PopulateDatabase.main(null);

	}

	// Este método comprueba que se muestran las competiciones que juzga un
	// determinado juez
	@Test
	public void testFindCompetitionJudge() {
		authenticate("judge1");
		Judge j = judgeService.findByPrincipal();
		Collection<Competition> competitions;
		competitions = competitionService.findAllCompetitionsFromJudgeById(j
				.getId());
		Assert.isTrue(competitions.size() == 3);

	}

	// Este método comprueba que un juez juzga a un perro
	public void testJudgeDog() {
		authenticate("judge2");
		Judge j = judgeService.findByPrincipal();
		Dog d = dogService.findOneToShow(90);
		Collection<Result> r = d.getResults();
		Result r1 = new Result();
		r1.setPosition(1);
		r1.setPoints(25);
		r1.setDog(d);
		r1.setJudge(j);
		r1.setComment("lalalal");
		r1.setCompetition(competitionService.findOneToShow(80));
		r.add(r1);
		d.setResults(r);
		resultService.save(r1);
		Assert.isTrue(d.getResults().iterator().next().getPoints().equals(25));

	}

	// Negativos

	// Este método comprueba que un juez no puede juzgar a un perro
	// si los datos no son correctos
	@Test(expected = DataIntegrityViolationException.class)
	@Rollback(value = true)
	public void testBadJudgeDog() {
		authenticate("judge2");
		Dog d = dogService.findOneToShow(90);
		Collection<Result> r = d.getResults();
		Result r1 = new Result();
		r1.setPosition(1);
		r1.setPoints(25);
		r1.setDog(d);

		r1.setComment("lalalal");
		r1.setCompetition(competitionService.findOneToShow(80));
		r.add(r1);
		d.setResults(r);
		resultService.save(r1);
		Assert.isTrue(d.getResults().iterator().next().getPoints().equals(25));

	}

}
