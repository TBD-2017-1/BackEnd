package PoliTweetsCL.Collector;

import PoliTweetsCL.Core.BD.MySQLController;
import PoliTweetsCL.Core.Resources.Config;
import twitter4j.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Singleton
public class TwitterStreaming {
	@EJB
	private Config config;

	private TwitterStream twitterStream;
	private Set<String> keywords;
	private boolean isActive;
	private boolean initialized = false;

	@PostConstruct
	public void init() {
		if(!initialized){
			isActive = false;
			twitterStream = new TwitterStreamFactory().getInstance();
			keywords = new HashSet<>();

			Properties prop = config.getPropertiesObj();

			MySQLController sqlDB = new MySQLController(prop);
			keywords = sqlDB.getKeywords();

			TwitterListener listener = new TwitterListener(prop);

			this.twitterStream.addListener(listener);
			initialized = true;
		}
	}

	public boolean start(){
		init();
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
