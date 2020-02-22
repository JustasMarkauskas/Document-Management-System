package it.akademija.model.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import it.akademija.model.file.DBFile;

@Entity
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String author;
	private String docType;
	private String title;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date submissionDate;	
	@Temporal(TemporalType.DATE)
	private Date reviewDate;
	private String documentReceiver;
	private String rejectionReason;
	private String status;
	
	@OneToMany(mappedBy="document",  cascade = { CascadeType.ALL })
	private List<DBFile> DBfiles = new ArrayList<DBFile>();
	
	@Transient
	private Map<String, String> dbFileIDs;
	
	public Document() {}
	
	public Document(Long id, String author, String docType, String title, String description, Date submissionDate,
			Date reviewDate, String documentReceiver, String rejectionReason, String status) {
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
	}
	
	
	
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

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DBFile> getDBfiles() {
		return DBfiles;
	}

	public void setDBfiles(List<DBFile> dBfiles) {
		DBfiles = dBfiles;
	}

	
	public void addFile(DBFile file) {
		this.DBfiles.add(file);
		file.setDocument(this);
		}

	
//	public Map<String, String> generateDbFileIDs() {
//		Map<String, String> dbFilesIds = new HashMap<String, String>();
//		
//		for(int i = 0; i< getDBfiles().size(); i++) {
//			dbFilesIds.put(getDBfiles().get(i).getId(), getDBfiles().get(i).getFileName());
//			
//		}
//		
//		return dbFilesIds;
//	}
	
	public List<String> generateDbFileIDs() {
		List<String> dbFilesIds = new ArrayList<String>();
		
		for(int i = 0; i< getDBfiles().size(); i++) {
			dbFilesIds.add(getDBfiles().get(i).getId());
			
		}
		
		return dbFilesIds;
	}

	public Map<String, String> getDbFileIDs() {
		return dbFileIDs;
	}

	public void setDbFileIDs(Map<String, String> dbFileIDs) {
		this.dbFileIDs = dbFileIDs;
	}
	
	
	
}
