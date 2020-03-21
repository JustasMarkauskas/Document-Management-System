package resources.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DocumentType")
public class DocumentType {
	@XStreamAlias("testDataGroup")
	private String testDataGroup;

	@XStreamAlias("identificator")
	private String identificator;

	@XStreamAlias("documentTypeName")
	private String documentTypeName;

	@XStreamAlias("comment")
	private String comment;

	public String getTestDataDocument() {
		return testDataGroup;
	}

	public void setTestDataDocument(String testDataDocument) {
		this.testDataGroup = testDataDocument;
	}

	public String getIdentificator() {
		return identificator;
	}

	public void setIdentificator(String identificator) {
		this.identificator = identificator;
	}

	public String getDocumentTypeName() {
		return documentTypeName.trim();
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

	@Override
	public String toString() {
		return "DocumentType [testDataGroup=" + testDataGroup + ", identificator=" + identificator
				+ ", documentTypeName=" + documentTypeName + "]";
	}
	
	

}
