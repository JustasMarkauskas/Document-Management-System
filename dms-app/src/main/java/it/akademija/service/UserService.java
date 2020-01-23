package it.akademija.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import it.akademija.dao.UserRepository;
import it.akademija.model.NewUser;
import it.akademija.model.User;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;


	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found.");
		}
		//cia patikrina, kurias roles turi. "ROLE_TEST" pakeisti veliau
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				AuthorityUtils
						.createAuthorityList(new String[] { "ROLE_TEST" }));
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Transactional
	public User getUser(String username) {
		return getUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find user"));
	}
	
	@Transactional
	public User saveUser(NewUser newUser) {
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(encoder.encode(newUser.getPassword()));
		User saved = userRepository.save(user);
		return saved;
	}
	
	@Transactional
	public User updatePassword(String username, NewUser newUser) {
		User existingUser = findByUsername(username);
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		existingUser.setPassword(encoder.encode(newUser.getPassword()));
		return existingUser;

	}



	

}
