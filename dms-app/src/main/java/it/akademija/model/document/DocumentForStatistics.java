package it.akademija.model.document;

public class DocumentForStatistics {

	private Long authorSubmittedDocuments;
	private String author;
	
	public DocumentForStatistics() {}
	
	public DocumentForStatistics(String author, Long authorSubmittedDocuments) {
		this.authorSubmittedDocuments = authorSubmittedDocuments;
		this.author = author;
	}
	
	public Long getAuthorSubmittedDocuments() {
		return authorSubmittedDocuments;
	}
	public void setAuthorSubmittedDocuments(Long authorSubmittedDocuments) {
		this.authorSubmittedDocuments = authorSubmittedDocuments;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
}
