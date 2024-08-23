package net.engineeringDigest.journalApp.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.engineeringDigest.journalApp.entity.User;
import net.engineeringDigest.journalApp.repository.UserRespository;

@Component
public class UserService{
	
	@Autowired
	private UserRespository userRepository;
	
	public void saveEntry(User user) {
		userRepository.save(user);
	}
	
	public List<User> getAll(){
		return userRepository.findAll();
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void deleteUserById(ObjectId id) {
		userRepository.deleteById(id);
	}

	public void deleteUsers() {
		userRepository.deleteAll();
	}
}
