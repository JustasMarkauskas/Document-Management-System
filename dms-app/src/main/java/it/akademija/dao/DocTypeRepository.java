package it.akademija.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.doctype.DocType;


public interface DocTypeRepository extends JpaRepository<DocType, Long>  {
	
	DocType findById(String docTypeName);
	void deleteById(String docTypeName);
	void deleteByComment (String docTypeName);

}
