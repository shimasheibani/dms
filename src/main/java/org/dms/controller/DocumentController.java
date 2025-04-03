package org.dms.controller;

import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dms.dto.DocumentsDto;
import org.dms.dto.Response;
import org.dms.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("api/documents")
@Slf4j
//@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addDocument(@RequestBody DocumentsDto documentsDto, MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(documentService.addDocument(documentsDto, multipartFile));
    }
    @GetMapping("/allDocuments")
    public ResponseEntity<Response> getAllDocuments(){
        return ResponseEntity.ok(documentService.getAllDocuments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDocumentById(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }
    @PutMapping("update/{id}")
    public ResponseEntity<Response> updateDocument(@RequestBody DocumentsDto documentDto, MultipartFile multipartFile) throws IOException {
        return ResponseEntity.ok(documentService.updateDocument(documentDto,multipartFile));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Response> deletDocument(@PathVariable Long id){
        return ResponseEntity.ok(documentService.deleteDocument(id));
    }
}
