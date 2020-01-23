package it.akademija.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.user.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	User findByUsername (String username);
	

}