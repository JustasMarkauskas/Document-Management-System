package resources.utils;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class DeleteAutotestingData {

	public static void main(String[] args) throws UnirestException {
		
		deleteUserDataByComment("autotesting", "http://localhost:8081");
		deleteUserDataByComment("autotesting autotesting autotesting autotesting au", "http://localhost:8081");
		deleteGroupDataByComment("autotesting", "http://localhost:8081");
		deleteGroupDataByComment("autotesting autotesting autotesting autotesting au", "http://localhost:8081");
		deleteDocTypeDataByComment("autotesting", "http://localhost:8081");
		deleteDocTypeDataByComment("autotesting autotesting autotesting autotesting au", "http://localhost:8081");
		deleteDocTypeDataByName("Shgn7", "http://localhost:8081");
		
	}

	public static void deleteGroupDataByComment(String comment, String baseUrl) throws UnirestException {
		String editApi = baseUrl + "/api/group/comment?comment={comment}";
        Unirest.delete(editApi).routeParam("comment", comment).asString();
	}
	
	public static void deleteUserDataByComment(String comment, String baseUrl) throws UnirestException {
		String editApi = baseUrl + "/api/user?comment={comment}";
        Unirest.delete(editApi).routeParam("comment", comment).asString();
	}
	
	public static void deleteDocTypeDataByComment(String comment, String baseUrl) throws UnirestException {
		String editApi = baseUrl + "/api/doctype/comment?comment={comment}";
        Unirest.delete(editApi).routeParam("comment", comment).asString();
	}
	
	public static void deleteDocTypeDataByName(String name, String baseUrl) throws UnirestException {
		String editApi = baseUrl + "/api/doctype?docTypeName={name}";
        Unirest.delete(editApi).routeParam("name", name).asString();
	}
	

}
