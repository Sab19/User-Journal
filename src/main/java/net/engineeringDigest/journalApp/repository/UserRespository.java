package net.engineeringDigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringDigest.journalApp.entity.User;


public interface UserRespository extends MongoRepository<User, ObjectId>{
	User findByUsername(String username);
}
