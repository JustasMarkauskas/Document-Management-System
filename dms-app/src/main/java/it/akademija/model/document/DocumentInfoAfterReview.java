package it.akademija.model.document;

public class DocumentInfoAfterReview {
	
	private Long id;
	private String documentReceiver;
	private String rejectionReason;
	
	public DocumentInfoAfterReview() {}
	
	public DocumentInfoAfterReview(Long id, String documentReceiver, String rejectionReason) {
		
		this.id = id;
		this.documentReceiver = documentReceiver;
		this.rejectionReason = rejectionReason;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
	

}
