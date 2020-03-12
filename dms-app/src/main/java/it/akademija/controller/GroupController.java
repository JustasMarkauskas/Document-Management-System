package it.akademija.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import it.akademija.model.group.GroupForClient;
import it.akademija.model.group.NewGroup;
import it.akademija.service.GroupService;

@RestController
@RequestMapping(value = "/api/group")
public class GroupController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

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

	@RequestMapping(path = "/starting-with/{groupNameText}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Groups starting with", notes = "Returns list of groups starting with passed String")
	public List<GroupForClient> getGroupsForClient(@PathVariable String groupNameText) {
		return groupService.getGroupsForClientContaining(groupNameText);
	}

	@RequestMapping(path = "/{groupName}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Group", notes = "Returns group, group users, group doc types by group name")
	public GroupForClient getGroupForClient(@PathVariable String groupName) {
		return groupService.getGroupForClient(groupName);
	}

	@RequestMapping(path = "/group-names", method = RequestMethod.GET)
	@ApiOperation(value = "Get all group names", notes = "Returns list of all group names")
	public List<String> getAllGroupNames() {
		return groupService.getAllGroupNames();
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create group", notes = "Creates group with data")
	public  ResponseEntity<String> saveGroup(@ApiParam(required = true) @Valid @RequestBody final NewGroup newGroup) {

		if (groupService.findByGroupName(newGroup.getId()) == null) {
			LOGGER.info("Action by {}. Created group: {}",
					SecurityContextHolder.getContext().getAuthentication().getName(), newGroup.getId());
			groupService.saveGroup(newGroup);
			return new ResponseEntity<String>("Saved succesfully", HttpStatus.CREATED);
		} else {
			LOGGER.warn("Action by {}. Group {} is not created",
					SecurityContextHolder.getContext().getAuthentication().getName(), newGroup.getId());
			return new ResponseEntity<String>("Failed to create group", HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(path = "/update-comment/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update group comment", notes = "Update group comment")
	public void updateGroupComment(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestBody final NewGroup newGroup) {
		groupService.updateGroupComment(groupName, newGroup);
		
		LOGGER.info("Action by {}. Updated group comment for group: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), groupName);
	}

	@RequestMapping(path = "/update-group-doctypes-for-approval/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update group doc types for approval", notes = "Add/update doc types for approval to group")
	public void updateDocTypeForApproval(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestParam final List<String> docTypesForApprovalNames) {
		groupService.updateDocTypesForApproval(groupName, docTypesForApprovalNames);
		
		LOGGER.info("Action by {}. Updated group DFA for group: {}. After update DFA names: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), groupName, docTypesForApprovalNames);
	}

	@RequestMapping(path = "/update-group-doctypes-for-creation/{groupName}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update group doc types for creation", notes = "Add/update doc types for creation to group")
	public void updateDocTypeForCreation(@ApiParam(required = true) @PathVariable String groupName,
			@Valid @RequestParam final List<String> docTypesForCreationNames) {
		groupService.updateDocTypesForCreation(groupName, docTypesForCreationNames);
		
		LOGGER.info("Action by {}. Updated group DFC for group: {}. After update DFC names: {}",
				SecurityContextHolder.getContext().getAuthentication().getName(), groupName, docTypesForCreationNames);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes group by name", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGroupByName(@RequestParam final String groupName) {
		groupService.deleteGroupByName(groupName);
	}

	@RequestMapping(path = "/comment", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes groups by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGroupsByComment(@RequestParam final String comment) {
		groupService.deleteGroupsByComment(comment);

	}

}
