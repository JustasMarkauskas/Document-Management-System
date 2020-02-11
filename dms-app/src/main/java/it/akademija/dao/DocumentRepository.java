package it.akademija.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.document.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findByAuthor(String username);
	
}
