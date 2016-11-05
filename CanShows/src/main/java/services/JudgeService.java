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

import repositories.JudgeRepository;
import security.Authority;
import security.UserAccount;
import domain.Competition;
import domain.Judge;
import domain.Result;
import forms.JudgeForm;

@Service
@Transactional
public class JudgeService {

	@Autowired
	private JudgeRepository judgeRepository;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private ParticipantService participantService;

	public boolean userRegistered(String username) {
		Boolean res = true;
		if (judgeRepository.getJudgeByUserName(username) == null) {
			res = false;
		}
		return res;
	}

	public void save(Judge judge) {
		Assert.notNull(judge);
		Assert.isTrue(judge.getName() != "" && judge.getSurname() != "");
		judgeRepository.saveAndFlush(judge);

	}

	public Judge findOne(int id) {
		Assert.notNull(id);
		return judgeRepository.findOne(id);
	}

	public Judge findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return judgeRepository.getJudgeByUserAccount(userAccount.getId());

	}

	public Judge findByPrincipal() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Assert.notNull(user);
		Judge judge = this.findByUserAccount(user);
		return judge;
	}

	public Collection<Judge> findAll() {
		return judgeRepository.findAll();
	}

	public Judge reconstruct(JudgeForm judgeForm) {
		Boolean contraseña = false;

		if (judgeForm.getPassword() != null && judgeForm.getPassword2() != null) {
			contraseña = judgeForm.getPassword2().equals(
					judgeForm.getPassword());
		}
		if (!contraseña) {
			throw new IllegalArgumentException("not same password");
		} else if (!judgeForm.getIsCondition()) {
			throw new IllegalArgumentException("notCondition");
		}
		if (this.userRegistered(judgeForm.getUsername())
				|| participantService.userRegistered(judgeForm.getUsername())
				|| organiserService.userRegistered(judgeForm.getUsername())
				|| administratorService.userRegistered(judgeForm.getUsername())) {
			throw new IllegalArgumentException("duplicated");

		}
		Judge a = new Judge();
		a.setEmail(judgeForm.getEmail());
		a.setHomePage(judgeForm.getHomePage());
		a.setName(judgeForm.getName());
		a.setPhone(judgeForm.getPhone());
		a.setSurname(judgeForm.getSurname());
		a.setCompetitions(new HashSet<Competition>());
		a.setNationality(judgeForm.getNationality());
		a.setJudgeNumber(judgeForm.getJudgeNumber());
		a.setResults(new HashSet<Result>());

		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(judgeForm.getUsername());

		String password = judgeForm.getPassword();

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);

		userAccount.setPassword(md5);

		Authority authority = new Authority();
		authority.setAuthority("JUDGE");

		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		a.setUserAccount(userAccount);

		return a;
	}

	public boolean isNumberRegistered(Integer judgeNumber) {
		Boolean res = true;
		if (judgeRepository.getJudgeNumberUsed(judgeNumber) == null) {
			res = false;
		}
		return res;
	}

}
