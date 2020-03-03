package it.akademija.model.document;

public class DocumentCountForStatistics {
	
	private Long submittedCount;
	private Long rejectedCount;
	private Long approvedCount;
	
	public DocumentCountForStatistics() {}
	
	
	public DocumentCountForStatistics(Long submittedCount, Long rejectedCount, Long approvedCount) {
	
		this.submittedCount = submittedCount;
		this.rejectedCount = rejectedCount;
		this.approvedCount = approvedCount;
	}
	public Long getSubmittedCount() {
		return submittedCount;
	}
	public void setSubmittedCount(Long submittedCount) {
		this.submittedCount = submittedCount;
	}
	public Long getRejectedCount() {
		return rejectedCount;
	}
	public void setRejectedCount(Long rejectedCount) {
		this.rejectedCount = rejectedCount;
	}
	public Long getApprovedCount() {
		return approvedCount;
	}
	public void setApprovedCount(Long approvedCount) {
		this.approvedCount = approvedCount;
	}

}
