package org.dms.entitiesTest;

import org.dms.entity.Documents;
import org.dms.enums.DocumentStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentEntityTest {
    @Test
    void DocumentEntityTest(){
        Documents document = new Documents();
        document.setTitle("boofe kur");
        document.setAuthor("Sadegh hedayat");
        document.setStatus(DocumentStatus.updated);
        document.setDocumentUrl("src/main/docs");

        assertEquals("Sadegh hedayat",document.getAuthor());
        assertEquals("boofe kur",document.getTitle());
        assertEquals(DocumentStatus.updated,document.getStatus());
        assertEquals("src/main/docs",document.getDocumentUrl());

    }
}
