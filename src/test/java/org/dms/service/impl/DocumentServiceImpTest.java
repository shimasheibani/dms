package org.dms.service.impl;

import org.dms.dto.DocumentsDto;
import org.dms.dto.Response;
import org.dms.entity.Documents;
import org.dms.exception.NotFoundException;
import org.dms.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Regression tests for DocumentServiceImp.
 *
 * Main bug covered here: updateDocument() saved the updated document but
 * then did "return null;" instead of a real Response, which would surface
 * to callers as a null response body / NPE further up the stack.
 */
@ExtendWith(MockitoExtension.class)
class DocumentServiceImpTest {

    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DocumentServiceImp documentServiceImp;

    @Test
    void updateDocument_returnsSuccessResponse_insteadOfNull() throws Exception {
        Documents existing = Documents.builder().id(1L).title("Old Title").author("Old Author").build();
        when(documentRepository.findById(1L)).thenReturn(Optional.of(existing));

        DocumentsDto dto = new DocumentsDto();
        dto.setId(1L);
        dto.setTitle("New Title");

        Response response = documentServiceImp.updateDocument(dto, null);

        assertNotNull(response, "updateDocument must not return null");
        assertEquals(200, response.getStatus());
        verify(documentRepository).save(existing);
        assertEquals("New Title", existing.getTitle());
    }

    @Test
    void updateDocument_throwsNotFound_whenDocumentMissing() {
        when(documentRepository.findById(99L)).thenReturn(Optional.empty());
        DocumentsDto dto = new DocumentsDto();
        dto.setId(99L);

        assertThrows(NotFoundException.class, () -> documentServiceImp.updateDocument(dto, null));
    }

    @Test
    void deleteDocument_removesDocument_whenFound() {
        Documents existing = Documents.builder().id(1L).build();
        when(documentRepository.findById(1L)).thenReturn(Optional.of(existing));

        documentServiceImp.deleteDocument(1L);

        verify(documentRepository).delete(existing);
    }
}
