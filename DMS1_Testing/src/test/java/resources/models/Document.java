package resources.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Document")
public class Document {
	@XStreamAlias("testDataDocument")
	private String testDataDocument;
	
	@XStreamAlias("identificator")
	private String identificator;
	
	@XStreamAlias("documentTypeName")
	private String documentTypeName;
	
	@XStreamAlias("comment")
	private String comment;
	

	public String getTestDataDocument() {
		return testDataDocument;
	}

	public void setTestDataDocument(String testDataDocument) {
		this.testDataDocument = testDataDocument;
	}

	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setGroupName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public String getComment() {
		return comment;
	}

	public void setComments(String comment) {
		this.comment = comment;
	}

}
