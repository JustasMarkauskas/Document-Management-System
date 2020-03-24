package it.akademija.model.document;

import java.util.Date;
import java.util.List;

public class DocumentForTable {

	public DocumentForTable() {
	}

	public DocumentForTable(Long id, String author, String docType, String title, String description,
			Date submissionDate, Date reviewDate, String documentReceiver, String rejectionReason, String status,
			List<String> dbFileIDs, List<String> documentFileNames, long totalDocuments) {

		this.id = id;
		this.author = author;
		this.docType = docType;
		this.title = title;
		this.description = description;
		this.submissionDate = submissionDate;
		this.reviewDate = reviewDate;
		this.documentReceiver = documentReceiver;
		this.rejectionReason = rejectionReason;
		this.status = status;
		this.dbFileIDs = dbFileIDs;
		this.documentFileNames = documentFileNames;
		this.totalDocuments = totalDocuments;
	}

	private Long id;
	private String author;
	private String docType;
	private String title;
	private String description;
	private Date submissionDate;
	private Date reviewDate;
	private String documentReceiver;
	private String rejectionReason;
	private String status;
	private List<String> dbFileIDs;
	private List<String> documentFileNames;
	private long totalDocuments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getDocumentReceiver() {
		return documentReceiver;
	}

	public void setDocumentReceiver(String documentReceiver) {
		this.documentReceiver = documentReceiver;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getDbFileIDs() {
		return dbFileIDs;
	}

	public void setDbFileIDs(List<String> dbFileIDs) {
		this.dbFileIDs = dbFileIDs;
	}

	public List<String> getDocumentFileNames() {
		return documentFileNames;
	}

	public void setDocumentFileNames(List<String> documentFileNames) {
		this.documentFileNames = documentFileNames;
	}

	public long getTotalDocuments() {
		return totalDocuments;
	}

	public void setTotalDocuments(long totalDocuments) {
		this.totalDocuments = totalDocuments;
	}

}
