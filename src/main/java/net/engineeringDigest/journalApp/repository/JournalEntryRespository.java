package net.engineeringDigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringDigest.journalApp.entity.JournalEntry;


public interface JournalEntryRespository extends MongoRepository<JournalEntry, ObjectId>{

}
