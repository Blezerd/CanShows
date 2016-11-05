package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Participant;

@Repository
public interface ParticipantRepository extends
		JpaRepository<Participant, Integer> {

	@Query("select p from Participant p where p.userAccount.id=?1")
	Participant getParticipantByUserAccount(int id);

	@Query("select p from Participant p where p.userAccount.username=?1")
	Participant getParticipantByUserName(String username);

	@Query("select p from Participant p where p.name like concat('%',?1,'%')")
	Collection<Participant> findSearchParticipantName(String text);

	@Query("select p from Participant p where p.surname like concat('%',?1,'%')")
	Collection<Participant> findSearchParticipantSurName(String text);

	@Query("select p from Participant p where p.email like concat('%',?1,'%')")
	Collection<Participant> findSearchParticipantEmail(String text);

}
