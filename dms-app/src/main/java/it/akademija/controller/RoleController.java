package it.akademija.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.service.RoleService;

@RestController
@RequestMapping(value = "/api/role")
public class RoleController {

	private final RoleService roleService;

	   @Autowired
		public RoleController(RoleService roleService) {
			this.roleService = roleService;
		}
	
	    @RequestMapping(method = RequestMethod.POST)
		@ApiOperation(value = "Create role", notes = "Creates role with data")
		@ResponseStatus(HttpStatus.CREATED)
		public void saveUser(@ApiParam(value = "Role Data", required = true) @Valid @RequestBody final String roleName) {
		   roleService.saveRole(roleName);
		}
}
