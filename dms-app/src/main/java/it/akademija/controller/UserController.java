package it.akademija.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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

	@RequestMapping(path = "/containing/{userText}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Users containing text", notes = "Returns list of users containing passed String")
	public List<UserForClient> getUsersForClientContaining(@PathVariable String userText) {
		return userService.getUsersForClientContaining(userText);
	}
	
	@RequestMapping(path = "/usernames/", method = RequestMethod.GET)
	@ApiOperation(value = "Get all usernames", notes = "Returns list of all usernames")
	public List<String> getAllUsernames() {
		return userService.getAllUsernames();
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

	@RequestMapping(path = "/user-doctypes-for-approval/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get User doc types for approval", notes = "Return names of all user doc types for approval")
	public Set<String> getAllDocTypesForApproval(@PathVariable String username) {
		return userService.getAllDocTypesForApproval(username);
	}

	@RequestMapping(path = "/user-doctypes-for-creation/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get User doc types for creation", notes = "Return names of all user doc types for creation")
	public Set<String> getAllDocTypesForCreation(@PathVariable String username) {
		return userService.getAllDocTypesForCreation(username);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create user", notes = "After creation user is not assigned to any Group")
	public ResponseEntity<String>  saveUser(@ApiParam(required = true) @Valid @RequestBody final NewUser newUser) {

		if (userService.findByUsername(newUser.getUsername()) == null) {
			LOGGER.info("Action by {}. Created user: {}",
					SecurityContextHolder.getContext().getAuthentication().getName(), newUser.getUsername());
			userService.saveUser(newUser);
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else {
			LOGGER.warn("Action by {}. User {} is not created",
					SecurityContextHolder.getContext().getAuthentication().getName(), newUser.getUsername());
			return new ResponseEntity<String>("Failed to create user", HttpStatus.CONFLICT);

		}

	}

	@RequestMapping(method = RequestMethod.POST, path = "/admin")
	@ApiOperation(value = "Create admin")
	public ResponseEntity<String> saveAdmin(@ApiParam(required = true) @Valid @RequestBody final NewUser newUser) {
		if (userService.findByUsername(newUser.getUsername()) == null) {
			LOGGER.info("Action by {}. Created admin: {}",
					SecurityContextHolder.getContext().getAuthentication().getName(), newUser.getUsername());
			userService.saveAdmin(newUser);
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else {
			LOGGER.warn("Action by {}. Admin {} is not created",
					SecurityContextHolder.getContext().getAuthentication().getName(), newUser.getUsername());
			return new ResponseEntity<String>("Failed to create admin", HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(path = "/update-password/{username}", method = RequestMethod.PUT)
//	@PreAuthorize("hasRole('ROLE_ADMIN')") // leidžiama tik su ROLE_ADMIN
//	@PreAuthorize("hasAuthority('OP1')") // leidžiama tik su tam tikru authority(veikia)
//	@PreAuthorize("hasAuthority('ADMIN')") //Roles nebutina saugoti  su ROLE_ prefiksu
	@ApiOperation(value = "Update user password", notes = "Update user password")
	public User updatePassword(@ApiParam(required = true) @PathVariable String username,
			@Valid @RequestBody final NewUser newUser) {

		LOGGER.info("Action by {}. Updated password for user: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), username);

		return userService.updatePassword(username, newUser);

	}

	@RequestMapping(path = "/update-user-info/{username}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update user info", notes = "Update user info")
	public User updateUserInfo(@ApiParam(required = true) @PathVariable String username,
			@Valid @RequestBody final NewUser newUser) {

		LOGGER.info("Action by {}. Updated user info for user: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), username);

		return userService.updateUserInfo(username, newUser);

	}

	@RequestMapping(path = "/update-user-groups/{username}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update user groups", notes = "After update user has only those groups which are passed in the list")
	public void updateGroups(@ApiParam(required = true) @PathVariable String username,
			@RequestParam final List<String> groups) {

		userService.updateGroupsForOneUser(username, groups);

		LOGGER.info("Action by {}. Updated user groups for user: {}. After update group names: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), username, groups);

	}

	@RequestMapping(path = "/add-users-to-group/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Add list of users to one group", notes = "Usernames must be passed in the list of users")
	public void assignListOfUsersToOneGroup(@PathVariable String groupName,
			@RequestParam(value = "usernames", required = true) final List<String> usernames) {

		userService.assignListOfUsersToOneGroup(groupName, usernames);

		LOGGER.info("Action by {}. Users assigned to group: {}. After update assigned users: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), groupName, usernames);

	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes user by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public List<User> deleteUsersByComment(@RequestParam final String comment) {
		userService.deleteUsersByComment(comment);
		return userService.getUsers();
	}

}