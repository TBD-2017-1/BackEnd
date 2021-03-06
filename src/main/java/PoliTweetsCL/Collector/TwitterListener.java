package PoliTweetsCL.Collector;

import PoliTweetsCL.Core.BD.MongoDBController;
import PoliTweetsCL.Core.Model.Tweet;
import PoliTweetsCL.Core.Resources.Config;
import PoliTweetsCL.Neo4J.GraphAPI;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.InitialContext;
import java.util.Objects;
import java.util.Properties;


public class TwitterListener implements StatusListener{
	private MongoDBController db;
	private SentimentAnalyzer sentimentAnalyzer;



	public TwitterListener(Properties prop){
		try {
			db = new MongoDBController(prop);
			sentimentAnalyzer = new SentimentAnalyzer();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void onStatus(Status status) {
		if(Objects.equals(status.getLang(), "es")){
			// generar tweet
			Tweet tweet = new Tweet(status);

			// obtener sentimiento
			tweet.setSentimiento(sentimentAnalyzer.findSentiment(tweet.getText()));

			// guardar en mongodb
			db.saveTweet(tweet);
		}
	}


	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	}

	public void onException(Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onStallWarning(StallWarning arg0) {

	}

}
