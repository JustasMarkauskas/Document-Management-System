package it.akademija.service;


import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.akademija.dao.DBFileRepository;
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

	private DBFileRepository dbFileRepository;
	
	@Autowired
	public DocumentService(DocumentRepository documentRepository, DBFileRepository dbFileRepository) {
		this.documentRepository = documentRepository;
		this.dbFileRepository = dbFileRepository;
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
	public List<DocumentForClient> getDocumentsForClientByAuthor(String username) {
		return documentRepository.findByAuthor(username).stream()
				.map((document) -> new DocumentForClient(document.getId(), document.getAuthor(), document.getDocType(),
						document.getTitle(), document.getDescription(), document.getSubmissionDate(),
						document.getReviewDate(), document.getDocumentReceiver(),
						document.getRejectionReason(), document.getStatus()))
				.collect(Collectors.toList());
	}

	
	@Transactional
	public DocumentForClient getDocumentForClientById(String username, Long id) {
		return getDocumentsForClientByAuthor(username).stream().filter(document -> document.getId().equals(id)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find document"));
	}
	
	@Transactional
	public void saveDocument(NewDocument newDocument) {
		Document document = new Document();
		document.setAuthor(newDocument.getAuthor());
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SAVED");	
		documentRepository.save(document);
	}
	
	
	@Transactional
	public void submitDocument(NewDocument newDocument) {
		Document document = new Document();
		document.setAuthor(newDocument.getAuthor());
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SUBMITTED");
		Date date = new Date();	 
		document.setSubmissionDate(date);	
		documentRepository.save(document);
	}
	
	@Transactional
	public void submitDocumentAfterSaveForLater(NewDocument newDocument, Long id) {
		Document document = getDocument(id);
		document.setDescription(newDocument.getDescription());
		document.setDocType(newDocument.getDocType());
		document.setTitle(newDocument.getTitle());
		document.setStatus("SUBMITTED");
		Date date = new Date();	 
		document.setSubmissionDate(date);	
		documentRepository.save(document);
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

}
