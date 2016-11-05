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

import repositories.ParticipantRepository;
import security.Authority;
import security.UserAccount;
import domain.Dog;
import domain.Folder;
import domain.Message;
import domain.Participant;
import forms.CustomerForm;

@Service
@Transactional
public class ParticipantService {

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private OrganiserService organiserService;

	@Autowired
	private JudgeService judgeService;

	@Autowired
	private AdministratorService administratorService;

	public boolean userRegistered(String username) {
		Boolean res = true;
		if (participantRepository.getParticipantByUserName(username) == null) {
			res = false;
		}
		return res;
	}

	@SuppressWarnings("deprecation")
	public Participant reconstruct(@Valid CustomerForm participantForm) {
		Assert.notNull(participantForm);

		Integer mescc = participantForm.getCreditCard().getExpirationMonth();
		Integer añocc = participantForm.getCreditCard().getExpirationYear();
		int mes = new Date(System.currentTimeMillis()).getMonth() + 1;
		int año = new Date(System.currentTimeMillis()).getYear() + 1900;

		Boolean contraseña = false;

		if (participantForm.getPassword() != null
				&& participantForm.getPassword2() != null) {
			contraseña = participantForm.getPassword2().equals(
					participantForm.getPassword());
		}
		if (!contraseña) {
			throw new IllegalArgumentException("not same password");
		} else if (!participantForm.getIsCondition()) {
			throw new IllegalArgumentException("notCondition");
		}
		if (participantForm.getCreditCard().getExpirationMonth() == null
				|| participantForm.getCreditCard().getExpirationYear() == null) {
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
		if (this.userRegistered(participantForm.getUsername())
				|| administratorService.userRegistered(participantForm
						.getUsername())
				|| organiserService.userRegistered(participantForm
						.getUsername())
				|| judgeService.userRegistered(participantForm.getUsername())) {
			throw new IllegalArgumentException("duplicated");

		}
		Participant participant = new Participant();
		participant.setCreditCard(participantForm.getCreditCard());
		participant.setEmail(participantForm.getEmail());
		participant.setHomePage(participantForm.getHomePage());
		participant.setName(participantForm.getName());
		participant.setPhone(participantForm.getPhone());
		participant.setNationality(participantForm.getNationality());
		participant.setSurname(participantForm.getSurname());

		participant.setDogs(new HashSet<Dog>());
		participant.setFriends(new HashSet<Participant>());
		Folder inbox = new Folder();
		inbox.setMessages(new HashSet<Message>());
		inbox.setName("Inbox");
		Folder outbox = new Folder();
		inbox.setMessages(new HashSet<Message>());
		inbox.setName("Outbox");
		Collection<Folder> folders = new HashSet<Folder>();
		folders.add(outbox);
		folders.add(inbox);
		participant.setFolders(folders);

		UserAccount userAccount = new UserAccount();
		userAccount.setUsername(participantForm.getUsername());

		String password = participantForm.getPassword();

		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String md5 = encoder.encodePassword(password, null);

		userAccount.setPassword(md5);

		Authority authority = new Authority();
		authority.setAuthority("PARTICIPANT");

		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);
		participant.setUserAccount(userAccount);

		return participant;
	}

	public void save(@Valid Participant participant) {
		Assert.notNull(participant);
		Assert.isTrue(participant.getName() != ""
				&& participant.getSurname() != "");

		participantRepository.saveAndFlush(participant);
	}

	public Participant findByPrincipal() {
		UserAccount user = (UserAccount) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Assert.notNull(user);
		Participant participant = this.findByUserAccount(user);
		return participant;
	}

	public Collection<Participant> findAll() {
		return participantRepository.findAll();
	}

	public Participant findOne(int id) {
		Assert.notNull(id);
		return participantRepository.findOne(id);
	}

	public Participant findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		return participantRepository.getParticipantByUserAccount(userAccount
				.getId());

	}

	public void checkPrincipalParticipant() {
		Assert.notNull(findByPrincipal());
	}

	public Collection<Participant> getAllParticipants(String text) {
		// checkPrincipalParticipant();
		Collection<Participant> res = new HashSet<Participant>();
		Collection<Participant> name = participantRepository
				.findSearchParticipantName(text);
		Collection<Participant> surname = participantRepository
				.findSearchParticipantSurName(text);
		Collection<Participant> email = participantRepository
				.findSearchParticipantEmail(text);
		for (Participant a : name) {
			if (!res.contains(a))
				res.add(a);
		}
		for (Participant a : surname) {
			if (!res.contains(a))
				res.add(a);
		}
		for (Participant a : email) {
			if (!res.contains(a))
				res.add(a);
		}
		return res;
	}

	public void addFriend(Participant newFriend) {
		Participant yo = this.findByPrincipal();
		Assert.isTrue(newFriend.getId() != yo.getId());
		Collection<Participant> friends = yo.getFriends();
		friends.add(newFriend);
		yo.setFriends(friends);
		participantRepository.saveAndFlush(yo);

	}

	public void deleteFriend(Participant newFriend) {
		Participant yo = this.findByPrincipal();
		Assert.isTrue(newFriend.getId() != yo.getId());
		Collection<Participant> friends = yo.getFriends();
		friends.remove(newFriend);
		yo.setFriends(friends);
		participantRepository.saveAndFlush(yo);
	}

	public void update(Participant participant) {
		participantRepository.saveAndFlush(participant);
	}

	public boolean puedeRegistrar() {
		Participant participant = this.findByPrincipal();
		boolean res = true;
		Collection<Dog> dogs = participant.getDogs();
		Integer peticiones = 0;
		for (Dog a : dogs) {
			if (a.getRequest() != null) {
				peticiones++;
			}
		}
		if (peticiones >= dogs.size()) {
			res = false;
		}
		return res;
	}

}
