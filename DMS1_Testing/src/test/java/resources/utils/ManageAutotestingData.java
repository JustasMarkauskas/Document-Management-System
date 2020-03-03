package resources.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;



public class ManageAutotestingData {

	public static void main(String[] args) throws UnirestException {

		deleteUserDataByComment("http://localhost:8081", "autotesting");
		deleteUserDataByComment("http://localhost:8081", "autotesting autotesting autotesting autotesting au");
		deleteGroupDataByComment("http://localhost:8081", "autotesting");
		deleteGroupDataByComment("http://localhost:8081", "autotesting autotesting autotesting autotesting au");
		deleteDocTypeDataByComment("http://localhost:8081", "autotesting");
		deleteDocTypeDataByComment("http://localhost:8081", "autotesting autotesting autotesting autotesting au");
		deleteDocTypeDataByName("http://localhost:8081", "Shgn7");
//		createUser("http://localhost:8081", "testUser101", "test", "test", "Password1", "autotesting");
//		createGroup("http://localhost:8081", "testGroup101", "autotesting");
//		createGroup("http://localhost:8081", "testGroup102", "autotesting");
//		createDocType("http://localhost:8081", "testDocType101", "autotesting");
//		updateUserGroups("http://localhost:8081", "testUser101", "testGroup101", "testGroup102");
//		updateGroupsDocTypesForApproval("http://localhost:8081", "testGroup101", "Annual leave", "ExpenseClaim", "Sick-leave", "notice period", "EquipmentRequest");
//		updateGroupsDocTypesForCreation("http://localhost:8081", "testGroup101", "Annual leave", "ExpenseClaim", "Sick-leave", "notice period", "EquipmentRequest");
		
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

	public static void deleteDocTypeDataByName(String baseUrl, String name) throws UnirestException {
		String editApi = baseUrl + "/api/doctype?docTypeName={name}";
		Unirest.delete(editApi)
		.routeParam("name", name)
		.asString();
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


}
