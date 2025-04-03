package org.dms.service;

import org.dms.dto.DocumentsDto;
import org.dms.dto.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {
    Response addDocument(DocumentsDto documentsDto, MultipartFile multipartFile) throws IOException;
    Response deleteDocument(Long documentId);
    Response updateDocument(DocumentsDto documentsDto, MultipartFile multipartFile) throws IOException;
    Response getAllDocuments();
    Response getDocumentById(Long documentId);
}
