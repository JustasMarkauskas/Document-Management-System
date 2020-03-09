package resources.utils;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



public class ManageAutotestingData {

	public static void main(String[] args) throws UnirestException {

		//		deleteUserDataByComment("http://localhost:8081", "autotesting");
		//		deleteUserDataByComment("http://localhost:8081", "autotesting autotesting autotesting autotesting au");
		//		deleteGroupDataByComment("http://localhost:8081", "autotesting");
		//		deleteGroupDataByComment("http://localhost:8081", "autotesting autotesting autotesting autotesting au");
		//		deleteDocTypeDataByComment("http://localhost:8081", "autotesting");
		//		deleteDocTypeDataByComment("http://localhost:8081", "autotesting autotesting autotesting autotesting au");
		//		deleteDocTypeDataByName("http://localhost:8081", "Shgn7");
		//		createUser("http://localhost:8081", "testUser101", "test", "test", "Password1", "autotesting");
		//		createGroup("http://localhost:8081", "testGroup101", "autotesting");
		//		createGroup("http://localhost:8081", "testGroup102", "autotesting");
		//		createDocType("http://localhost:8081", "testDocType101", "autotesting");
//				updateUserGroups("http://localhost:8081", "testUser101", "testGroup101", "");
		//		updateGroupsDocTypesForApproval("http://localhost:8081", "testGroup101", "Annual leave", "ExpenseClaim", "Sick-leave", "notice period", "EquipmentRequest");
		//		updateGroupsDocTypesForCreation("http://localhost:8081", "testGroup101", "Annual leave", "ExpenseClaim", "Sick-leave", "notice period", "EquipmentRequest");
//				updateGroupUsers("http://localhost:8081", "testGroup101", "testUser101", "", "", "", "");
		//		removeAssignedElementsFromGroup("http://localhost:8081", "testGroup101");
//		saveDocument("http://localhost:8081", "testUser1", "test unirest 1", "Sick-leave", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
//		submitDocument("http://localhost:8081", "testUser1", "test unirest 3", "Sick-leave", "autotesting", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 3.pdf");
//		deleteDocumentsByComment("http://localhost:8081", "autotesting");
//		createDataForStatistics("http://localhost:8081");
//		rejectDocument("http://localhost:8081", "testUser1", 1059, "test api Call");
		
	}

	public static void deleteGroupDataByComment(String baseUrl, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/group/comment?comment={comment}";
		Unirest.delete(editApi)
		.routeParam("comment", comment)
		.asString();
	}

	public static void deleteUserDataByComment(String baseUrl, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/user?comment={comment}";
		Unirest.delete(editApi)
		.routeParam("comment", comment)
		.asString();
	}

	public static void deleteDocTypeDataByComment(String baseUrl, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/doctype/comment?comment={comment}";
		Unirest.delete(editApi)
		.routeParam("comment", comment)
		.asString();
	}
	
	public static void deleteDocumentsByComment(String baseUrl, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/document/comment?description={description}";
		HttpResponse<String> response = Unirest.delete(editApi)
		.routeParam("description", comment)
		.asString();
		System.out.println("delete documents status code: " + response.getStatus());
	}

	public static void deleteDocTypeDataByName(String baseUrl, String name) throws UnirestException {
		String editApi = baseUrl + "/api/doctype?docTypeName={name}";
		Unirest.delete(editApi)
		.routeParam("name", name)
		.asString();
	}

	public static void saveDocument(String baseUrl, String author, String title, String docType, String description, String filePath) throws UnirestException {
		String editApi = baseUrl + "/api/document/save";
		HttpResponse<String> response = Unirest.post(editApi)
		.field("author", author)
		.field("description", description)
		.field("docType", docType)
		.field("files", new File(filePath))
		.field("title", title).asString();
		System.out.println("save document status code: " + response.getStatus());
		
	}

	public static void submitDocument(String baseUrl, String author, String title, String docType, String description, String filePath) throws UnirestException {
		String editApi = baseUrl + "/api/document/submit";
		HttpResponse<String> response = Unirest.post(editApi)
		.field("author", author)
		.field("description", description)
		.field("docType", docType)
		.field("files", new File(filePath))
		.field("title", title).asString();
		System.out.println("submit document status code: " + response.getStatus());
	}

	public static void createUser(String baseUrl, String username, String firstName, String lastName, String password, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/user";
		String userData = "{ \"comment\": \"" + comment + "\", " 
				+ "\"firstName\": \"" + firstName + "\", " 
				+ "\"lastName\": \"" + lastName + "\", " 
				+ "\"password\": \"" + password + "\", " 
				+ "\"username\": \"" + username + "\"}";
		HttpResponse<JsonNode> jsonResponse = Unirest.post(editApi)
				.header("content-type", "application/json")
				.body(userData)
				.asJson();
		System.out.println("createUser status code: " + jsonResponse.getStatus());
	}

	public static void createGroup(String baseUrl, String groupName, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/group";
		String groupData = "{ \"comment\": \"" + comment + "\", " 
				+ "\"id\": \"" + groupName + "\"}";
		HttpResponse<JsonNode> jsonResponse = Unirest.post(editApi)
				.header("content-type", "application/json")
				.body(groupData)
				.asJson();
		System.out.println("createGroup status code: " + jsonResponse.getStatus());
	}

	public static void createDocType(String baseUrl, String docTypeName, String comment) throws UnirestException {
		String editApi = baseUrl + "/api/doctype";
		String docTypeData = "{ \"comment\": \"" + comment + "\", " 
				+ "\"id\": \"" + docTypeName + "\"}";
		HttpResponse<JsonNode> jsonResponse = Unirest.post(editApi)
				.header("content-type", "application/json")
				.body(docTypeData)
				.asJson();
		System.out.println("createDocType status code: " + jsonResponse.getStatus());
	}

	public static void updateUserGroups(String baseUrl, String username, String group1, String group2) throws UnirestException {
		String editApi = baseUrl + "/api/user/update-user-groups/{username}?groups={groupName1}&groups={groupName2}";
		Unirest.put(editApi)
		.routeParam("username", username)
		.routeParam("groupName1", group1)
		.routeParam("groupName2", group2)
		.asString();
	}

	public static void updateGroupUsers(String baseUrl, String groupName, String user1, String user2, String user3,
			String user4, String user5) throws UnirestException {
		String editApi = baseUrl + "/api/user/add-users-to-group/{groupName}?usernames={user1}&usernames={user2}"
				+ "&usernames={user3}&usernames={user4}&usernames={user5}";
		Unirest.put(editApi)
		.routeParam("groupName", groupName)
		.routeParam("user1", user1)
		.routeParam("user2", user2)
		.routeParam("user3", user3)
		.routeParam("user4", user4)
		.routeParam("user5", user5)
		.asString();
	}

	public static void removeAssignedUsersFromGroup(String baseUrl, String groupName) throws UnirestException {
		String updateUsersApi = baseUrl + "/api/user/add-users-to-group/{groupName}?usernames=";
		Unirest.put(updateUsersApi)
		.routeParam("groupName", groupName)
		.asString();
	}

	public static void removeAssignedDFAFromGroup(String baseUrl, String groupName) throws UnirestException {
		String updateDFAApi = baseUrl + "/api/group/update-group-doctypes-for-approval/{groupName}?docTypesForApprovalNames=";
		Unirest.put(updateDFAApi)
		.routeParam("groupName", groupName)
		.asString();
	}

	public static void removeAssignedDFCFromGroup(String baseUrl, String groupName) throws UnirestException {
		String updateDFCApi = baseUrl + "/api/group/update-group-doctypes-for-creation/{groupName}?docTypesForCreationNames=";
		Unirest.put(updateDFCApi)
		.routeParam("groupName", groupName)
		.asString();
	}

	public static void removeAssignedElementsFromGroup(String baseUrl, String groupName) throws UnirestException {
		removeAssignedUsersFromGroup(baseUrl, groupName);
		removeAssignedDFAFromGroup(baseUrl, groupName);
		removeAssignedDFCFromGroup(baseUrl, groupName);
	}


	public static void updateGroupsDocTypesForApproval(String baseUrl, String groupName, String docType1, 
			String docType2, String docType3, String docType4, String docType5) throws UnirestException {
		String editApi = baseUrl + "/api/group/update-group-doctypes-for-approval/{groupName}?docTypesForApprovalNames={docType1}"
				+ "&docTypesForApprovalNames={docType2}&docTypesForApprovalNames={docType3}&docTypesForApprovalNames={docType4}"
				+ "&docTypesForApprovalNames={docType5}";
		Unirest.put(editApi)
		.routeParam("groupName", groupName)
		.routeParam("docType1", docType1)
		.routeParam("docType2", docType2)
		.routeParam("docType3", docType3)
		.routeParam("docType4", docType4)
		.routeParam("docType5", docType5)
		.asString();
	}

	public static void updateGroupsDocTypesForCreation(String baseUrl, String groupName, String docType1, 
			String docType2, String docType3, String docType4, String docType5) throws UnirestException {
		String editApi = baseUrl + "/api/group/update-group-doctypes-for-creation/{groupName}?docTypesForCreationNames={docType1}"
				+ "&docTypesForCreationNames={docType2}&docTypesForCreationNames={docType3}&docTypesForCreationNames={docType4}"
				+ "&docTypesForCreationNames={docType5}";
		Unirest.put(editApi)
		.routeParam("groupName", groupName)
		.routeParam("docType1", docType1)
		.routeParam("docType2", docType2)
		.routeParam("docType3", docType3)
		.routeParam("docType4", docType4)
		.routeParam("docType5", docType5)
		.asString();
	}
	
	public static int getLastDocumentIdByUsername(String baseUrl) throws UnirestException {
		String editApi = baseUrl + "/api/document/testUser1";

		HttpResponse<JsonNode> request = Unirest.get(editApi).asJson();
		System.out.println("get documents by username: " + request.getStatus());
		
		JSONObject jsonObj = new JSONObject(request);
		JSONObject body = jsonObj.getJSONObject("body");
		JSONArray results = body.getJSONArray("array");
		JSONObject doc = results.getJSONObject(0);
		int id = doc.getInt("id");
		return id;
	}
	
	public static void approveDocument(String baseUrl, String reviewer, int id, String rejectionReason) throws UnirestException {
		String editApi = baseUrl + "/api/document/approve-document";
		String docData = "{ \"documentReceiver\": \"" + reviewer + "\", " 
				+ "\"id\": \"" + id + "\", " 
				+ "\"rejectionReason\": \"" + rejectionReason + "\"}";
		HttpResponse<JsonNode> request = Unirest.put(editApi)
				.header("content-type", "application/json")
				.body(docData)
				.asJson();
		System.out.println("approve document status code: " + request.getStatus());
	}
	
	public static void rejectDocument(String baseUrl, String reviewer, int id, String rejectionReason) throws UnirestException {
		String editApi = baseUrl + "/api/document/reject-document";
		String docData = "{ \"documentReceiver\": \"" + reviewer + "\", " 
				+ "\"id\": \"" + id + "\", " 
				+ "\"rejectionReason\": \"" + rejectionReason + "\"}";
		HttpResponse<JsonNode> request = Unirest.put(editApi)
				.header("content-type", "application/json")
				.body(docData)
				.asJson();
		System.out.println("reject document status code: " + request.getStatus());
	}
	
	
	
	public static void createDataForStatistics(String baseUrl) throws UnirestException {
		createGroup(baseUrl, "managerStatistics", "data for statistics");
		createGroup(baseUrl, "userStatistics", "data for statistics");
		createUser(baseUrl, "manager111", "test", "test", "Password1", "data for statistics");
		createUser(baseUrl, "userStatistics1", "test", "test", "Password1", "data for statistics");
		createUser(baseUrl, "userStatistics2", "test", "test", "Password1", "data for statistics");
		createUser(baseUrl, "userStatistics3", "test", "test", "Password1", "data for statistics");
		createUser(baseUrl, "userStatistics4", "test", "test", "Password1", "data for statistics");
		createUser(baseUrl, "userStatistics5", "test", "test", "Password1", "data for statistics");
		createDocType(baseUrl, "statisticsRequest1", "data for statistics");
		createDocType(baseUrl, "statisticsRequest2", "data for statistics");
		createDocType(baseUrl, "statisticsRequest3", "data for statistics");

		updateGroupUsers(baseUrl, "userStatistics", "userStatistics1", "userStatistics2", "userStatistics3", "userStatistics4", "userStatistics5");
		updateUserGroups(baseUrl, "manager111", "managerStatistics", "");
		updateGroupsDocTypesForCreation(baseUrl, "userStatistics", "statisticsRequest1", "statisticsRequest2", "statisticsRequest3", "statisticsRequest4", "statisticsRequest5");
		updateGroupsDocTypesForApproval(baseUrl, "managerStatistics", "statisticsRequest1", "statisticsRequest2", "statisticsRequest3", "statisticsRequest4", "statisticsRequest5");
		
		submitDocument(baseUrl, "userStatistics1", "test request 1", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics2", "test request 2", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics2", "test request 3", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics3", "test request 4", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 5", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 6", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 7", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics3", "test request 8", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 9", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 10", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 11", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics3", "test request 12", "statisticsRequest3", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 13", "statisticsRequest3", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics3", "test request 14", "statisticsRequest3", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics4", "test request 15", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics4", "test request 16", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics4", "test request 17", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics4", "test request 18", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics4", "test request 19", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics4", "test request 20", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics4", "test request 21", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics4", "test request 22", "statisticsRequest3", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics4", "test request 23", "statisticsRequest3", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		submitDocument(baseUrl, "userStatistics5", "test request 24", "statisticsRequest1", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		submitDocument(baseUrl, "userStatistics5", "test request 25", "statisticsRequest2", "data for statistics", "/home/justas/Desktop/testing_files/testdocspdf/Test doc 2.pdf");
		
		
		
	}


}
