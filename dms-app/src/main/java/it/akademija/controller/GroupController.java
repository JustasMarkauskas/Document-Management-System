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
import it.akademija.model.group.GroupForClient;
import it.akademija.model.group.NewGroup;
import it.akademija.model.user.NewUser;
import it.akademija.model.user.User;
import it.akademija.service.GroupService;


@RestController
@RequestMapping(value = "/api/group")
public class GroupController {

	private final GroupService groupService;

	@Autowired
	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get Groups", notes = "Returns list of all groups")
	public List<GroupForClient> getGroupsForClient() {
		return groupService.getGroupsForClient();
	}

	@RequestMapping(path = "/{groupName}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Group", notes = "Returns group, group users, group doc types by group name")
	public GroupForClient getGroupForClient(@PathVariable String groupName) {
		return groupService.getGroupForClient(groupName);
	}
	

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create group", notes = "Creates group with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveGroup(@ApiParam(required = true) @Valid @RequestBody final NewGroup newGroup) {
		groupService.saveGroup(newGroup);
	}
	
	@RequestMapping(path = "/update-comment/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update group comment", notes = "Update group comment")
	public void updateGroupComment(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestBody final NewGroup newGroup) {
		 groupService.updateGroupComment(groupName, newGroup);
	}

	
	@RequestMapping(path = "/update-group-doctypes-for-approval/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update group doc types for approval", notes = "Add/update doc types for approval to group")
	public void updateDocTypeForApproval(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestParam final List<String> docTypesForApprovalNames) {
		groupService.updateDocTypesForApproval(groupName, docTypesForApprovalNames);
	}
	
	@RequestMapping(path = "/update-group-doctypes-for-creation/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update group doc types for creation", notes = "Add/update doc types for creation to group")
	public void updateDocTypeForCreation(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestParam final List<String> docTypesForCreationNames) {
		groupService.updateDocTypesForCreation(groupName, docTypesForCreationNames);
	}

	
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes group by name", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGroupByName(@RequestParam final String groupName) {
		groupService.deleteGroupByName(groupName);
	}

	@RequestMapping(path = "/comment",method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes groups by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGroupsByComment(@RequestParam final String comment) {
		groupService.deleteGroupsByComment(comment);
		
	}
	
}
