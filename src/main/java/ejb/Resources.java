package ejb;

import javax.ejb.Stateless;
import java.io.InputStream;
import java.net.URL;

@Stateless
public class Resources {

	public URL getResource(String resourceName){
		return getClass().getResource(resourceName);
	}

	public InputStream getResourceAsStream(String resourceName){
		return getClass().getClassLoader().getResourceAsStream(resourceName);
	}
}
