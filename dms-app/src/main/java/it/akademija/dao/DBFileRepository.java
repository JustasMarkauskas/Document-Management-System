package it.akademija.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.akademija.model.file.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

	void deleteById(String id);
	List<DBFile> findByDocumentId(Long id);
	
}