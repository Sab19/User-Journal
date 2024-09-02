package net.engineeringDigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection="users")
@Data
public class User {
	
	@Id
	private ObjectId id;

	@Indexed(unique=true)
	@NonNull
	private String username;
	
	@NonNull
	private String password;
	
	//users collection will keep the reference of journal_entries collection
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();

	private List<String> roles;
}
