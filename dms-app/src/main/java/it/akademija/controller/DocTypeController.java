package it.akademija.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.model.doctype.DocTypeForClient;
import it.akademija.model.doctype.NewDocType;
import it.akademija.model.role.NewRole;
import it.akademija.model.role.RoleForClient;
import it.akademija.service.DocTypeService;



@RestController
@RequestMapping(value = "/api/doctype")
public class DocTypeController {
	
	private final DocTypeService docTypeService;

	@Autowired
	public DocTypeController(DocTypeService docTypeService) {
		this.docTypeService = docTypeService;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get doc types", notes = "Returns names of all doc types")
	public List<DocTypeForClient> getNamesOfDocTypeForClient() {
		return docTypeService.getDocTypeNamesForClient();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Create doc type", notes = "Creates doc type with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveRole(@ApiParam(required = true) @Valid @RequestBody final NewDocType newDocType) {
		docTypeService.saveDocType(newDocType);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes doc types by name", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRoleByName(@RequestParam final String docTypeName) {
		docTypeService.deleteDocTypByName(docTypeName);
	}

	@RequestMapping(path = "/comment",method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes doctypes by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRolesByComment(@RequestParam final String comment) {
		docTypeService.deleteDocTypesByComment(comment);
		
	}

}
