# Laboratorio virtualización e introducción a Docker y AWS

Se realiza una aplicación la cual tendra una base de datos no relacional como lo es MongoDB. Para esto obtenemos la imagen de MongoDB y la guardamos en el contendor
correspondiente. 

Se procede a conectar la base de datos con el siguiente código 
```
String connectionString = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
        ...
        }
```

Se tiene un servicio REST llamado **logService** el cual recibe una cadena que sera almacenada en la base de datos anteriormente creada, se guardara el texto con su
fecha de creación, para que luego sean consultadas los ultimos 10 textos que se hayan almacenado. 


```
try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("local");
            MongoCollection<Document> collection = database.getCollection("collection");
            collection.insertOne(new Document("texto", req.queryParams("cadena").toString()).append("fecha", new Date()));
            List<Document> resultados = collection.find().limit(10).into(new ArrayList<>());
            resultados.forEach(result -> System.out.println(result.toJson()));
            return resultados;
        }
```

Este proyecto se encuentra tanto en docker como en AWS EC2
