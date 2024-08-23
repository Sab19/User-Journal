package net.engineeringDigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringDigest.journalApp.entity.User;
import net.engineeringDigest.journalApp.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createEntry(@RequestBody User user) {
		try {
			userService.saveEntry(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> getEntries() {
		List<User> listOfUsers = userService.getAll();
		if(listOfUsers!=null && !listOfUsers.isEmpty())
			return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
//	
//	@GetMapping("id/{journalId}")
//	public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId journalId) {
//		/*
//		 * return journalEntryService.getEntryById(journalId).orElse(null);
//		 * If we keep above line instead of the below code then :
//		 * 		 		1) There won't be any results returned for the wrong id provided in the url
//		 * 				2) you will get 200 status code in postman even if you enter wrong id
//		 */
//		Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(journalId);
//		if (journalEntry.isPresent()) {
//			return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
//	
//	@DeleteMapping("id/{id}")
//	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId id) {
//		journalEntryService.deleteEntryById(id);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
	
	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user) {
		User userInDB = userService.findByUsername(user.getUsername());
		if(userInDB!=null) {
			userInDB.setUsername(user.getUsername());
			userInDB.setPassword(user.getPassword());
			userService.saveEntry(userInDB);//save user details on same id
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteAllUsers() {
		userService.deleteUsers();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
