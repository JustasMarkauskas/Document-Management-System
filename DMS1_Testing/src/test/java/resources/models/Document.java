package resources.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Document")
public class Document {
	@XStreamAlias("testDataGroup")
	private String testDataGroup;

	@XStreamAlias("identificator")
	private String identificator;

	@XStreamAlias("documentTitle")
	private String documentTitle;

	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("documentType")
	private String documentType;
	
	@XStreamAlias("fileName")
	private String fileName;

	
	public String getTestDataGroup() {
		return testDataGroup;
	}

	public void setTestDataGroup(String testDataGroup) {
		this.testDataGroup = testDataGroup;
	}

	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	



}
