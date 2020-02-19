package resources.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.thoughtworks.xstream.XStream;

import resources.models.Document;
import resources.models.DocumentData;
import resources.models.Group;
import resources.models.GroupData;
import resources.models.User;
import resources.models.UserData;

public class FileReaderUtils {

	public static List<String> getTestDataFromTxt(String fileName) throws IOException {
		List<String> records = new ArrayList<String>();
		String record;

		try (
				BufferedReader buffer = new BufferedReader(new FileReader(fileName)); 
				) {		
			while ((record = buffer.readLine()) != null) {
				records.add(record);
			}
		}		
		return records;
	}

	public static Object[] getGroupsFromXml(String fileName) throws IOException {
		XStream xstream = new XStream();

		xstream.processAnnotations(GroupData.class);
		xstream.processAnnotations(Group.class);
		GroupData data = (GroupData) xstream.fromXML(FileUtils.readFileToString(new File(fileName)));

		return data.getGroups().toArray();
	}
	
	public static Object[] getDocumentsFromXml(String fileName) throws IOException {
		XStream xstream = new XStream();

		xstream.processAnnotations(DocumentData.class);
		xstream.processAnnotations(Document.class);
		DocumentData data = (DocumentData) xstream.fromXML(FileUtils.readFileToString(new File(fileName)));

		return data.getDocuments().toArray();
	}
	
	public static Object[] getUsersFromXml(String fileName) throws IOException {
		XStream xstream = new XStream();

		xstream.processAnnotations(UserData.class);
		xstream.processAnnotations(User.class);
		UserData data = (UserData) xstream.fromXML(FileUtils.readFileToString(new File(fileName)));

		return data.getUsers().toArray();
	}



}
