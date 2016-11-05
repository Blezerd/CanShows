package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

	@Query("select d from Dog d order by d.totalPoints DESC")
	Collection<Dog> findDogsWithPoints();

	@Query("select d from Dog d where d.name like concat('%',?1,'%')")
	Collection<Dog> findSearchDogName(String text);

	@Query("select d from Dog d where d.nickname like concat('%',?1,'%')")
	Collection<Dog> findSearchDogNickName(String text);

	@Query("select p.dogs from Participant p where p.id=?1")
	Collection<Dog> findAllByParticipantId(int id);

	@Query("select d from Dog d where d.breed.name=?1 and d.sex=?2 and d.canParticipate IS TRUE")
	Collection<Dog> findAllDogsFromBreedAndSex(String breed, String string);

	@Query("select d from Dog d where d.breed.name=?1 and d.participant.id=?2 and d.canParticipate IS TRUE")
	Collection<Dog> findAllDogsFromBreed(String name, int i);

}
