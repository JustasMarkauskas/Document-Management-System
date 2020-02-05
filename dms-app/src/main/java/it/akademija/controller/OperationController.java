package it.akademija.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.service.OperationService;

@RestController
@RequestMapping(value = "/api/operation")
public class OperationController {

	private final OperationService operationService;

	   @Autowired
		public OperationController(OperationService operationService) {
			this.operationService = operationService;
		}
	
	    @RequestMapping(method = RequestMethod.POST)
		@ApiOperation(value = "Create operation", notes = "Creates operation with data")
		@ResponseStatus(HttpStatus.CREATED)
		public void saveOperation(@ApiParam(value = "Operation Data", required = true) @Valid @RequestBody final String operationName) {
	    	operationService.saveOperation(operationName);
		}
}