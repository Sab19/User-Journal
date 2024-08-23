package net.engineeringDigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringDigest.journalApp.entity.JournalEntry;
import net.engineeringDigest.journalApp.entity.User;
import net.engineeringDigest.journalApp.repository.JournalEntryRespository;
import net.engineeringDigest.journalApp.repository.UserRespository;

@Component
public class JournalEntryService{
	
	@Autowired
	private JournalEntryRespository journalEntryRepository;	
	
	@Autowired
	private UserService userService;
	
	public void saveEntry(JournalEntry journalEntry, String username) {
		User user = userService.findByUsername(username);
		journalEntry.setDate(LocalDateTime.now());
		JournalEntry saved = journalEntryRepository.save(journalEntry);// first save in journal_entries collection
		user.getJournalEntries().add(saved);//user is still stored in memory and not in user collection yet
		userService.saveEntry(user);//this will save the record in user collection
	}
	
	public List<JournalEntry> getAll(){
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> getEntryById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}

	public void deleteEntryById(ObjectId id) {
		journalEntryRepository.deleteById(id);
	}

	public void deleteJournalEntries() {
		journalEntryRepository.deleteAll();
	}
}