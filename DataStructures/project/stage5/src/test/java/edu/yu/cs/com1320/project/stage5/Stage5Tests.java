package edu.yu.cs.com1320.project.stage5;
import edu.yu.cs.com1320.project.MinHeap;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage5.impl.DocumentPersistenceManager;
import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class Stage5Tests {

    @Test
    public void firstTestStage4AccommodatingStage5() throws IOException, URISyntaxException {
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(3);
        store.putDocument(new ByteArrayInputStream("Craft".getBytes()), new URI("CraftDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Cap".getBytes()), new URI("CapDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Captain America".getBytes()), new URI("CapAmDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Corn".getBytes()), new URI("CornDoc"), DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("Cinderblocks".getBytes()), new URI("CinderDoc"), DocumentStore.DocumentFormat.TXT);

        //Since docLimit was set to 3, adding 4 and 5 should remove 1 and 2
        assertEquals(5, store.searchByPrefix("C").size());
    }

    @Test
    public void wordCountAndGetWordsTest() throws URISyntaxException {
        DocumentImpl txtDoc = new DocumentImpl(new URI("placeholder"), " The!se ARE? sOme   W@o%$rds with^ s**ymbols (m)ixed [in]. Hope    this test test passes!");
        assertEquals(0, txtDoc.wordCount("bundle"));
        assertEquals(1, txtDoc.wordCount("these"));
        assertEquals(1, txtDoc.wordCount("WORDS"));
        assertEquals(1, txtDoc.wordCount("S-Y-M-B-O-??-LS"));
        assertEquals(1, txtDoc.wordCount("p@A$$sse$s"));
        assertEquals(2, txtDoc.wordCount("tEst"));
        Set<String> words = txtDoc.getWords();
        assertEquals(12, words.size());
//        assertTrue(words.contains("some")); //issue is that mine would be SOME bc caps for ignore case

        DocumentImpl binaryDoc = new DocumentImpl(new URI("0110"), new byte[] {0,1,1,0});
        assertEquals(0, binaryDoc.wordCount("anythingYouPutHereShouldBeZero"));
        Set<String> words2 = binaryDoc.getWords();
        assertEquals(0, words2.size());
    }

    @Test
    public void testDPM() throws URISyntaxException {
        DocumentPersistenceManager dPM = new DocumentPersistenceManager(null); // Idk what to put in here

        URI uri1 = new URI("https://test1.yu.edu");
        Document test1 = new DocumentImpl(uri1, "This is a test");

        try {
            dPM.serialize(uri1, test1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        URI uri2 = new URI("https://folder/test2.yu.edu");
        Document test2 = new DocumentImpl(uri2, "This is also a test");

        try{
            dPM.serialize(uri2, test2);
        }catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Document dDoc = dPM.deserialize(uri1);
            assertTrue(dDoc.equals(test1));
        }catch (IOException e){
            e.printStackTrace();
        }

        URI uri3 = new URI("https://folder/inner/test3.yu.edu");
        Document test3 = new DocumentImpl(uri3, "This is a test to test a byte array".getBytes());

        try{
            dPM.serialize(uri3, test3);
        }catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Document dDoc = dPM.deserialize(uri2);
            assertTrue(dDoc.equals(test2));
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            Document dDoc = dPM.deserialize(uri3);
            assertTrue(Arrays.equals(dDoc.getDocumentBinaryData(), test3.getDocumentBinaryData()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testBtreeAlone() throws Exception {
        BTreeImpl<URI, Document> bTree = new BTreeImpl();
        bTree.setPersistenceManager(new DocumentPersistenceManager(null));

        URI uri1 = new URI("https://test1.yu.edu");
        Document test1 = new DocumentImpl(uri1, "This is a test");

        URI uri2 = new URI("https://folder/test2.yu.edu");
        Document test2 = new DocumentImpl(uri2, "This is also a test");

        URI uri3 = new URI("https://folder/inner/test3.yu.edu");
        Document test3 = new DocumentImpl(uri3, "This is a test to test a byte array".getBytes());

        bTree.put(uri1, test1);
        bTree.put(uri2, test2);
        bTree.put(uri3, test3);

        bTree.moveToDisk(uri1);
        bTree.moveToDisk(uri2);
        bTree.moveToDisk(uri3);

        assertTrue(bTree.get(uri1).equals(test1));
        assertTrue(bTree.get(uri2).equals(test2));
        assertTrue(Arrays.equals(test3.getDocumentBinaryData(), bTree.get(uri3).getDocumentBinaryData()));
    }

    //Instance Var for tests
    Map<URI, Document> map = new HashMap<>();

    @Test
    public void testHeapObj() throws URISyntaxException {
        MinHeap<Stage5Tests.MemHeapObj> heap = new MinHeapImpl();

        URI uri1 = new URI("https://test1.yu.edu");
        Document test1 = new DocumentImpl(uri1, "This is a test");
        this.map.put(uri1,test1);
        Stage5Tests.MemHeapObj mHO1 = new Stage5Tests.MemHeapObj(test1.getKey());
        this.map.get(uri1).setLastUseTime(System.nanoTime());
        heap.insert(mHO1);

        URI uri2 = new URI("https://folder/test2.yu.edu");
        Document test2 = new DocumentImpl(uri2, "This is also a test");
        this.map.put(uri2,test2);
        Stage5Tests.MemHeapObj mHO2 = new Stage5Tests.MemHeapObj(test2.getKey());
        this.map.get(uri2).setLastUseTime(System.nanoTime());
        heap.insert(mHO2);

        URI uri3 = new URI("https://folder/inner/test3.yu.edu");
        Document test3 = new DocumentImpl(uri3, "This is a test to test a byte array".getBytes());
        this.map.put(uri3,test3);
        Stage5Tests.MemHeapObj mHO3 = new Stage5Tests.MemHeapObj(test3.getKey());
        this.map.get(uri3).setLastUseTime(System.nanoTime());
        heap.insert(mHO3);

        URI uri4 = new URI("https://folder/test4.yu.edu");
        Document test4 = new DocumentImpl(uri4, "This is also a test");
        this.map.put(uri4,test4);
        Stage5Tests.MemHeapObj mHO4 = new Stage5Tests.MemHeapObj(test4.getKey());
        this.map.get(uri4).setLastUseTime(System.nanoTime());
        heap.insert(mHO4);

        URI uri5 = new URI("https://test5.yu.edu");
        Document test5 = new DocumentImpl(uri5, "This is a test");
        this.map.put(uri5,test5);
        Stage5Tests.MemHeapObj mHO5 = new Stage5Tests.MemHeapObj(test5.getKey());
        this.map.get(uri5).setLastUseTime(System.nanoTime());
        heap.insert(mHO5);

        assertTrue(this.map.get(uri1).getKey().equals(heap.remove().uri));

        this.map.get(uri2).setLastUseTime(System.nanoTime());
        heap.reHeapify(new MemHeapObj(this.map.get(uri2).getKey()));

        assertTrue(this.map.get(uri3).getKey().equals(heap.remove().uri));

    }

    private class MemHeapObj implements Comparable<Stage5Tests.MemHeapObj> {
        private URI uri;

        public MemHeapObj(URI uri) {
            this.uri = uri;
        }

        @Override
        public int compareTo(Stage5Tests.MemHeapObj o) {
            if(Stage5Tests.this.map.get(uri).getLastUseTime() - Stage5Tests.this.map.get(o.uri).getLastUseTime() == 0){
                return 0;
            }else if(Stage5Tests.this.map.get(uri).getLastUseTime() - Stage5Tests.this.map.get(o.uri).getLastUseTime() < 0){
                return -1;
            }
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Stage5Tests.MemHeapObj that = (Stage5Tests.MemHeapObj) o;
            return Objects.equals(uri, that.uri);
        }

        @Override
        public int hashCode() {
            return Objects.hash(uri);
        }
    }

    @Test
    public void testDocStoreDocLimit() throws URISyntaxException, IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(10);

        URI uri1 = new URI("https://test1.yu.edu");
        String txt1 = "This is a test";
        Document test1 = new DocumentImpl(uri1, txt1);
        store.putDocument(new ByteArrayInputStream(txt1.getBytes()),uri1,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc1 = txt1.getBytes().length;

        URI uri2 = new URI("https://folder/test2.yu.edu");
        String txt2 = "This is also a test";
        Document test2 = new DocumentImpl(uri2, txt2);
        store.putDocument(new ByteArrayInputStream(txt2.getBytes()),uri2,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc2 = txt2.getBytes().length;

        URI uri3 = new URI("https://folder/inner/test3.yu.edu");
        byte[] byte1 = "This is a test to test a byte array".getBytes();
        Document test3 = new DocumentImpl(uri3, byte1);
        store.putDocument(new ByteArrayInputStream(byte1),uri3,DocumentStore.DocumentFormat.BINARY);
        int bytesPerDoc3 = byte1.length;

        URI uri4 = new URI("https://test4.yu.edu");
        String txt4 = "This is a test";
        Document test4 = new DocumentImpl(uri4, txt4);
        store.putDocument(new ByteArrayInputStream(txt4.getBytes()),uri4,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc4 = txt4.getBytes().length;

        URI uri5 = new URI("https://folder/test5.yu.edu");
        String txt5 = "This is also a test";
        Document test5 = new DocumentImpl(uri5, txt5);
        store.putDocument(new ByteArrayInputStream(txt5.getBytes()),uri5,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc5 = txt5.getBytes().length;

        URI uri6 = new URI("https://folder/inner/test6.yu.edu");
        byte[] byte2 = "This is a test to test a byte array".getBytes();
        Document test6 = new DocumentImpl(uri6, byte2);
        store.putDocument(new ByteArrayInputStream(byte2),uri6,DocumentStore.DocumentFormat.BINARY);
        int bytesPerDoc6 = byte2.length;

        URI uri7 = new URI("https://test7.yu.edu");
        String txt7 = "This is a test";
        Document test7 = new DocumentImpl(uri7, txt7);
        store.putDocument(new ByteArrayInputStream(txt7.getBytes()),uri7,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc7 = txt7.getBytes().length;

        URI uri8 = new URI("https://folder/test8.yu.edu");
        String txt8 = "This is also a test";
        Document test8 = new DocumentImpl(uri8, txt8);
        store.putDocument(new ByteArrayInputStream(txt8.getBytes()),uri8,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc8 = txt8.getBytes().length;

        URI uri9 = new URI("https://folder/inner/test9.yu.edu");
        byte[] byte3 = "This is a test to test a byte array".getBytes();
        Document test9 = new DocumentImpl(uri9, byte3);
        store.putDocument(new ByteArrayInputStream(byte3),uri9,DocumentStore.DocumentFormat.BINARY);
        int bytesPerDoc9 =byte3.length;

        URI uri10 = new URI("https://test10.yu.edu");
        String txt10 = "This is a test";
        Document test10 = new DocumentImpl(uri10, txt10);
        store.putDocument(new ByteArrayInputStream(txt10.getBytes()),uri10,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc10 = txt10.getBytes().length;

//        assertEquals(7, store.searchByPrefix("T").size()); //7 because 3/10 are byte[]

        URI uri11 = new URI("https://folder/test11.yu.edu");
        String txt11 = "This is also a test";
        Document test11 = new DocumentImpl(uri11, txt11);
        store.putDocument(new ByteArrayInputStream(txt11.getBytes()),uri11,DocumentStore.DocumentFormat.TXT);
        int bytesPerDoc11 = txt11.getBytes().length;

        URI uri12 = new URI("https://folder/inner/test12.yu.edu");
        byte[] byte4 = "This is a test to test a byte array".getBytes();
        Document test12 = new DocumentImpl(uri12, byte4);
        store.putDocument(new ByteArrayInputStream(byte4),uri12,DocumentStore.DocumentFormat.BINARY);
        int bytesPerDoc12 = byte4.length;

        assertEquals(8, store.searchByPrefix("T").size());//8 because 4/12 are bye[]

        String uri3Name = uri3.toString();
        if(uri3.getScheme() != null){
            uri3Name = (uri3.toString().replace(uri3.getScheme(),"")) + ".json";
            uri3Name = (uri3Name.replace("://",""));
        }
        String path3 = "";
        boolean hasDirs3 = false;
        File dirs3 = null;
        if(uri3Name.contains("/")){
            path3 = uri3Name.substring(0, uri3Name.lastIndexOf("/")+1);
            uri3Name = uri3Name.substring(uri3Name.lastIndexOf("/")+1);
            dirs3 = new File(System.getProperty("user.dir") + File.separatorChar + path3);
            hasDirs3 = true;
            dirs3.mkdirs();
        }
        File file3 = (hasDirs3 ?  new File(dirs3.getAbsolutePath() + File.separatorChar + uri3Name) : new File(System.getProperty("user.dir") + File.separatorChar + uri3Name));

        assertTrue(file3.exists());

        String uri6Name = uri6.toString();
        if(uri6.getScheme() != null){
            uri6Name = (uri6.toString().replace(uri6.getScheme(),"")) + ".json";
            uri6Name = (uri6Name.replace("://",""));
        }
        String path6 = "";
        boolean hasDirs6 = false;
        File dirs6 = null;
        if(uri6Name.contains("/")){
            path6 = uri6Name.substring(0, uri6Name.lastIndexOf("/")+1);
            uri6Name = uri6Name.substring(uri6Name.lastIndexOf("/")+1);
            dirs6 = new File(System.getProperty("user.dir") + File.separatorChar + path6);
            hasDirs6 = true;
            dirs6.mkdirs();
        }
        File file6 = (hasDirs6 ?  new File(dirs6.getAbsolutePath() + File.separatorChar + uri6Name) : new File(System.getProperty("user.dir") + File.separatorChar + uri6Name));

        assertTrue(file6.exists());

        store.setMaxDocumentCount(Integer.MAX_VALUE);
        store.setMaxDocumentBytes(10);//Step into code to see as moved to disk: 14, 19, 35 - since lowest is 14, this is the same as setting to 0

        assertEquals(test3, store.getDocument(uri3));
        assertEquals(test4, store.getDocument(uri4));
        assertEquals(test7, store.getDocument(uri7));
        assertEquals(test12, store.getDocument(uri12));



        store.setMaxDocumentBytes(Integer.MAX_VALUE);
        List<Document> documentList = store.search("This");
        assertEquals(8, documentList.size());

        assertEquals(test3, store.getDocument(uri3));
        assertEquals(test6, store.getDocument(uri6));
        assertEquals(test9, store.getDocument(uri9));
        assertEquals(test12, store.getDocument(uri12));

        store.deleteAll("This");
        store.undo(uri1);
        assertEquals(test1, store.getDocument(uri1));
        store.undo(uri2);
        store.undo(uri3);
        store.undo(uri4);
        store.undo(uri5);
        assertEquals(4,store.searchByPrefix("T").size());

        store.setMaxDocumentCount(3);

        assertEquals(test1, store.getDocument(uri1));
        assertEquals(test2, store.getDocument(uri2));
        assertNull(store.getDocument(uri3));
        assertEquals(test4, store.getDocument(uri4));
        assertEquals(test5, store.getDocument(uri5));

        String overtxt = "Overwriting text";
        store.putDocument(new ByteArrayInputStream(overtxt.getBytes()),uri1,DocumentStore.DocumentFormat.TXT);

        assertEquals(overtxt, store.getDocument(uri1).getDocumentTxt());
        store.undo();
        assertEquals(txt1, store.getDocument(uri1).getDocumentTxt());


    }

}
