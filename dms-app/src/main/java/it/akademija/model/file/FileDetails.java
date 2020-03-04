package it.akademija.model.file;

public class FileDetails {
	private String id;
	private String fileName;
	
	
	public FileDetails() {}
	public FileDetails(DBFile DBFile) {
		this.id = DBFile.getId();
		this.fileName = DBFile.getFileName();
		this.fileType = DBFile.getFileType();
	}
	
	
	private String fileType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
