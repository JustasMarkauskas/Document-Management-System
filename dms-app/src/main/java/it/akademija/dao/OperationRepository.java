package it.akademija.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.model.operation.Operation;


public interface OperationRepository extends JpaRepository<Operation, Long>  {
	
	Operation findById(String operationName);
	
}
