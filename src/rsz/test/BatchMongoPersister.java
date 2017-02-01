package rsz.test;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.List;

import static com.mongodb.client.model.Updates.*;

/**
 * Created by rzabrisk on 2/1/17.
 */
public class BatchMongoPersister implements BatchPersistable {

    public MongoClient getClient() {
        if (client == null) {
            setClient(new MongoClient("localhost", 27017));

        }
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    private MongoClient client;

    public MongoCollection getCollection() {

        if (null == collection) {
            collection = getDatabase().getCollection("names");
        }
        return collection;
    }

    public void setCollection(MongoCollection collection) {
        this.collection = collection;
    }

    private MongoCollection collection;

    public MongoDatabase getDatabase() {
        if (null == database) {
            setDatabase(mongoClient.getDatabase("test"));
        }
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    private MongoDatabase database;


    @Override
    public void addToBatch(String sqlInsert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addToBatch(String name, String value) {
        Document doc = new Document("name", name).append("value", value);
        getCollection().insertOne(doc);
    }

    @Override
    public void commitBatch() {

    }

    @Override
    public void truncate(String tablename) {

    }
}
