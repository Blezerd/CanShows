package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import repositories.MessageRepository;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private FolderRepository folderRepository;

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private FolderService folderService;

	public Collection<Message> findAllMessageFromParticipantById(int id) {
		return messageRepository.findAllByParticipantId(id);
	}

	public Collection<Message> findAllByFolderId(int id) {
		return messageRepository.findAllByFolderId(id);
	}

	public Message create() {
		Message m = new Message();
		return m;
	}

	public MessageForm constructCreate(Message m) {
		MessageForm res = new MessageForm();
		return res;

	}

	public Message reconstruct(MessageForm messageForm) {
		Message m = new Message();
		m.setBody(messageForm.getBody());
		m.setSubject(messageForm.getSubject());
		m.setReceiver(messageForm.getParticipant());
		m.setSender(participantService.findByPrincipal());
		return m;

	}

	public void save(Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getReceiver() != null
				&& message.getSender() != null);
		if (message.getBody().isEmpty() || message.getSubject().isEmpty()) {
			throw new IllegalArgumentException("Empty");
		}
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		Collection<Folder> folderSender = folderService
				.findNameFolderParticipant(participantService.findByPrincipal()
						.getId());
		Collection<Folder> folderReceiver = folderService
				.findNameFolderParticipant(message.getReceiver().getId());
		for (Folder a : folderSender) {
			if (a.getName().contains("Out")) {
				Collection<Message> mensajes = a.getMessages();
				mensajes.add(message);
				Collection<Folder> folders = message.getFolders();
				folders.add(a);
				folderRepository.saveAndFlush(a);
			}
		}
		for (Folder a : folderReceiver) {
			if (a.getName().contains("In")) {
				Collection<Message> mensajes = a.getMessages();
				mensajes.add(message);
				Collection<Folder> folders = message.getFolders();
				folders.add(a);
				folderRepository.saveAndFlush(a);
			}
		}
		messageRepository.save(message);
	}
}
