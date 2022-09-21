package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * created by the document store and given to the BTree via a call to BTree.setPersistenceManager
 */
public class DocumentPersistenceManager implements PersistenceManager<URI, Document> {
    private File dir;//check

    public DocumentPersistenceManager(File baseDir){
        if(baseDir == null){
            this.dir = new File(System.getProperty("user.dir")); //System.getProperty("user.dir");??
        }else{
            this.dir = baseDir;//check - mkdir first if doesn't exist yet - if!exists() then mkdirs()
            if(!this.dir.exists()){
                this.dir.mkdirs();
            }
        }
    }

    @Override
    public void serialize(URI uri, Document val) throws IOException {
        if(val == null || !uri.equals(val.getKey())){
            throw new IllegalArgumentException();
        }
        String uriName = uri.toString();
        if(uri.getScheme() != null){
            uriName = (uri.toString().replace(uri.getScheme(),"")) + ".json";
            uriName = (uriName.replace("://",""));
        }
        String path = "";
        boolean hasDirs = false;
        File dirs = null;
        if(uriName.contains("/")){
            path = uriName.substring(0, uriName.lastIndexOf("/")+1);
            uriName = uriName.substring(uriName.lastIndexOf("/")+1);
            dirs = new File(this.dir.getAbsolutePath() + File.separatorChar + path);
            hasDirs = true;
            dirs.mkdirs();
        }
        File file = (hasDirs ?  new File(dirs.getAbsolutePath() + File.separatorChar + uriName) : new File(this.dir.getAbsolutePath() + File.separatorChar + uriName));
        file.createNewFile();
        FileWriter writer = new FileWriter(file);

        GsonBuilder gsonBuilder = new GsonBuilder();

        JsonSerializer<Document> jsonSerializer = new JsonSerializer<Document>() {
            @Override
            public JsonElement serialize(Document document, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jSonDoc = new JsonObject();
                if(document.getDocumentTxt() == null){
                    jSonDoc.addProperty("BinaryData", DatatypeConverter.printBase64Binary(val.getDocumentBinaryData()));
                }else{
                    jSonDoc.addProperty("Text", val.getDocumentTxt());
                }

                jSonDoc.addProperty("URI", val.getKey().toString());
                Type gsonType = new TypeToken<HashMap>(){}.getType();
                jSonDoc.add("WordCount Map", jsonSerializationContext.serialize(val.getWordMap(), gsonType));//check
                return jSonDoc;
            }
        };
        gsonBuilder.registerTypeAdapter(DocumentImpl.class,jsonSerializer);
        Gson customGson = gsonBuilder.create();
        customGson.toJson(val, writer);
        writer.close();
    }

    @Override
    public Document deserialize(URI uri) throws IOException {
        String uriName = uri.toString();
        if(uri.getScheme() != null){
            uriName = (uri.toString().replace(uri.getScheme(),"")) + ".json";
            uriName = (uriName.replace("://",""));
        }
        FileReader reader = new FileReader(this.dir.getAbsolutePath() + File.separatorChar + uriName);//idk -- test for if nothing is there. see @505 return null
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Document> jsonDeserializer = new JsonDeserializer<Document>() {
            @Override
            public Document deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                JsonObject jsonDoc = jsonElement.getAsJsonObject();
                boolean contText = false;
                String text = "";
                byte[] binaryDataDecoded = null;
                if(jsonDoc.has("Text")){
                    contText = true;
                    text = jsonDoc.get("Text").getAsString();
                }else{
                    binaryDataDecoded = DatatypeConverter.parseBase64Binary(jsonDoc.get("BinaryData").getAsString());
                }
                Type typeToken = new TypeToken<HashMap<String, Integer>>(){}.getType();
                Map<String, Integer> map = (!contText ? new HashMap<>() : jsonDeserializationContext.deserialize(jsonDoc.get("WordCount Map"), typeToken));
                if(contText){
                    return new DocumentImpl(uri, text, map);
                }
                return new DocumentImpl(uri, binaryDataDecoded);
            }
        };
        gsonBuilder.registerTypeAdapter(DocumentImpl.class,jsonDeserializer);
        Gson gson = gsonBuilder.create();
        Document doc = gson.fromJson(reader, DocumentImpl.class);
        reader.close();
        delete(uri);
        return doc;
    }

    @Override
    public boolean delete(URI uri) throws IOException {
        String uriName = uri.toString();
        if(uri.getScheme() != null){
            uriName = (uri.toString().replace(uri.getScheme(),"")) + ".json";
            uriName = (uriName.replace("://",""));
        }
        File deleteFile = new File(this.dir.getAbsolutePath() + File.separatorChar + uriName);
        Path path = Paths.get(this.dir.getAbsolutePath() + File.separatorChar + uriName);
        return Files.deleteIfExists(path);
    }
}


