package net.engineeringDigest.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringDigest.journalApp.entity.JournalEntry;
import net.engineeringDigest.journalApp.entity.User;
import net.engineeringDigest.journalApp.service.JournalEntryService;
import net.engineeringDigest.journalApp.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/{username}")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username) {
		try {
			User user = userService.findByUsername(username);
			journalEntryService.saveEntry(journalEntry, username);//first save the journal entry in journal_entries collection
			
			return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {
		User user = userService.findByUsername(username);
		List<JournalEntry> listOfEntries = user.getJournalEntries();
		if(listOfEntries!=null && !listOfEntries.isEmpty())
			return new ResponseEntity<>(listOfEntries, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("id/{journalId}")
	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
		/*
		 * return journalEntryService.getEntryById(journalId).orElse(null);
		 * If we keep above line instead of the below code then :
		 * 		 		1) There won't be any results returned for the wrong id provided in the url
		 * 				2) you will get 200 status code in postman even if you enter wrong id
		 */
		Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(journalId);
		if (journalEntry.isPresent()) {
			return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("id/{id}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
		journalEntryService.deleteEntryById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("id/{id}")
	public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newJournalEntry) {
		JournalEntry oldJournalEntry = journalEntryService.getEntryById(id).orElse(null);
		if(oldJournalEntry!=null) {
			oldJournalEntry.setContent(newJournalEntry.getContent()!=null && !newJournalEntry.getContent().equals("")?newJournalEntry.getContent():oldJournalEntry.getContent());
			oldJournalEntry.setTitle(newJournalEntry.getTitle()!=null && !newJournalEntry.getTitle().equals("")?newJournalEntry.getTitle():oldJournalEntry.getTitle());
			return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteAllJournalEntries() {
		journalEntryService.deleteJournalEntries();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
