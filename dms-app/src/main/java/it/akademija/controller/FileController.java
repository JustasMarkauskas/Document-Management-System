package it.akademija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;
import it.akademija.model.document.DocumentForClient;
import it.akademija.model.file.DBFile;
import it.akademija.model.file.DBFileNameAndId;
import it.akademija.model.file.UploadFileResponse;
import it.akademija.service.DBFileStorageService;
import it.akademija.service.GroupService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/file")
public class FileController {

	
	private DBFileStorageService dbFileStorageService;

	@Autowired
	public FileController(DBFileStorageService dbFileStorageService) {
		this.dbFileStorageService = dbFileStorageService;
	}

  
    @RequestMapping(path = "/uploadFile",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();
        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @RequestMapping(path = "/uploadMultipleFiles",method = RequestMethod.POST)
   	@ResponseStatus(HttpStatus.CREATED)
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    
    @RequestMapping(path = "/{documentId}", method = RequestMethod.GET)
	@ApiOperation(value = "Get files by document id", notes = "Returns list of files by document id")
	public List<DBFileNameAndId> getFilesByDocumentId(@PathVariable Long documentId) {
		return dbFileStorageService.getFilesByDocumentId(documentId);
	}
   
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    
	@RequestMapping(path = "/{fileId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes file by id")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFileById(@PathVariable String fileId) {
		dbFileStorageService.deleteFileById(fileId);
	}

}
