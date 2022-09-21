package edu.yu.cs.com1320.project.stage1.impl;
import static org.junit.Assert.assertEquals;

import edu.yu.cs.com1320.project.stage1.Document;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

public class samDocumentImplTest {

    @Test
    public void constructsTXTDoc(){
        String string1 = "It was a dark and stormy night";
        URI uri1 =  URI.create("www.wrinkleintime.com");
        Document document1 = new DocumentImpl(uri1, string1);
    }

    @Test
    public void constructsBINARYDoc(){
        String string1 = "It was a dark and stormy night";
        byte[] binaryData = string1.getBytes();
        URI uri1 =  URI.create("www.wrinkleintime.com");
        Document document1 = new DocumentImpl(uri1, binaryData);
    }

    @Test
    public void testGetDocumentTxt(){
        String string1 = "It was a dark and stormy night";
        URI uri1 =  URI.create("www.wrinkleintime.com");
        Document document1 = new DocumentImpl(uri1, string1);
        assertEquals(document1.getDocumentTxt(), string1);
    }

    @Test
    public void testGetDocumentBinaryData(){
        String string1 = "It was a dark and stormy night";
        byte[] binaryData = string1.getBytes();
        URI uri1 =  URI.create("www.wrinkleintime.com");
        Document document1 = new DocumentImpl(uri1, binaryData);
        assertEquals(document1.getDocumentBinaryData(), binaryData);
    }

    @Test
    public void testGetKey(){
        String string1 = "It was a dark and stormy night";
        byte[] binaryData = string1.getBytes();
        URI uri1 =  URI.create("www.wrinkleintime.com");
        Document document1 = new DocumentImpl(uri1, binaryData);

        String string2 = "It was the best of times, it was the worst of times";
        URI uri2 =  URI.create("www.taleoftwocities.com");
        Document document2 = new DocumentImpl(uri2, string2);

        assertEquals(document1.getKey(), uri1);
        assertEquals(document2.getKey(), uri2);
    }

    @Test
    public void testEquals(){
        String string1 = "It was a dark and stormy night";
        byte[] binaryData = string1.getBytes();
        URI uri1 =  URI.create("www.wrinkleintime.com");

        String string2 = "It was a dark and stormy night";
        byte[] binaryData2 = string2.getBytes();
        URI uri2 =  URI.create("www.wrinkleintime.com");

        Document document1 = new DocumentImpl(uri1, binaryData);
        Document document2 = new DocumentImpl(uri2, binaryData2);

        assertEquals(document1, document2);
    }

    @Test
    public void testHashCode(){
        String string1 = "It was a dark and stormy night";
        byte[] binaryData = string1.getBytes();
        URI uri1 =  URI.create("www.wrinkleintime.com");

        String string2 = "It was a dark and stormy night";
        byte[] binaryData2 = string2.getBytes();
        URI uri2 =  URI.create("www.wrinkleintime.com");

        Document document1 = new DocumentImpl(uri1, binaryData);
        Document document2 = new DocumentImpl(uri2, binaryData2);

        assertEquals(document1.hashCode(), document2.hashCode());
    }
}
