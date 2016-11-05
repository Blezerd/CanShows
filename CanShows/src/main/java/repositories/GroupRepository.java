package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Groups;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Integer> {

}
