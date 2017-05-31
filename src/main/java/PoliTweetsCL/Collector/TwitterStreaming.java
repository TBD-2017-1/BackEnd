package PoliTweetsCL.Collector;

import PoliTweetsCL.Core.BD.MongoDBController;
import PoliTweetsCL.Core.BD.MySQLController;
import PoliTweetsCL.Core.Misc.Config;
import PoliTweetsCL.Core.Model.Tweet;
import twitter4j.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	private SentimentAnalyzer sentimentAnalyzer;

	private Config config;


	private TwitterStreaming() {
		config = new Config();
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
		sentimentAnalyzer = new SentimentAnalyzer();
	}


	private void loadKeywords() {
		MySQLController sqlDB = new MySQLController(config.getPropertiesObj());

		keywords = sqlDB.getKeywords();
	}

	private void init() {
		StatusListener listener = new StatusListener() {
			private MongoDBController db = new MongoDBController(config.getPropertiesObj());

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
		};

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);

	}
	
	public static void main(String[] args) {
		new TwitterStreaming().init();
	}

}
