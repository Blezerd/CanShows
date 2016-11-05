package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
	
	@Query("select f.messages from Folder f where f.participant.id=?1")
	Collection<Folder> findFolderParticipant(int id);
	
	@Query("select f from Folder f where f.participant.id=?1")
	Collection<Folder> findNameFolderParticipant(int id);

}
