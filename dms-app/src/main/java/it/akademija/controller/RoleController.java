package it.akademija.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import it.akademija.model.role.Role;
import it.akademija.model.role.RoleForClient;
import it.akademija.model.user.NewUser;
import it.akademija.model.user.User;
import it.akademija.model.user.UserForClient;
import it.akademija.service.RoleService;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

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
		roleService.saveRole(newRole);
	}

	@RequestMapping(path = "/update-role-operations/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update role/group operations", notes = "Add/update operations to role/group")
	public void updateOperations(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestParam final List<String> operations) {
		roleService.updateOperations(groupName, operations);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes role/group by name", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRoleByName(@RequestParam final String roleName) {
		roleService.deleteRoleByName(roleName);
	}

	@RequestMapping(path = "/comment",method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes roles/groups by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRolesByComment(@RequestParam final String comment) {
		roleService.deleteRolesByComment(comment);
		
	}
	
}
