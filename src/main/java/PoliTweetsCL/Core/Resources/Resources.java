package PoliTweetsCL.Core.Resources;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

@Stateless
public class Resources {

	Logger logger = Logger.getLogger(getClass().getName());

	public URL getResource(String resourceName){
		URL url = getClass().getClassLoader().getResource("/"+resourceName);
		if (url==null){
			logger.info("Unable to find /"+resourceName);
			url = getClass().getClassLoader().getResource(resourceName);
			if (url==null){
				logger.info("Unable to find "+resourceName);
			}
		}
		return url;
	}

	public InputStream getResourceAsStream(String resourceName){

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/"+resourceName);
		if (inputStream==null){
			logger.info("Unable to find /"+resourceName);
			inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
			if (inputStream==null){
				logger.info("Unable to find "+resourceName);
			}
		}
		return inputStream;
	}
}
