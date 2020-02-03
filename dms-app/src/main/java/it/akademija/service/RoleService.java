package it.akademija.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.akademija.dao.OperationRepository;
import it.akademija.dao.RoleRepository;
import it.akademija.model.operation.Operation;
import it.akademija.model.role.NewRole;
import it.akademija.model.role.Role;
import it.akademija.model.role.RoleForClient;
import it.akademija.model.user.User;

@Service
public class RoleService {

	private RoleRepository roleRepository;
	private OperationRepository operationRepository;

	@Autowired
	public RoleService(RoleRepository roleRepository, OperationRepository operationRepository) {
		this.roleRepository = roleRepository;
		this.operationRepository = operationRepository;
	}

	@Transactional(readOnly = true)
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<RoleForClient> getRolesForClient() {
		return roleRepository.findAll().stream().map((role) -> new RoleForClient(role.getId(), role.getComment(), role.getUsers().size()))
				.collect(Collectors.toList());
	}

	@Transactional
	public Role getRole(String roleName) {
		return getRoles().stream().filter(role -> role.getId().equals(roleName)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find role"));
	}

	@Transactional
	public RoleForClient getRoleForClient(String roleName) {
		return getRolesForClient().stream().filter(role -> role.getId().equals(roleName)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find role"));
	}

	@Transactional
	public void saveRole(NewRole newRole) {
		Role role = new Role();
		role.setId(newRole.getId());
		role.setComment(newRole.getComment());
		roleRepository.save(role);
	}

	@Transactional
	public void updateOperations(String roleName, List<String> operations) {
		Role role = roleRepository.findById(roleName);
		List<Operation> operationsList = new ArrayList<Operation>();
		for (String operation : operations) {
			operationsList.add(operationRepository.findById(operation));
		}
		role.setOperations(operationsList);
	}

	@Transactional
	public void deleteRoleByName(String roleName) {
		roleRepository.deleteById(roleName);
	}
	
	@Transactional
	public void deleteRolesByComment(String comment) {
		roleRepository.deleteByComment(comment);
	}
	
}
