package it.akademija.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.User;

public interface UserRepository extends JpaRepository<User, Long>  {
	
	User findByUsername (String username);
	

}