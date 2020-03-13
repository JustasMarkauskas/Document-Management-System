package it.akademija.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.doctype.DocType;


public interface DocTypeRepository extends JpaRepository<DocType, Long>  {
	
	List<DocType> findAllByOrderByCreateDateDesc();
	DocType findById(String docTypeName);
	void deleteById(String docTypeName);
	void deleteByComment (String comment);
	List<DocType> findByIdContainingIgnoreCase(String docTypeText);

}
