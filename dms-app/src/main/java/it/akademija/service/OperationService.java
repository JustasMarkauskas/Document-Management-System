package it.akademija.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.akademija.dao.OperationRepository;
import it.akademija.model.operation.Operation;

@Service
public class OperationService {	

		private OperationRepository operationRepository;
	
		@Autowired
		public OperationService(OperationRepository operationRepository) {
			this.operationRepository = operationRepository;
		}

		
		@Transactional
		public void saveOperation(String operationName) {
			Operation operation = new Operation();
			operation.setId(operationName);
			operationRepository.save(operation);
			}
		
		
	}


