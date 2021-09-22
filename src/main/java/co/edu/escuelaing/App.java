package co.edu.escuelaing;

import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.port;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;


public class App 
{
    public static void main( String[] args )
    {
        port(getPort());
//        SparkSession spark = SparkSession.builder()
//                .master("local")
//                .appName("MongoSparkConnectorIntro")
//                .config("spark.mongodb.input.uri", "mongodb+srv://arep_user:Password@arep1.caty9.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
//                .config("spark.mongodb.output.uri", "mongodb+srv://arep_user:Password@arep1.caty9.mongodb.net/myFirstDatabase?retryWrites=true&w=majority")
//                .getOrCreate();
//
//        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        get("/logService", (req, res) -> inputDataPage(req, res));
        get("/consulta", (req, res) -> consulta(req, res));

//        jsc.close();
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
