package it.akademija.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.model.document.DocumentForClient;
import it.akademija.model.document.DocumentInfoAfterReview;
import it.akademija.model.document.NewDocument;
import it.akademija.model.group.GroupForClient;
import it.akademija.model.group.NewGroup;
import it.akademija.service.DocumentService;
import it.akademija.service.GroupService;

@RestController
@RequestMapping(value = "/api/document")
public class DocumentController {

	private final DocumentService documentService;

	@Autowired
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@RequestMapping(path = "/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get documents by author", notes = "Returns list of documents by author")
	public List<DocumentForClient> getDocumentsForClientByAuthor(@PathVariable String username) {
		return documentService.getDocumentsForClientByAuthor(username);
	}

	@RequestMapping(path = "/{id}/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get documents by author", notes = "Returns list of documents by author")
	public DocumentForClient getDocumentForClientById(@PathVariable String username, @PathVariable Long id) {
		return documentService.getDocumentForClientById(username, id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Save document", notes = "Creates document with data")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDocument(@ApiParam(required = true) @Valid @RequestBody final NewDocument newDocument) {
		documentService.saveDocument(newDocument);
	}
	
	@RequestMapping(path = "/submit",method = RequestMethod.POST)
	@ApiOperation(value = "Submit document", notes = "Creates document with data. Status SUBMITTED")
	@ResponseStatus(HttpStatus.CREATED)
	public void submitDocument(@ApiParam(required = true) @Valid @RequestBody final NewDocument newDocument) {
		documentService.submitDocument(newDocument);
	}
	
	
	@RequestMapping(path = "/submit-after-save/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after save for later")
	public void submitDocumentAfterSaveForLater(@ApiParam(required = true) @Valid @RequestBody final NewDocument newDocument, @PathVariable Long id) {
		documentService.submitDocumentAfterSaveForLater(newDocument, id);
	}

	@RequestMapping(path = "/approve-document", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after approval", notes = "Update document info after approval")
	public void approveDocument(@ApiParam(required = true) @Valid @RequestBody final DocumentInfoAfterReview documentInfoAfterReview) {
		documentService.approveDocument(documentInfoAfterReview);
	}
	
	@RequestMapping(path = "/reject-document", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after approval", notes = "Update document info after approval")
	public void rejectDocument(@ApiParam(required = true) @Valid @RequestBody final DocumentInfoAfterReview documentInfoAfterReview) {
		documentService.rejectDocument(documentInfoAfterReview);
	}

}
