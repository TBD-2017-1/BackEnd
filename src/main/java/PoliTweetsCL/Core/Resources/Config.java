package PoliTweetsCL.Core.Resources;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

@Singleton
public class Config {
	@EJB
	private Resources resources;

	//Atributes
	private Properties app;

	Logger logger = Logger.getLogger(getClass().getName());


	//Methods
	@PostConstruct
	public void init(){
		try {
			// cargar configuracion de politweets
			app = new Properties();

			InputStream inputStream = resources.getResourceAsStream("app.properties");

			app.load(inputStream);
		}catch (Exception ex){
			logger.info("Error cargando properties: "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public Properties getPropertiesObj(){return app;}
	public String mongoGet(String property){return app.getProperty("mongo."+property);}
	public String mysqlGet(String property){return app.getProperty("mysql."+property);}
	public String appGet(String property){return app.getProperty("app."+property);}
	public String get(String property){return app.getProperty(property);}
}