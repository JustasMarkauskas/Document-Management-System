package it.akademija.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.print.Doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.dao.RoleRepository;
import it.akademija.dao.UserRepository;
import it.akademija.model.doctype.DocType;
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
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				createGrantedAuthorityList(username));
	}

	// returns all grantedAuthorities(Roles and Operations)
	public List<GrantedAuthority> createGrantedAuthorityList(String username) {
		User user = findByUsername(username);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getId()));
			grantedAuthorities.addAll(role.getGrantedAutoritiesFromOperations());
		}
		List<GrantedAuthority> grantedAuthoritiesList = grantedAuthorities.stream().collect(Collectors.toList());
		return grantedAuthoritiesList;
	}

	@Transactional(readOnly = true)
	public List<String> getUserRoles(String username) {
		User user = findByUsername(username);
		return user.getRoles().stream().map((role) -> role.getId()).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Set<String> getAllDocTypesForCreation(String username) {
		User user = findByUsername(username);
		Set<String> allDocTypesForCreation = new HashSet<>();
		for (Role role : user.getRoles()) {
			for (DocType docType : role.getDocTypesForCreation()) {
				allDocTypesForCreation.add(docType.getId());
			}
		}

		return allDocTypesForCreation;
	}
	
	@Transactional(readOnly = true)
	public Set<String> getAllDocTypesForApproval(String username) {
		User user = findByUsername(username);
		Set<String> allDocTypesForApproval = new HashSet<>();
		for (Role role : user.getRoles()) {
			for (DocType docType : role.getDocTypesForApproval()) {
				allDocTypesForApproval.add(docType.getId());
			}
		}
		return allDocTypesForApproval;
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
		return userRepository.findAll().stream().map((user) -> new UserForClient(user.getFirstName(),
				user.getLastName(), user.getUsername(), user.getComment())).collect(Collectors.toList());
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
		user.setComment(newUser.getComment());
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

	@Transactional
	public void updateRolesForOneUser(String username, List<String> roles) {
		User existingUser = findByUsername(username);
		Role adminRole = roleRepository.findById("ADMIN");

		List<Role> roleList = new ArrayList<Role>();
		for (String roleName : roles) {
			roleList.add(roleRepository.findById(roleName));
			if (roleRepository.findById(roleName).equals(adminRole)) {
				existingUser.setAdmin(true);
			} else {
				existingUser.setAdmin(false);
			}
		}

		existingUser.setRoles(roleList);

	}

	@Transactional
	public void assignListOfUsersToOneRole(String roleName, List<String> usernames) {
		Role role = roleRepository.findById(roleName);
		if (roleName.equals("ADMIN")) {
			for (String username : usernames) {
				User user = userRepository.findByUsername(username);
				user.setAdmin(true);
				Collection<Role> userRoles = user.getRoles();
				userRoles.add(role);
			}
		} else {
			for (String username : usernames) {
				User user = userRepository.findByUsername(username);
				Collection<Role> userRoles = user.getRoles();
				userRoles.add(role);
			}
		}
	}

	@Transactional
	public void deleteUsersByComment(String comment) {
		userRepository.deleteByComment(comment);
	}

}
