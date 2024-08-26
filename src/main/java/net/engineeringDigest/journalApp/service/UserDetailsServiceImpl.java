package net.engineeringDigest.journalApp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.engineeringDigest.journalApp.entity.User;
import net.engineeringDigest.journalApp.repository.UserRespository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	private UserRespository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user != null) {
			UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRoles().toArray(new String[0]))
				.build();
			return userDetails;
		}
		throw new UsernameNotFoundException("User not found with the username: "+username);
	}

}
