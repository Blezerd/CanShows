package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FolderRepository;
import repositories.MessageRepository;
import domain.Competition;
import domain.Folder;


@Service
@Transactional
public class FolderService {
	
	@Autowired
	private FolderRepository folderRepository;
	
	public Collection<Folder> findAllMessageFolderFromParticipantById(int id) {
		return folderRepository.findFolderParticipant(id);
	}
	
	public Collection<Folder> findNameFolderParticipant(int id) {
		return folderRepository.findNameFolderParticipant(id);
	}
	
	public Folder findOneToShow(int folderId) {
		return folderRepository.findOne(folderId);
	}


}
