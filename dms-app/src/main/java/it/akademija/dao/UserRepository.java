package it.akademija.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.user.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	List<User> findAllByOrderByIdDesc();
	User findByUsername (String username);
	void deleteByComment (String comment);
	List<User> findByUsernameContainingIgnoreCase(String userText);
	

}