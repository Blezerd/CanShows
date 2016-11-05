package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

}
