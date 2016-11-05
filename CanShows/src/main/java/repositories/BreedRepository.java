package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Integer> {

	@Query("Select b from Breed b where b.name=?1")
	Breed getBreedByName(String name);

}
