package it.akademija.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.dao.DocTypeRepository;
import it.akademija.model.doctype.DocType;
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
		return docTypeRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<DocTypeForClient> getDocTypeNamesForClient() {
		return docTypeRepository.findAll().stream().map((docType) -> new DocTypeForClient(docType.getId()))
				.collect(Collectors.toList());
	}
	
	
	@Transactional
	public void saveDocType(NewDocType newDocType) {
		DocType docType = new DocType();
		docType.setId(newDocType.getId());
		docType.setComment(newDocType.getComment());
		docTypeRepository.save(docType);
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
