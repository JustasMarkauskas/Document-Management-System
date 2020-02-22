package it.akademija.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.akademija.dao.DBFileRepository;
import it.akademija.file.exceptions.FileStorageException;
import it.akademija.file.exceptions.MyFileNotFoundException;
import it.akademija.model.document.DocumentForClient;
import it.akademija.model.file.DBFile;
import it.akademija.model.file.DBFileNameAndId;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Transactional
    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    @Transactional
    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
    
    @Transactional
    public List<DBFileNameAndId> getFilesByDocumentId(Long id) {
        return dbFileRepository.findByDocumentId(id).stream()
				.map((file) -> new DBFileNameAndId(file.getId(), file.getFileName()))
				.collect(Collectors.toList());
    }
    
    @Transactional
	public void deleteFileById(String id) {
		dbFileRepository.deleteById(id);
	}
}
