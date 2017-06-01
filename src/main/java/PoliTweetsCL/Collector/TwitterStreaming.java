package PoliTweetsCL.Collector;

import PoliTweetsCL.Core.BD.MySQLController;
import PoliTweetsCL.Core.Resources.Config;
import twitter4j.*;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class TwitterStreaming {
	private Config config;
	private MySQLController sqlDB;

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	private final Object lock = new Object();
	private boolean isActive;

	public TwitterStreaming() {
		config = new Config();
		sqlDB = new MySQLController();
		isActive = false;
		twitterStream = new TwitterStreamFactory().getInstance();
		keywords = new HashSet<>();
		keywords = sqlDB.getKeywords();

		StatusListener listener = new TwitterListener();
		this.twitterStream.addListener(listener);
	}

	public boolean start(){
		if(!isActive) {
			FilterQuery fq = new FilterQuery();
			fq.track(keywords.toArray(new String[0]));
			this.twitterStream.filter(fq);
			isActive = true;
		}
		return getStatus();
	}

	public boolean stop(){
		if(isActive){
			twitterStream.cleanUp();
			twitterStream.shutdown();
			isActive = false;
		}
		return getStatus();
	}

	public boolean getStatus(){return isActive;}
}
