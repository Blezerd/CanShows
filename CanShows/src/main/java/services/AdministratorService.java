package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import repositories.DogRepository;
import repositories.OrganiserRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;
import domain.Dog;
import domain.Organiser;
import domain.Request;
import forms.ActorForm;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private OrganiserRepository organiserRepository;

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private JudgeService judgeService;

	public boolean userRegistered(String username) {
		Boolean res = true;
		if (administratorRepository.isRegistered(username) == null) {
			res = false;
		}
		return res;
	}

	public void save(Administrator administrator) {
		checkPrincipal();
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getName() != ""
				&& administrator.getSurname() != "");
		administratorRepository.saveAndFlush(administrator);

	}

	public Administrator findOne(int id) {
		Assert.notNull(id);
		return administratorRepository.findOne(id);
	}

	public Administrator findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return administratorRepository
				.getAdministratorByUserAccount(userAccount.getId());

	}

	public Administrator findByPrincipal() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Assert.notNull(user);
		Administrator administrator = this.findByUserAccount(user);
		return administrator;
	}

	public Collection<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	public Administrator reconstruct(ActorForm adminForm) {
		Boolean contraseña = false;

		if (adminForm.getPassword() != null && adminForm.getPassword2() != null) {
			contraseña = adminForm.getPassword2().equals(
					adminForm.getPassword());
		}
		if (!contraseña) {
			throw new IllegalArgumentException("not same password");
		} else if (!adminForm.getIsCondition()) {
			throw new IllegalArgumentException("notCondition");
		}
		if (this.userRegistered(adminForm.getUsername())
				|| participantService.userRegistered(adminForm.getUsername())
				|| organiserService.userRegistered(adminForm.getUsername())
				|| judgeService.userRegistered(adminForm.getUsername())) {
			throw new IllegalArgumentException("duplicated");

		}
		Administrator a = new Administrator();
		a.setEmail(adminForm.getEmail());
		a.setHomePage(adminForm.getHomePage());
		a.setName(adminForm.getName());
		a.setPhone(adminForm.getPhone());
		a.setSurname(adminForm.getSurname());
		a.setRequests(new HashSet<Request>());
		a.setNationality(adminForm.getNationality());
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(adminForm.getUsername());

		String password = adminForm.getPassword();

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);

		userAccount.setPassword(md5);

		Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		a.setUserAccount(userAccount);

		return a;
	}

	public void checkPrincipal() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Assert.notNull(user);
		Administrator administrator = this.findByUserAccount(user);
		Assert.isTrue(findByPrincipal() == administrator);
	}

	public Collection<Organiser> findOrganisersWithCompetition() {
		checkPrincipal();
		return organiserRepository.findAll();

	}

	public Collection<Dog> findDogsWithPoints() {
		checkPrincipal();
		return dogRepository.findDogsWithPoints();

	}

	public Dog findBestDog() {
		Collection<Dog> dogs = this.findDogsWithPoints();
		return dogs.iterator().next();

	}
}