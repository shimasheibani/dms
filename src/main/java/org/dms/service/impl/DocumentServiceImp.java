package org.dms.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.dto.DocumentsDto;
import org.dms.dto.Response;
import org.dms.entity.Documents;
import org.dms.exception.NotFoundException;
import org.dms.repository.DocumentRepository;
import org.dms.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImp implements DocumentService {
    private final DocumentRepository documentRepository;
    private final String DOCUMENT_DIRECTORY = System.getProperty("user.dir") + "/documents/";
    private static final List<String> ALLOWED_DOCS_TYPES = List.of(
            "application/pdf",
            "application/msword",
            "image/png","image/jpeg","image/jpg"
    );
    private final ModelMapper modelMapper;

    @Override
    public Response addDocument(DocumentsDto documentsDto, MultipartFile multipartFile) throws IOException {
        Documents documentsToSave = Documents.builder()
                .title(documentsDto.getTitle())
                .author(documentsDto.getAuthor())
                .status(documentsDto.getDocumentStatus())
                .build();
        if(multipartFile != null){
            String documentPath = saveDoc(multipartFile);
            documentsToSave.setDocumentUrl(documentPath);
        }
        documentRepository.save(documentsToSave);
        return Response.builder()
                .status(200)
                .message("Document uploaded successfully")
                .build();
    }

    private String saveDoc(MultipartFile multipartFile) throws IOException {
        String documentType= multipartFile.getContentType();
        if(!ALLOWED_DOCS_TYPES.contains(documentType)){
            throw new IOException("Invali file type");
        }
        File directory = new File(DOCUMENT_DIRECTORY);
        if(!directory.exists()){
            directory.mkdirs();
            log.info("Directory Created");
        }
        String uniqueFileName = UUID.randomUUID()+ "-" + multipartFile.getOriginalFilename();
        String documentPath = DOCUMENT_DIRECTORY + uniqueFileName;
        try {
            File destinationFile = new File(documentPath);
            multipartFile.transferTo(destinationFile);
        }catch (Exception e){
            throw new IllegalArgumentException(" Error accurend while savinf image" + e.getMessage());
        }
        return documentPath;
    }

    @Override
    public Response deleteDocument(Long documentId) {
        Documents documents = documentRepository.findById(documentId).orElseThrow(() -> new NotFoundException("Document not found"));
        documentRepository.delete(documents);
        return Response.builder()
                .status(200)
                .message("Document deleted successfully")
                .build();
    }

    @Override
    public Response updateDocument(DocumentsDto documentsDto, MultipartFile multipartFile) throws IOException {
        Documents documents = documentRepository.findById(documentsDto.getId()).orElseThrow(()-> new NotFoundException("Document not found"));
        if(documentsDto.getAuthor() != null) documents.setAuthor(documentsDto.getAuthor());
        if(documentsDto.getTitle() != null) documents.setTitle(documentsDto.getTitle());
        if (multipartFile != null){
            String documentPath = saveDoc(multipartFile);
            documents.setDocumentUrl(documentPath);
        }
        documentRepository.save(documents);
        return null;
    }

    @Override
    public Response getAllDocuments() {
        List<Documents> documents = documentRepository.findAll();
        List<DocumentsDto> documentsDtoList = modelMapper.map(documents, new TypeToken<List<DocumentsDto>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("All documents found")
                .documentsDtos(documentsDtoList)
                .build();
    }

    @Override
    public Response getDocumentById(Long documentId) {
        Documents documents = documentRepository.findById(documentId).orElseThrow(() -> new NotFoundException("Document not found"));
        DocumentsDto documentsDto = modelMapper.map(documents,DocumentsDto.class);
        return Response.builder()
                .status(200)
                .message("Document found")
                .documentsDto(documentsDto)
                .build();
    }
}
