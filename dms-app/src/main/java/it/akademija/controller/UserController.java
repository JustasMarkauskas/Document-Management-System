package it.akademija.controller;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.model.user.NewUser;
import it.akademija.model.user.User;
import it.akademija.model.user.UserForClient;
import it.akademija.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	
	
	private final UserService userService;

   @Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}



	@RequestMapping(path = "/loggedUsername", method = RequestMethod.GET)
	public String getLoggedInUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return currentUserName;
		}
		return "not logged";
	}
	

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get Users", notes = "Returns list of all users")
	public List<UserForClient> getUsersForClient() {
		return userService.getUsersForClient(); 
	}

	@RequestMapping(path = "/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get User", notes = "Returns user by username")
	public UserForClient getUserForClient(@PathVariable String username) {
		return userService.getUserForClient(username);
	}
	

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create user", notes = "Creates user with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveUser(@ApiParam(value = "User Data", required = true) @Valid @RequestBody final NewUser newUser) {
		userService.saveUser(newUser);
	}
	
	@RequestMapping(path = "/admin", method = RequestMethod.POST)
	//@PreAuthorize("hasRole('ROLE_ADMIN')") // kurti nauja admin leidzia tik ROLE_ADMIN
	@ApiOperation(value = "Create admin user", notes = "Creates admin user with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveAdmin(@ApiParam(value = "Admin user data", required = true) @Valid @RequestBody final NewUser newUser) {
		userService.saveAdmin(newUser);
	}
	
	@RequestMapping(path = "/{username}", method = RequestMethod.PUT)
	//@PreAuthorize("hasRole('ROLE_ADMIN')") // kurti nauja admin leidzia tik ROLE_ADMIN
	@ApiOperation(value = "Update user", notes = "Update user password")
	public User updatePassword(@ApiParam(value = "User Data", required = true) @Valid @PathVariable String username,
			@RequestBody final NewUser newUser) {
		return userService.updatePassword(username, newUser);
	}

	


}