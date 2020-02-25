package it.akademija.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.akademija.model.document.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findByAuthor(String username);
	
	@Query("SELECT d FROM Document d WHERE d.docType IN(:names) and d.status != :status ")
	List<Document> findDocumentsForApproval(@Param("names")List<String> names, @Param("status")String status);
	
	void deleteByDescription (String description);
}
