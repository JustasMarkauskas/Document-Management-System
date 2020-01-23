package it.akademija.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.akademija.dao.RoleRepository;
import it.akademija.dao.UserRepository;
import it.akademija.model.role.Role;
import it.akademija.model.user.NewUser;
import it.akademija.model.user.User;
import it.akademija.model.user.UserForClient;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found.");
		}
		//tikrina, kurias roles turi
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				AuthorityUtils
				.createAuthorityList(new String[] { user.getRoles().stream().findAny().get().getName() }));
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<UserForClient> getUsersForClient() {
		return userRepository.findAll().stream().map(
				(user) -> new UserForClient(user.getFirstName(), user.getLastName(), user.getUsername()))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public User getUser(String username) {
		return getUsers().stream().filter(u -> u.getUsername().equals(username)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find user"));
	}
	
	@Transactional
	public UserForClient getUserForClient(String username) {
		return getUsersForClient().stream().filter(u -> u.getUsername().equals(username)).findFirst()
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
		Role role = roleRepository.findByName("ROLE_USER");
		user.setRoles(Arrays.asList(role));
		User saved = userRepository.save(user);
		return saved;
	}
	
	@Transactional
	public User saveAdmin(NewUser newUser) {
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(encoder.encode(newUser.getPassword()));
		Role role = roleRepository.findByName("ROLE_ADMIN");
		user.setRoles(Arrays.asList(role));
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
