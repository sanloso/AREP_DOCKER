package co.edu.escuelaing;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import spark.Request;
import spark.Response;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.port;


public class App 
{
    public static void main( String[] args )
    {
        port(getPort());
        conecctionMongoDB();
        get("/logService", (req, res) -> inputDataPage(req, res));
        get("/consulta", (req, res) -> consulta(req, res));
    }

    private static void conecctionMongoDB() {
        String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static String inputDataPage(Request req, Response res) {
        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<body>"
                + "<h2>Cadenas</h2>"
                + "<form action=\"/consulta\">"
                + "  Cadena:<br>"
                + "  <input type=\"text\" name=\"cadena\">"
                + "  <br>"
                + "  <input type=\"submit\" value=\"Submit\">"
                + "</form>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    private static List<Document> consulta(Request req, Response res) {
        String connectionString = "mongodb://localhost:27017";
//        para acceder al mongoDB desde el docker seguir esta ruta:
//        usr, bin, mongo
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("collection");
            collection.insertOne(new Document("texto", req.queryParams("cadena").toString()).append("fecha", new Date()));
            List<Document> resultados = collection.find().limit(10).into(new ArrayList<>());
            resultados.forEach(result -> System.out.println(result.toJson()));
            return resultados;
        }
    }
}
