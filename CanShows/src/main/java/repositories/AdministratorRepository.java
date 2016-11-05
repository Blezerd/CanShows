package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends
		JpaRepository<Administrator, Integer> {

	@Query("Select a from Administrator a where a.userAccount.username=?1")
	Administrator isRegistered(String username);

	@Query("select a from Administrator a where a.userAccount.id=?1")
	Administrator getAdministratorByUserAccount(int id);

}
