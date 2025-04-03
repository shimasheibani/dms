package dtosTest;
import org.dms.dto.DocumentsDto;
import org.dms.enums.DocumentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DocumentDtoTest {
    @Test
    void documentDtoTest(){
        DocumentsDto documentDtoTest = new DocumentsDto();
        documentDtoTest.setAuthor("dolatabadi");
        documentDtoTest.setTitle("jaye khali solooch");
        documentDtoTest.setDocumentStatus(DocumentStatus.deleted);
        assertEquals("dolatabadi", documentDtoTest.getAuthor());
        assertEquals("jaye khali solooch", documentDtoTest.getTitle());
        assertEquals(DocumentStatus.deleted, documentDtoTest.getDocumentStatus());
    }
}
