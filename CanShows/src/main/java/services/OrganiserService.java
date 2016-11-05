package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.OrganiserRepository;
import security.Authority;
import security.UserAccount;
import domain.Competition;
import domain.Organiser;
import forms.CustomerForm;

@Service
@Transactional
public class OrganiserService {

	@Autowired
	private OrganiserRepository organiserRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private JudgeService judgeService;

	@SuppressWarnings("deprecation")
	public Organiser reconstruct(@Valid CustomerForm organiserForm) {
		Assert.notNull(organiserForm);

		Integer mescc = organiserForm.getCreditCard().getExpirationMonth();
		Integer añocc = organiserForm.getCreditCard().getExpirationYear();
		int mes = new Date(System.currentTimeMillis()).getMonth() + 1;
		int año = new Date(System.currentTimeMillis()).getYear() + 1900;

		Boolean contraseña = false;

		if (organiserForm.getPassword() != null
				&& organiserForm.getPassword2() != null) {
			contraseña = organiserForm.getPassword2().equals(
					organiserForm.getPassword());
		}
		if (!contraseña) {
			throw new IllegalArgumentException("not same password");
		} else if (!organiserForm.getIsCondition()) {
			throw new IllegalArgumentException("notCondition");
		}
		if (organiserForm.getCreditCard().getExpirationMonth() == null
				|| organiserForm.getCreditCard().getExpirationYear() == null) {
			throw new IllegalArgumentException("BadCreditCard");
		} else {
			if (mescc < mes) {
				if (!(añocc > año)) {
					throw new IllegalArgumentException("BadCreditCard");
				}
			} else if (mescc == mes) {
				if (!(añocc >= año)) {
					throw new IllegalArgumentException("BadCreditCard");
				}

			} else if (mescc > mes) {
				if (!(añocc >= año)) {
					throw new IllegalArgumentException("BadCreditCard");
				}
			}
		}
		if (this.userRegistered(organiserForm.getUsername())
				|| administratorService.userRegistered(organiserForm
						.getUsername())
				|| participantService.userRegistered(organiserForm
						.getUsername())
				|| judgeService.userRegistered(organiserForm.getUsername())) {
			throw new IllegalArgumentException("duplicated");

		}
		Organiser organiser = new Organiser();
		organiser.setCreditCard(organiserForm.getCreditCard());
		organiser.setEmail(organiserForm.getEmail());
		organiser.setHomePage(organiserForm.getHomePage());
		organiser.setName(organiserForm.getName());
		organiser.setPhone(organiserForm.getPhone());
		organiser.setNationality(organiserForm.getNationality());
		organiser.setCompetitions(new HashSet<Competition>());
		organiser.setSurname(organiserForm.getSurname());

		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(organiserForm.getUsername());

		String password = organiserForm.getPassword();

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);

		userAccount.setPassword(md5);

		Authority authority = new Authority();
		authority.setAuthority("ORGANISER");

		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		organiser.setUserAccount(userAccount);

		return organiser;
	}

	public void save(@Valid Organiser organiser) {
		Assert.notNull(organiser);
		Assert.isTrue(organiser.getName() != "" && organiser.getSurname() != "");

		organiserRepository.saveAndFlush(organiser);
	}

	public Organiser findByPrincipal() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Assert.notNull(user);
		Organiser organiser = this.findByUserAccount(user);
		return organiser;
	}

	public Collection<Organiser> findAll() {
		return organiserRepository.findAll();
	}

	public Organiser findOne(int id) {
		Assert.notNull(id);
		return organiserRepository.findOne(id);
	}

	public Organiser findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return organiserRepository.getOrganiserByUserAccount(userAccount
				.getId());

	}

	public boolean userRegistered(String username) {
		Boolean res = true;
		if (organiserRepository.getOrganiserByUserName(username) == null) {
			res = false;
		}
		return res;
	}

	public void saveAndFlush(Organiser a) {
		organiserRepository.saveAndFlush(a);
	}

}
