package it.akademija.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.dao.DocTypeRepository;
import it.akademija.model.doctype.DocType;
import it.akademija.model.doctype.DocTypeComment;
import it.akademija.model.doctype.DocTypeForClient;
import it.akademija.model.doctype.NewDocType;

@Service
public class DocTypeService {
	
	private DocTypeRepository docTypeRepository;
	
	@Autowired
	public DocTypeService(DocTypeRepository docTypeRepository) {
		this.docTypeRepository = docTypeRepository;
	}


	@Transactional(readOnly = true)
	public DocType findByDocTypeName(String docTypeName) {
		return docTypeRepository.findById(docTypeName);
	}
	
	@Transactional(readOnly = true)
	public List<DocType> getDocTypes() {
		return docTypeRepository.findAllByOrderByCreateDateDesc();
	}
	
	@Transactional(readOnly = true)
	public List<DocTypeForClient> getDocTypeNamesForClient() {
		return docTypeRepository.findAllByOrderByCreateDateDesc().stream().map((docType) -> new DocTypeForClient(docType.getId()))
				.collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<DocTypeForClient> getDocTypeNamesAndCommentsForClient() {
		return docTypeRepository.findAllByOrderByCreateDateDesc().stream().map((docType) -> new DocTypeForClient(docType.getId(), docType.getComment()))
				.collect(Collectors.toList());
	}
	
	
	@Transactional(readOnly = true)
	public List<DocTypeForClient> getDocTypeNamesAndCommentsForClientContaining(String docTypeText) {
		return docTypeRepository.findByIdContainingIgnoreCase(docTypeText).stream().map((docType) -> new DocTypeForClient(docType.getId(), docType.getComment()))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public DocTypeForClient getDocTypeNameAndCommentForClient(String docTypeName) {
		return getDocTypeNamesAndCommentsForClient().stream().filter(docType -> docType.getId().equals(docTypeName)).findFirst()
				.orElseThrow(() -> new RuntimeException("Can't find group"));
	}
	
	
	@Transactional(readOnly = true)
	public List<String> getAllDocTypeNames() {
		List<String> allDocTypeNames = new ArrayList<String>();
		for (int i = 0; i < getDocTypes().size(); i++) {
			allDocTypeNames.add(getDocTypes().get(i).getId());
		}
		return allDocTypeNames;
	}
	
	@Transactional
	public void saveDocType(NewDocType newDocType) {
		DocType docType = new DocType();
		docType.setId(newDocType.getId());
		docType.setComment(newDocType.getComment());
		docTypeRepository.save(docType);
	}
	
	@Transactional
	public void updateDocTypeComment(String docTypeName, DocTypeComment docTypeComment) {
		DocType existingDocType = findByDocTypeName(docTypeName);
		existingDocType.setComment(docTypeComment.getComment());		
	}
	
	@Transactional
	public void deleteDocTypByName(String docTypeName) {
		docTypeRepository.deleteById(docTypeName);
	}
	
	@Transactional
	public void deleteDocTypesByComment(String comment) {
		docTypeRepository.deleteByComment(comment);
	}
	
}
