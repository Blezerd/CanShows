package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Dog;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	@Query("select p.folders from Participant p where p.id=?1")
	Collection<Message> findAllByParticipantId(int id);
	
	@Query("select f.messages from Folder f where f.id=?1")
	Collection<Message> findAllByFolderId(int id);

}
