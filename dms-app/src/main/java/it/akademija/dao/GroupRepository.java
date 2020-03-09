package it.akademija.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.group.Group;

public interface GroupRepository extends JpaRepository<Group, Long>   {

	List<Group> findAllByOrderByCreateDateDesc();
	Group findById(String groupName);
	void deleteById(String groupName);
	void deleteByComment (String comment);
	List<Group> findByIdStartingWith(String groupNameText);
}
