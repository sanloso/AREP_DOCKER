package co.edu.escuelaing;

import spark.Request;
import spark.Response;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.port;


public class App 
{
    public static void main( String[] args )
    {
        port(getPort());
//        conecctionMongoDB();
        get("/logService", (req, res) -> inputDataPage(req, res));
        get("/consulta", (req, res) -> consulta(req, res));
    }

    private static void conecctionMongoDB() {
        String connectionString = System.getProperty("mongodb://localhost:27017");
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

    private static String consulta(Request req, Response res) {
        return req.queryParams("cadena");

    }
}
