package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Competition;

@Repository
public interface CompetitionRepository extends
		JpaRepository<Competition, Integer> {

	@Query("select j.competitions from Judge j where j.id=?1")
	Collection<Competition> findAllByJudgeId(int id);

	@Query("select c from Competition c where c.title=?1")
	Competition findOneByTitle(String title);

}
