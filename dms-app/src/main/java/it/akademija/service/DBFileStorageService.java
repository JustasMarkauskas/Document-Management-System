package it.akademija.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVWriter;

import it.akademija.dao.DBFileRepository;
import it.akademija.dao.DocumentRepository;
import it.akademija.file.exceptions.FileStorageException;
import it.akademija.file.exceptions.MyFileNotFoundException;
import it.akademija.model.document.Document;
import it.akademija.model.document.DocumentForClient;
import it.akademija.model.file.DBFile;
import it.akademija.model.file.DBFileNameAndId;
import it.akademija.model.file.FileDetails;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DBFileStorageService {

	private final DocumentService documentService;

	private final DBFileRepository dbFileRepository;
	private final DocumentRepository documentRepository;

	@Autowired
	public DBFileStorageService(DBFileRepository dbFileRepository, DocumentRepository documentRepository,
			DocumentService documentService) {
		this.dbFileRepository = dbFileRepository;
		this.documentRepository = documentRepository;
		this.documentService = documentService;
	}

	@Transactional
	public DBFile storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
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
				.map((file) -> new DBFileNameAndId(file.getId(), file.getFileName())).collect(Collectors.toList());
	}

	@Transactional
	public void deleteFileById(String id) {
		dbFileRepository.deleteById(id);
	}

	public List<FileDetails> findAllFileDetailsByUsername(String username) {
		List<Document> documentList = documentRepository.findByAuthor(username);
		Set<DBFile> dbFilesSet = new HashSet<DBFile>();
		for (Document document : documentList) {
			List<DBFile> dbFilesList = document.getDBfiles();
			dbFilesSet.addAll(dbFilesList);
		}
		List<FileDetails> fileDetailsList = new ArrayList<FileDetails>();
		for (DBFile dbFile : dbFilesSet) {
			fileDetailsList.add(new FileDetails(dbFile));
		}
		return fileDetailsList;
	}

	public String formatDate(Date date) {
		String dateToString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		if (!(date == null)) {
			dateToString = formatter.format(date);
		}
		return dateToString;
	}

	public File getCsvFile(String username) {
		File csvFile = new File(username);
		List<DocumentForClient> documents = documentService.getDocumentsForClientByAuthor(username);

		try {
			FileWriter outputfile = new FileWriter(csvFile);
			CSVWriter writer = new CSVWriter(outputfile);
			String[] header = { "id", "author", "docType", "title", "description", "submissionDate", "reviewDate",
					"documentReceiver", "rejectionReason", "status" };
			writer.writeNext(header);

			for (DocumentForClient doc : documents) {
				String[] data = { Long.toString(doc.getId()), doc.getAuthor(), doc.getDocType(), doc.getTitle(),
						doc.getDescription(), formatDate(doc.getSubmissionDate()), formatDate(doc.getReviewDate()),
						doc.getDocumentReceiver(), doc.getRejectionReason(), doc.getStatus() };
				writer.writeNext(data);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return csvFile;
	}

	public Map<String, ByteArrayResource> getAllFilesByUsername(String username) {
		List<FileDetails> usersFilesDetails = findAllFileDetailsByUsername(username);
		Map<String, ByteArrayResource> filesAsBytes = new HashMap<>();
		for (FileDetails file : usersFilesDetails) {
			filesAsBytes.put(file.getFileName(),
					new ByteArrayResource(dbFileRepository.findById(file.getId()).get().getData()));
		}
		return filesAsBytes;
	}

	public Map<String, ByteArrayResource> getAllFilesAndCsvByUsername(String username) throws IOException {
		List<FileDetails> usersFilesDetails = findAllFileDetailsByUsername(username);
		Map<String, ByteArrayResource> filesAsBytes = new HashMap<>();
		for (FileDetails file : usersFilesDetails) {
			filesAsBytes.put(file.getFileName(),
					new ByteArrayResource(dbFileRepository.findById(file.getId()).get().getData()));
		}

		File file = getCsvFile(username);
		byte[] csvFileBytesArray = new byte[(int) file.length()];
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			fis.read(csvFileBytesArray);
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		filesAsBytes.put(username + "_documents.csv", new ByteArrayResource(csvFileBytesArray));
		file.delete();
		return filesAsBytes;
	}

	public byte[] zipFiles(Map<String, ByteArrayResource> files) throws IOException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
		ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

		for (Map.Entry<String, ByteArrayResource> entry : files.entrySet()) {
			File file = new File("/tmp/" + entry.getKey());
			zipOutputStream.putNextEntry(new ZipEntry(entry.getKey()));
			OutputStream out = new FileOutputStream(file);
			out.write(entry.getValue().getByteArray());
			FileInputStream fileInputStream = new FileInputStream(file);
			IOUtils.copy(fileInputStream, zipOutputStream);
			out.close();
			fileInputStream.close();
			zipOutputStream.closeEntry();
		}

		if (zipOutputStream != null) {
			zipOutputStream.finish();
			zipOutputStream.flush();
			IOUtils.closeQuietly(zipOutputStream);
		}

		IOUtils.closeQuietly(bufferedOutputStream);
		IOUtils.closeQuietly(byteArrayOutputStream);

		return byteArrayOutputStream.toByteArray();
	}
}
