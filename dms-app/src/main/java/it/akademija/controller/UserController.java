package it.akademija.controller;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(path = "/user-groups/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get User groups", notes = "Return names of all user groups")
	public List<String> getUserGroups(@PathVariable String username) {
		return userService.getUserGroups(username); 
	}
	
	
	@RequestMapping(path = "/user-doctypes-for-creation/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get User doc types for creation", notes = "Return names of all user doc types for creation")
	public Set<String> getAllDocTypesForCreation(@PathVariable String username) {
		return userService.getAllDocTypesForCreation(username); 
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create user", notes = "After creation user is not assigned to any Group")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveUser(@ApiParam(required = true) @Valid @RequestBody final NewUser newUser) {
		userService.saveUser(newUser);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/admin")
	@ApiOperation(value = "Create admin")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveAdmin(@ApiParam(required = true) @Valid @RequestBody final NewUser newUser) {
		userService.saveAdmin(newUser);
	}
	
	
	
	@RequestMapping(path = "/update-password/{username}", method = RequestMethod.PUT)
//	@PreAuthorize("hasRole('ROLE_ADMIN')") // leidžiama tik su ROLE_ADMIN
//	@PreAuthorize("hasAuthority('OP1')") // leidžiama tik su tam tikru authority(veikia)
//	@PreAuthorize("hasAuthority('ADMIN')") //Roles nebutina saugoti  su ROLE_ prefiksu
	@ApiOperation(value = "Update user password", notes = "Update user password")
	public User updatePassword(@ApiParam(required = true) @PathVariable String username,
			@Valid @RequestBody final NewUser newUser) {
		return userService.updatePassword(username, newUser);
	}
	
	@RequestMapping(path = "/update-user-info/{username}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update user password", notes = "Update user info")
	public User updateUserInfo(@ApiParam(required = true) @PathVariable String username,
			@Valid @RequestBody final NewUser newUser) {
		return userService.updateUserInfo(username, newUser);
	}
	
	@RequestMapping(path = "/update-user-groups/{username}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update user groups", notes = "After update user has only those groups which are passed in the list")
	public void updateGroups(@ApiParam(required = true) @PathVariable String username,
			 @RequestParam final List<String> groups) {
		 userService.updateGroupsForOneUser(username, groups);
	}
	
	@RequestMapping(path = "/add-users-to-group/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Add list of users to one group", notes = "Usernames must be passed in the list of users")
	public void assignListOfUsersToOneGroup(@ApiParam(required = true) @PathVariable String groupName,
			 @RequestParam final List<String> usernames) {
		 userService.assignListOfUsersToOneGroup(groupName, usernames);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes user by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public List<User> deleteUsersByComment(@RequestParam final String comment) {
		userService.deleteUsersByComment(comment);
		return userService.getUsers();
	}
	


}