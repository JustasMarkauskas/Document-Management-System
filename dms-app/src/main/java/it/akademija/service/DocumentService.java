package it.akademija.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import it.akademija.dao.DocumentRepository;
import it.akademija.file.exceptions.FileStorageException;
import it.akademija.model.document.Document;
import it.akademija.model.document.DocumentForClient;
import it.akademija.model.document.DocumentInfoAfterReview;
import it.akademija.model.document.NewDocument;
import it.akademija.model.file.DBFile;

@Service
public class DocumentService {

	private DocumentRepository documentRepository;


	
	@Autowired
	public DocumentService(DocumentRepository documentRepository) {
		this.documentRepository = documentRepository;
	
	}

	@Transactional
	public Document getDocument(Long id) {
		return getDocuments().stream().filter(document -> document.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find document"));
	}
	
	@Transactional(readOnly = true)
	public List<Document> getDocuments() {
		return documentRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<DocumentForClient> getDocumentsForApprovalByDfaList(List<String> documentForApprovalNames, String status) {
		return documentRepository.findDocumentsForApproval(documentForApprovalNames, status).stream()
				.map((document) -> new DocumentForClient(document.getId(), document.getAuthor(), document.getDocType(),
						document.getTitle(), document.getDescription(), document.getSubmissionDate(),
						document.getReviewDate(), document.getDocumentReceiver(),
						document.getRejectionReason(), document.getStatus(), document.generateDbFileIDs()))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<DocumentForClient> getDocumentsForClientByAuthor(String username) {
		return documentRepository.findByAuthor(username).stream()
				.map((document) -> new DocumentForClient(document.getId(), document.getAuthor(), document.getDocType(),
						document.getTitle(), document.getDescription(), document.getSubmissionDate(),
						document.getReviewDate(), document.getDocumentReceiver(),
						document.getRejectionReason(), document.getStatus(), document.generateDbFileIDs()))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public DocumentForClient getDocumentForClientByIdAndUsername(String username, Long id) {
		return getDocumentsForClientByAuthor(username).stream().filter(document -> document.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find document"));
	}
	
	@Transactional
	public DocumentForClient getDocumentForClientById(Long id) {
		Document document = getDocument(id);
		DocumentForClient documentForClient = new DocumentForClient(document.getId(), document.getAuthor(), document.getDocType(),
				document.getTitle(), document.getDescription(), document.getSubmissionDate(),
				document.getReviewDate(), document.getDocumentReceiver(),
				document.getRejectionReason(), document.getStatus(), document.generateDbFileIDs());	
		return documentForClient;
	}

	
	@Transactional
	public DBFile saveDocumentWithOneFile(NewDocument newDocument, MultipartFile file) {
		Document document = new Document();
		document.setAuthor(newDocument.getAuthor());
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SAVED");	
		  String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
	            document.addFile(dbFile);
	    		documentRepository.save(document);
	    		return dbFile;
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }	
	}
	
	
	@Transactional
	public List<DBFile> saveDocumentWithMultipleFiles(NewDocument newDocument, MultipartFile[] files) {
		Document document = new Document();
		document.setAuthor(newDocument.getAuthor());
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SAVED");	
		List<DBFile> DBFiles = new ArrayList<DBFile>();
		
		for(MultipartFile file: files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
	            document.addFile(dbFile);	
	    		DBFiles.add(dbFile);	
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }	
		}
		documentRepository.save(document);
		return DBFiles;
		  
	}
	
	@Transactional
	public List<DBFile> submitDocument(NewDocument newDocument, MultipartFile[] files) {
		Document document = new Document();
		document.setAuthor(newDocument.getAuthor());
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SUBMITTED");	
		Date date = new Date();	 
		document.setSubmissionDate(date);
		List<DBFile> DBFiles = new ArrayList<DBFile>();
		
		for(MultipartFile file: files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
	            document.addFile(dbFile);	
	    		DBFiles.add(dbFile);	
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }	
		}
		documentRepository.save(document);
		return DBFiles;
		  
	}
	
	@Transactional
	public List<DBFile> submitDocumentAfterSaveForLater(Long id, NewDocument newDocument, MultipartFile[] files) {
		Document document = getDocument(id);
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SUBMITTED");
		Date date = new Date();	 
		document.setSubmissionDate(date);
		List<DBFile> DBFiles = new ArrayList<DBFile>();
		
		for(MultipartFile file: files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
	            document.addFile(dbFile);	
	    		DBFiles.add(dbFile);	
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }	
		}
		documentRepository.save(document);
		return DBFiles;
		  
	}
	
	@Transactional
	public List<DBFile> saveDocumentAfterSaveForLater(Long id, NewDocument newDocument, MultipartFile[] files) {
		Document document = getDocument(id);
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		List<DBFile> DBFiles = new ArrayList<DBFile>();
		
		for(MultipartFile file: files) {
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	        try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
	            document.addFile(dbFile);	
	    		DBFiles.add(dbFile);	
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }	
		}
		documentRepository.save(document);
		return DBFiles;
		  
	}
		
	
	@Transactional
	public void approveDocument(DocumentInfoAfterReview documentInfoAfterReview) {
		Document document = getDocument(documentInfoAfterReview.getId());
		Date date = new Date();	
		document.setReviewDate(date);
		document.setDocumentReceiver(documentInfoAfterReview.getDocumentReceiver()); 
		document.setStatus("APPROVED");	
		documentRepository.save(document);
	}
	
	@Transactional
	public void rejectDocument(DocumentInfoAfterReview documentInfoAfterReview) {
		Document document = getDocument(documentInfoAfterReview.getId());
		Date date = new Date();	
		document.setReviewDate(date);
		document.setDocumentReceiver(documentInfoAfterReview.getDocumentReceiver()); 
		document.setStatus("REJECTED");
		document.setRejectionReason(documentInfoAfterReview.getRejectionReason());
		documentRepository.save(document);
	}

	@Transactional
	public void deleteSavedDocumentById(Long id) {
		documentRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteDocumentByDescription(String description) {
		documentRepository.deleteByDescription(description);
	}
}
