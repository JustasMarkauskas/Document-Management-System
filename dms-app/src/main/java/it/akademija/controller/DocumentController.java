package it.akademija.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.model.document.DocumentCountForStatistics;
import it.akademija.model.document.DocumentForClient;
import it.akademija.model.document.DocumentForStatistics;
import it.akademija.model.document.DocumentInfoAfterReview;
import it.akademija.model.document.NewDocument;
import it.akademija.model.file.DBFile;
import it.akademija.model.file.UploadFileResponse;
import it.akademija.service.DocumentService;

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

	@RequestMapping(path = "/status/{status}/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get documents by author and status", notes = "Returns list of documents by author and status (descending order)")
	public List<DocumentForClient> getDocumentsForClientByAuthorAndStatus(@PathVariable String username,
			@PathVariable String status) {
		return documentService.getDocumentsForClientByAuthorAndStatus(username, status);
	}

	@RequestMapping(path = "/documents-for-approval", method = RequestMethod.GET)
	@ApiOperation(value = "Get documents for approval for user", notes = "Returns list of documents for approval by DFA names list. Status must not be equalto SAVED")
	public List<DocumentForClient> getDocumentsForApprovalByDfaList(
			@RequestParam final List<String> documentForApprovalNames) {
		return documentService.getDocumentsForApprovalByDfaList(documentForApprovalNames, "SAVED");
	}

	@RequestMapping(path = "/documents-for-approval/{status}", method = RequestMethod.GET)
	@ApiOperation(value = "Get documents for approval for user by status", notes = "Returns list of documents for approval by DFA names list and status")
	public List<DocumentForClient> getDocumentsForApprovalByDfaListAndStatus(
			@RequestParam final List<String> documentForApprovalNames, @PathVariable String status) {
		return documentService.getDocumentsForApprovalByDfaListAndStatus(documentForApprovalNames, status);
	}

	@RequestMapping(path = "/{id}/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get document by document id and username", notes = "Returns document by document id and username")
	public DocumentForClient getDocumentForClientByIdAndUsername(@PathVariable String username, @PathVariable Long id) {
		return documentService.getDocumentForClientByIdAndUsername(username, id);
	}

	@RequestMapping(path = "/get/{id}/", method = RequestMethod.GET)
	@ApiOperation(value = "Get document by document id", notes = "Returns document by document id")
	public DocumentForClient getDocumentForClientById(@PathVariable Long id) {
		return documentService.getDocumentForClientById(id);
	}

	@RequestMapping(path = "/count/{docType}", method = RequestMethod.GET)
	@ApiOperation(value = "Get count by doc type and status", notes = "Returns number of documents by doc type and status")
	public DocumentCountForStatistics getDocumentCountForStatistics(@PathVariable String docType,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		return documentService.findDocumentCountForStatistics(docType, startDate, endDate);
	}

	@RequestMapping(path = "/topAuthors/{docType}", method = RequestMethod.GET)
	@ApiOperation(value = "Get top 5 authors by docType", notes = "Returns top 5 authors by submitted document and doc type")
	public List<DocumentForStatistics> findTopAuthors(@PathVariable String docType,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		return documentService.findTopAuthors(docType, startDate, endDate, 5);
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "Save document with multiple files", notes = "Creates document with multiple files")
	@ResponseStatus(HttpStatus.CREATED)
	public List<UploadFileResponse> saveDocumentWithMultipleFiles(
			@ApiParam(required = true) @Valid @ModelAttribute final NewDocument newDocument,
			@RequestParam("files") MultipartFile[] files) {

		List<DBFile> dBFiles = documentService.saveDocumentWithMultipleFiles(newDocument, files);
		List<UploadFileResponse> list = new ArrayList<UploadFileResponse>();
		for (int i = 0; i < files.length; i++) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(dBFiles.get(i).getId()).toUriString();
			list.add(new UploadFileResponse(dBFiles.get(i).getFileName(), fileDownloadUri, files[i].getContentType(),
					files[i].getSize()));
		}
		return list;
	}

	@RequestMapping(path = "/submit", method = RequestMethod.POST)
	@ApiOperation(value = "Submit document with multiple files", notes = "Submit document with multiple files")
	@ResponseStatus(HttpStatus.CREATED)
	public List<UploadFileResponse> submitDocument(
			@ApiParam(required = true) @Valid @ModelAttribute final NewDocument newDocument,
			@RequestParam("files") MultipartFile[] files) {

		List<DBFile> dBFiles = documentService.submitDocument(newDocument, files);
		List<UploadFileResponse> list = new ArrayList<UploadFileResponse>();
		for (int i = 0; i < files.length; i++) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(dBFiles.get(i).getId()).toUriString();
			list.add(new UploadFileResponse(dBFiles.get(i).getFileName(), fileDownloadUri, files[i].getContentType(),
					files[i].getSize()));
		}
		return list;
	}

	@RequestMapping(path = "/submit-after-save/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after save for later")
	public List<UploadFileResponse> submitDocumentAfterSaveForLater(
			@ApiParam(required = true) @Valid @PathVariable Long id, @ModelAttribute final NewDocument newDocument,
			@RequestParam("files") MultipartFile[] files) {

		List<DBFile> dBFiles = documentService.submitDocumentAfterSaveForLater(id, newDocument, files);
		List<UploadFileResponse> list = new ArrayList<UploadFileResponse>();
		for (int i = 0; i < files.length; i++) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(dBFiles.get(i).getId()).toUriString();
			list.add(new UploadFileResponse(dBFiles.get(i).getFileName(), fileDownloadUri, files[i].getContentType(),
					files[i].getSize()));
		}
		return list;
	}

	@RequestMapping(path = "/save-after-save/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after save for later")
	public List<UploadFileResponse> saveDocumentAfterSaveForLater(
			@ApiParam(required = true) @Valid @PathVariable Long id, @ModelAttribute final NewDocument newDocument,
			@RequestParam("files") MultipartFile[] files) {

		List<DBFile> dBFiles = documentService.saveDocumentAfterSaveForLater(id, newDocument, files);
		List<UploadFileResponse> list = new ArrayList<UploadFileResponse>();
		for (int i = 0; i < files.length; i++) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
					.path(dBFiles.get(i).getId()).toUriString();
			list.add(new UploadFileResponse(dBFiles.get(i).getFileName(), fileDownloadUri, files[i].getContentType(),
					files[i].getSize()));
		}
		return list;
	}

	@RequestMapping(path = "/approve-document", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after approval", notes = "Update document info after approval")
	public void approveDocument(
			@ApiParam(required = true) @Valid @RequestBody final DocumentInfoAfterReview documentInfoAfterReview) {
		documentService.approveDocument(documentInfoAfterReview);
	}

	@RequestMapping(path = "/reject-document", method = RequestMethod.PUT)
	@ApiOperation(value = "Update document info after approval", notes = "Update document info after approval")
	public void rejectDocument(
			@ApiParam(required = true) @Valid @RequestBody final DocumentInfoAfterReview documentInfoAfterReview) {
		documentService.rejectDocument(documentInfoAfterReview);
	}

	@RequestMapping(path = "/{documentId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes saved document by id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSavedDocumentById(@PathVariable Long documentId) {
		documentService.deleteSavedDocumentById(documentId);
	}

	@RequestMapping(path = "/comment", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes document by comment", notes = "Usefull for testing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUsersByDescription(@RequestParam final String description) {
		documentService.deleteDocumentByDescription(description);

	}

}
