package it.akademija.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long>  {
	
	Role findById(String roleName);
	void deleteById(String roleName);
	void deleteByComment (String comment);

	
}