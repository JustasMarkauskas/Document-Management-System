package it.akademija.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.model.role.NewRole;
import it.akademija.model.role.RoleForClient;

import it.akademija.service.RoleService;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	private final RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get Roles", notes = "Returns list of all roles")
	public List<RoleForClient> getRolesForClient() {
		return roleService.getRolesForClient();
	}

	@RequestMapping(path = "/{roleName}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Role", notes = "Returns role and role users by role name")
	public RoleForClient getRoleForClient(@PathVariable String roleName) {
		return roleService.getRoleForClient(roleName);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create role", notes = "Creates role with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveRole(@ApiParam(required = true) @Valid @RequestBody final NewRole newRole) {

		if (roleService.findByRoleName(newRole.getId()) == null) {
			roleService.saveRole(newRole);

			LOGGER.info("Action by {}. Created role: {}",
					SecurityContextHolder.getContext().getAuthentication().getName(), newRole.getId());
		} else {
			LOGGER.warn("Action by {}. Role {} is not created",
					SecurityContextHolder.getContext().getAuthentication().getName(), newRole.getId());
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes role by name", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRoleByName(@RequestParam final String roleName) {
		roleService.deleteRoleByName(roleName);
	}

	@RequestMapping(path = "/comment", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes roles by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRolesByComment(@RequestParam final String comment) {
		roleService.deleteRolesByComment(comment);

	}

}
