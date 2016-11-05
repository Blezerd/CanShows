package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Judge;

@Repository
public interface JudgeRepository extends JpaRepository<Judge, Integer> {

	@Query("select j from Judge j where j.userAccount.id=?1")
	Judge getJudgeByUserAccount(int id);

	@Query("select j from Judge j where j.userAccount.username=?1")
	Judge getJudgeByUserName(String username);

	@Query("select j from Judge j where j.judgeNumber=?1")
	Judge getJudgeNumberUsed(Integer judgeNumber);

}
