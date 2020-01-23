package it.akademija.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.dao.RoleRepository;
import it.akademija.model.role.Role;

@Service
public class RoleService {
	
	private RoleRepository roleRepository;

	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	
	@Transactional
	public void saveRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		roleRepository.save(role);
		}
}
