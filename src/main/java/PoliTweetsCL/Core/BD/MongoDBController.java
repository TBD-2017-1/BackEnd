package PoliTweetsCL.Core.BD;

import PoliTweetsCL.Core.Model.*;
import ejb.Resources;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class MongoDBController {
	Resources resources;

	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoCollection<Document> tweetsCollection;

	Logger logger = Logger.getLogger(getClass().getName());

	/* CONSTRUCTORES */
	public MongoDBController(){
		resources = new Resources();
		Properties prop = null;
		String host;
		String user;
		String pass;
		try{
			prop = new Properties();
			prop.load(resources.getResourceAsStream("app.properties"));
		}catch (Exception e){
			prop = null;
			logger.info(e.getMessage());
			e.printStackTrace();
		}finally {
			if(prop == null){
				host = "localhost";
				user = "admin";
				pass = "DigitalOceanServer";
			}else {
				host = prop.getProperty("mongo.host");
				user = prop.getProperty("mongo.user");
				pass = prop.getProperty("mongo.pass");
			}
		}

		logger.info("pass = "+pass);

		// credencial
		MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(user, "admin", pass.toCharArray());
		// cliente
		mongoClient = new MongoClient(new ServerAddress(host, 27017), Arrays.asList(mongoCredential));
		db = mongoClient.getDatabase("politweets");
		tweetsCollection = db.getCollection("tweets");

	}

	public MongoDBController(Properties prop){
		String host;
		String user;
		String pass;

		if(prop == null){
			host = "localhost";
			user = "admin";
			pass = "DigitalOceanServer";
		}else {
			host = prop.getProperty("mongo.host");
			user = prop.getProperty("mongo.user");
			pass = prop.getProperty("mongo.pass");
		}

		// credencial
		MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(user, "admin", pass.toCharArray());
		// cliente
		mongoClient = new MongoClient(new ServerAddress(host, 27017), Arrays.asList(mongoCredential));
		db = mongoClient.getDatabase("politweets");
		tweetsCollection = db.getCollection("tweets");

	}



	/* METODOS */

	public void saveTweet(Tweet tweet){
		// create document
		Document doc = Document.parse( tweet.toJSON() );

		// Insert
		tweetsCollection.insertOne(doc);
	}

	public Tweet getTweetById(long id){
		Document doc = tweetsCollection.find(Filters.eq("_id",id)).first();
		return Tweet.fromDocument(doc);
	}

	public Tweet[] getTextUnindexedTweets(boolean setAsIndexed){
		// obtener tweets
		MongoCursor<Document> cursor = tweetsCollection.find(Filters.eq("textIndexed",false)).iterator();
		List<Tweet> tweets = new ArrayList<>();

		// si esta activa la opcion de actualizar la flag
		if (setAsIndexed){
			tweetsCollection.updateMany(
					Filters.eq("textIndexed",false),
					new Document("$set", new Document("textIndexed",true))
					);
		}

		// Guardar como arreglo de tweets
		try {
			while (cursor.hasNext()) {
				tweets.add(Tweet.fromDocument(cursor.next()));
			}
		} finally {
			cursor.close();
		}

		return tweets.toArray(new Tweet[0]);
	}

	public Tweet[] getGraphUnindexedTweets(boolean setAsIndexed){
		// obtener tweets
		MongoCursor<Document> cursor = tweetsCollection.find(Filters.eq("userIndexed",false)).iterator();
		List<Tweet> tweets = new ArrayList<>();

		// si esta activa la opcion de actualizar la flag
		if (setAsIndexed){
			tweetsCollection.updateMany(
					Filters.eq("userIndexed",false),
					new Document("$set", new Document("userIndexed",true))
			);
		}

		// Guardar como arreglo de tweets
		try {
			while (cursor.hasNext()) {
				tweets.add(Tweet.fromDocument(cursor.next()));
			}
		} finally {
			cursor.close();
		}

		return tweets.toArray(new Tweet[0]);
	}

	public Tweet[] getGeoUnindexedTweets(boolean setAsIndexed){
		// obtener tweets
		MongoCursor<Document> cursor = tweetsCollection.find(Filters.eq("geoIndexed",false)).iterator();
		List<Tweet> tweets = new ArrayList<>();

		// si esta activa la opcion de actualizar la flag
		if (setAsIndexed){
			tweetsCollection.updateMany(
					Filters.eq("geoIndexed",false),
					new Document("$set", new Document("geoIndexed",true))
			);
		}

		// Guardar como arreglo de tweets
		try {
			while (cursor.hasNext()) {
				tweets.add(Tweet.fromDocument(cursor.next()));
			}
		} finally {
			cursor.close();
		}

		return tweets.toArray(new Tweet[0]);
	}


}
