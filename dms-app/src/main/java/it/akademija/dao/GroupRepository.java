package it.akademija.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long>   {

	Group findById(String groupName);
	void deleteById(String groupName);
	void deleteByComment (String comment);
}
