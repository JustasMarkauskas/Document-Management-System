package it.akademija.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.akademija.dao.DocTypeRepository;
import it.akademija.dao.GroupRepository;
import it.akademija.model.doctype.DocType;
import it.akademija.model.group.Group;
import it.akademija.model.group.GroupForClient;
import it.akademija.model.group.NewGroup;



@Service
public class GroupService {

	private GroupRepository groupRepository;

	private DocTypeRepository docTypeRepository;


	@Autowired
	public GroupService(GroupRepository groupRepository, DocTypeRepository docTypeRepository) {
		this.groupRepository = groupRepository;
		this.docTypeRepository = docTypeRepository;

	}

	@Transactional(readOnly = true)
	public Group findByGroupName(String groupName) {
		return groupRepository.findById(groupName);
	}
	

	@Transactional(readOnly = true)
	public List<Group> getGroups() {
		return groupRepository.findAllByOrderByCreateDateDesc();
	}

	@Transactional(readOnly = true)
	public List<GroupForClient> getGroupsForClient() {
		return groupRepository.findAllByOrderByCreateDateDesc().stream()
				.map((group) -> new GroupForClient(group.getId(), group.getComment(), group.getUsers().size(),
						group.getGroupUsernames(), group.getGroupDocTypesForCreation(),
						group.getGroupDocTypesForApproval()))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<GroupForClient> getGroupsForClientStartingWith(String groupNameText) {
		return groupRepository.findByIdStartingWith(groupNameText).stream()
				.map((group) -> new GroupForClient(group.getId(), group.getComment(), group.getUsers().size(),
						group.getGroupUsernames(), group.getGroupDocTypesForCreation(),
						group.getGroupDocTypesForApproval()))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public GroupForClient getGroupForClient(String groupName) {
		return getGroupsForClient().stream().filter(group -> group.getId().equals(groupName)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find group"));
	}

	@Transactional
	public Group getGroup(String groupName) {
		return getGroups().stream().filter(group -> group.getId().equals(groupName)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find group"));
	}
	
	@Transactional(readOnly = true)
	public List<String> getAllGroupNames() {
		List<String> allGroupNames = new ArrayList<String>();
		for (int i = 0; i < getGroups().size(); i++) {
			allGroupNames.add(getGroups().get(i).getId());
		}
		return allGroupNames;
	}

	@Transactional
	public void saveGroup(NewGroup newGroup) {
		Group group = new Group();
		group.setId(newGroup.getId());
		group.setComment(newGroup.getComment());
		groupRepository.save(group);
	}
	
	@Transactional
	public void updateGroupComment(String groupName, NewGroup newGroup) {
		Group existingGroup = findByGroupName(groupName);
		existingGroup.setComment(newGroup.getComment());
		
	}

	@Transactional
	public void updateDocTypesForCreation(String groupName, List<String> docTypesForCreationNames) {
		Group group = groupRepository.findById(groupName);
		List<DocType> docTypesForCreation = new ArrayList<DocType>();
		for (String docTypesForCreationName : docTypesForCreationNames) {
			docTypesForCreation.add(docTypeRepository.findById(docTypesForCreationName));
		}
		group.setDocTypesForCreation(docTypesForCreation);
	}

	@Transactional
	public void updateDocTypesForApproval(String groupName, List<String> docTypesForApprovalNames) {
		Group group = groupRepository.findById(groupName);
		List<DocType> docTypesForApproval = new ArrayList<DocType>();
		for (String docTypesForApprovalName : docTypesForApprovalNames) {
			docTypesForApproval.add(docTypeRepository.findById(docTypesForApprovalName));
		}
		group.setDocTypesForApproval(docTypesForApproval);
	}

	
	
	@Transactional
	public void deleteGroupByName(String groupName) {
		groupRepository.deleteById(groupName);
	}

	@Transactional
	public void deleteGroupsByComment(String comment) {
		groupRepository.deleteByComment(comment);
	}

}
