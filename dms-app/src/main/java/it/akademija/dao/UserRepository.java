package it.akademija.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.user.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	List<User> findAllByOrderByIdDesc();
	List<User> findAllByOrderByIdDesc(Pageable pageable);
	
	User findByUsername (String username);
	void deleteByComment (String comment);
	List<User> findByUsernameContainingIgnoreCase(String username);
	List<User> findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String username, String firstName, String lastName);
	List<User> findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String username, String firstName, String lastName, Pageable pageable);

	long countByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String username, String firstName, String lastName);
	long count();  

}