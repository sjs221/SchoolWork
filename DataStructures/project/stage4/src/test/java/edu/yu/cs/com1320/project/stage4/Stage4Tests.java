package edu.yu.cs.com1320.project.stage4;
import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage4.impl.DocumentStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Stage4Tests {

    @Test
    public void simpleDocLimitTest() throws IOException, URISyntaxException {
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(3);
        store.putDocument(new ByteArrayInputStream("Craft".getBytes()), new URI("CraftDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Cap".getBytes()), new URI("CapDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Captain America".getBytes()), new URI("CapAmDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Corn".getBytes()), new URI("CornDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Cinderblocks".getBytes()), new URI("CinderDoc"), DocumentStore.DocumentFormat.TXT);

        //Since docLimit was set to 3, adding 4 and 5 should remove 1 and 2
        assertEquals(3, store.searchByPrefix("C").size());
    }

    @Test
    public void simpleHeapFullTest() throws IOException, URISyntaxException {
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(20);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc2"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc3"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc4"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc5"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc6"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc7"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc8"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc9"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc10"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc11"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc12"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc13"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc14"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc15"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc16"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc17"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc18"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc19"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc20"), DocumentStore.DocumentFormat.TXT);
//        List<Document> firstList = store.searchByPrefix("A");
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc21"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc22"), DocumentStore.DocumentFormat.TXT);
//        List<Document> secondList = store.searchByPrefix("A");
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc23"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc24"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc25"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc26"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc27"), DocumentStore.DocumentFormat.TXT);
        List<Document> thirdList = store.searchByPrefix("A");
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc28"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc29"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc30"), DocumentStore.DocumentFormat.TXT);
        List<Document> list = store.searchByPrefix("A");

        for(Document d:list){
            System.out.println(d.getKey());
        }

        //Since docLimit was set to 20, expand table
        assertEquals(20, store.searchByPrefix("A").size());// - expands but separate issue here
    }

    @Test
    public void goOverLimitUndoCol() throws IOException, URISyntaxException {
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(4);
        URI test1 = new URI("Test1");
        URI test2 = new URI("Test2");
        URI test3 = new URI("Test3");
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), test1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), test2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), test3, DocumentStore.DocumentFormat.TXT);
        store.deleteAllWithPrefix("T");
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), new URI("Test4"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), new URI("Test5"), DocumentStore.DocumentFormat.TXT);
        store.undo(test2);
        store.undo(test3);

        assertEquals(4, store.searchByPrefix("T").size());
    }

    @Test
    public void goOverLimitUndoIndiv() throws IOException, URISyntaxException{
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(4);
        URI test1 = new URI("Test1");
        URI test2 = new URI("Test2");
        URI test3 = new URI("Test3");
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), test1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), test2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), test3, DocumentStore.DocumentFormat.TXT);

        assertEquals(3, store.searchByPrefix("T").size());

        store.deleteDocument(test3);
        store.putDocument(new ByteArrayInputStream("Test".getBytes()), new URI("Test4"), DocumentStore.DocumentFormat.TXT);

        assertEquals(3, store.searchByPrefix("T").size());

        store.undo(test3);

        assertEquals(4, store.searchByPrefix("T").size());

    }

    @Test
    public void setDocLimitAfterTest() throws IOException, URISyntaxException{
        DocumentStore store = new DocumentStoreImpl();
        for(int i = 0; i < 20; i++){
            store.putDocument(new ByteArrayInputStream("Ap".getBytes()), new URI("ApDoc"+i), DocumentStore.DocumentFormat.TXT);
        }
        assertEquals(20, store.searchByPrefix("A").size());
        store.setMaxDocumentCount(4);
        assertEquals(4, store.searchByPrefix("A").size());
    }

    @Test
    public void byteLimitTest() throws IOException, URISyntaxException{
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentBytes(200);
        for(int i = 0; i < 3; i++){
            store.putDocument(new ByteArrayInputStream("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes()), new URI("ApDoc"+i), DocumentStore.DocumentFormat.TXT);
        }
        List<Document> listDocs = store.searchByPrefix("A");
        Integer sumBytes = 0;
        for (Document d : listDocs){
            sumBytes += (d.getDocumentTxt() != null ? d.getDocumentTxt().getBytes().length : d.getDocumentBinaryData().length);
        }
        assertTrue(sumBytes < 200);
    }

    @Test
    public void byteMaxSetAfter() throws IOException, URISyntaxException{
        DocumentStore store = new DocumentStoreImpl();
        for(int i = 0; i < 3; i++){
            store.putDocument(new ByteArrayInputStream("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes()), new URI("ApDoc"+i), DocumentStore.DocumentFormat.TXT);
        }
        store.setMaxDocumentBytes(200);
        List<Document> listDocs = store.searchByPrefix("A");
        Integer sumBytes = 0;
        for (Document d : listDocs){
            sumBytes += (d.getDocumentTxt() != null ? d.getDocumentTxt().getBytes().length : d.getDocumentBinaryData().length);
        }
        assertTrue(sumBytes < 200);
    }

    @Test
    public void setBothHitDocFirst() throws IOException, URISyntaxException{
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(70);
        store.setMaxDocumentBytes(2000);
        for(int i = 0; i < 20; i++){
            store.putDocument(new ByteArrayInputStream("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes()), new URI("ApDoc"+i), DocumentStore.DocumentFormat.TXT);
        }
        List<Document> listDocs = store.searchByPrefix("A");
        Integer sumBytes = 0;
        for (Document d : listDocs){
            sumBytes += (d.getDocumentTxt() != null ? d.getDocumentTxt().getBytes().length : d.getDocumentBinaryData().length);
        }
        assertTrue(sumBytes <= 2000);
        assertTrue(store.search("Lorem").size() <= 70);
    }

    @Test
    public void setBothHitDocFirstUndoCol() throws IOException, URISyntaxException{
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(50);
        store.setMaxDocumentBytes(5000);
        for(int i = 0; i < 200; i++){
            if(i % 5 == 0){
                store.putDocument(new ByteArrayInputStream("Lorem ipsum dolor sit amet".getBytes()), new URI("Doc"+i + "ModType0"), DocumentStore.DocumentFormat.TXT);
            }
            if(i % 5 == 1){
                store.putDocument(new ByteArrayInputStream("Lconsectetur et adipiscing elit,".getBytes()), new URI("Doc"+i + "ModType1"), DocumentStore.DocumentFormat.TXT);
            }
            if(i % 5 == 2){
                store.putDocument(new ByteArrayInputStream("Lsed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes()), new URI("Doc"+i + "ModType2"), DocumentStore.DocumentFormat.TXT);
            }
            if(i % 5 == 3){
                store.putDocument(new ByteArrayInputStream("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes()), new URI("Doc"+i + "ModType3"), DocumentStore.DocumentFormat.TXT);
            }
            if(i % 5 == 4){
                store.putDocument(new ByteArrayInputStream("Lorem ipsum dolor sit amet, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes()), new URI("Doc"+i + "ModType4"), DocumentStore.DocumentFormat.TXT);
            }
        }
        List<Document> listDocs1 = store.searchByPrefix("l");
        store.deleteAll("Lorem");
        List<Document> listDocs2 = store.searchByPrefix("l");
        store.deleteAllWithPrefix("lc");
        List<Document> listDocs3 = store.searchByPrefix("l");
        assertEquals(10, listDocs3.size());
        store.undo();
        List<Document> listDocs4 = store.searchByPrefix("l");
        assertEquals(20, listDocs4.size());
    }


}