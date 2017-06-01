package PoliTweetsCL.Core.Resources;

import ejb.Resources;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Properties;
import java.util.logging.Logger;

@Startup
@Singleton
public class Config {
	 private Resources resources;

	//Atributes
	private Properties app;

	Logger logger = Logger.getLogger(getClass().getName());


	//Methods
	@PostConstruct
	public void init(){
		resources = new Resources();
		try {
			// cargar configuracion de politweets
			app = new Properties();
			app.load(resources.getResourceAsStream("app.properties"));
		}catch (Exception ex){
			Logger logger = Logger.getLogger("Error cargando properties: "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public Properties getPropertiesObj(){return app;}
	public String mongoGet(String property){return app.getProperty("mongo."+property);}
	public String mysqlGet(String property){return app.getProperty("mysql."+property);}
	public String appGet(String property){return app.getProperty("app."+property);}
	public String get(String property){return app.getProperty(property);}
}