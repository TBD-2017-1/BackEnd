package PoliTweetsCL.Core.BD;

import PoliTweetsCL.Core.Model.*;
import PoliTweetsCL.Core.Resources.Config;
import PoliTweetsCL.Core.Resources.Resources;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class MongoDBController {

	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoCollection<Document> tweetsCollection;

	public MongoDBController(Properties prop){
		String host;
		String user;
		String pass;

		if(prop == null) prop = new Properties();

		host = prop.getProperty("mongo.host","localhost");
		user = prop.getProperty("mongo.user","admin");
		pass = prop.getProperty("mongo.pass","DigitalOceanServer");

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

	public Tweet[] getUnindexedTweets(boolean setAsIndexed){
		// obtener tweets
		MongoCursor<Document> cursor = tweetsCollection.find(Filters.eq("indexed",false)).iterator();
		List<Tweet> tweets = new ArrayList<>();

		// si esta activa la opcion de actualizar la flag
		if (setAsIndexed){
			tweetsCollection.updateMany(
					Filters.eq("indexed",false),
					new Document("$set", new Document("indexed",true))
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
