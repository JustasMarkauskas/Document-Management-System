package it.akademija.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import it.akademija.model.document.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document> findByAuthorOrderByIdDesc(String username);

	List<Document> findByAuthorAndTitleContainingIgnoreCaseOrderByIdDesc(String username, String title);

	List<Document> findByAuthor(String username);

	@Query("SELECT d FROM Document d WHERE d.docType IN(:names) and d.status != :status and d.title like %:title% ORDER BY d.id DESC")
	List<Document> findDocumentsForApprovalContaining(@Param("names") List<String> names,
			@Param("status") String status, @Param("title") String titleText);

	@Query("SELECT d FROM Document d WHERE d.docType IN(:names) and d.status != :status ORDER BY d.id DESC")
	List<Document> findDocumentsForApproval(@Param("names") List<String> names, @Param("status") String status);

	@Query("SELECT d FROM Document d WHERE d.docType IN(:names) and d.status = :status ORDER BY d.id DESC")
	List<Document> findDocumentsForApprovalByStatus(@Param("names") List<String> names, @Param("status") String status);

	void deleteByDescription(String description);

	List<Document> findByAuthorAndStatusOrderByIdDesc(String username, String status);

	@Query("SELECT new it.akademija.model.document.Document(COUNT(d), d.author) FROM Document d WHERE d.docType=:docType and d.status != 'SAVED' and d.submissionDate BETWEEN :startDate AND :endDate GROUP BY d.author ORDER BY COUNT(d) DESC")
	List<Document> findTopAuthors(@Param("docType") String docType, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT COUNT(d) FROM Document d WHERE d.docType =:docType and d.status =:status and d.submissionDate BETWEEN :startDate AND :endDate")
	Long countByDocTypeAndStatusAndDate(@Param("docType") String docType, @Param("status") String status,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT COUNT(d) FROM Document d WHERE d.docType =:docType and d.status != 'SAVED' and d.submissionDate BETWEEN :startDate AND :endDate")
	Long countAllSubittedByDocTypeAndDate(@Param("docType") String docType, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
}
