package PoliTweetsCL.Core.BD;

import ejb.Resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;


public class MySQLController {
    Resources resources;

    private Connection conn = null;

    Logger logger = Logger.getLogger(getClass().getName());

    public MySQLController() {
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
        }finally {
            if(prop == null){
                host = "localhost";
                user = "root";
                pass = "DigitalOceanServer";
            }else {
                host = prop.getProperty("mysql.host");
                user = prop.getProperty("mysql.user");
                pass = prop.getProperty("mysql.pass");
            }
        }

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/PoliTweets?user="+user+"&password="+pass);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public MySQLController(Properties prop) {
        String host;
        String user;
        String pass;

        if(prop == null){
            host = "localhost";
            user = "root";
            pass = "DigitalOceanServer";
        }else {
            host = prop.getProperty("mysql.host");
            user = prop.getProperty("mysql.user");
            pass = prop.getProperty("mysql.pass");
        }

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/PoliTweets?user="+user+"&password="+pass);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public Set<String> getKeywords(){
        try {
            /* Keywords */
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery("SELECT * FROM keyword");

            Set<String> keywords = new HashSet<>();

            // get keyword value
            while (rs.next()){
                keywords.add(rs.getString("value"));
            }
            st.close();

            /* Add Twitter username for mentions */
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM conglomerado");
            while (rs.next()){
                String username = rs.getString("cuentaTwitter");
                if(!rs.wasNull())
                    keywords.add("@"+username);
            }
            st.close();

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM partido");
            while (rs.next()){
                String username = rs.getString("cuentaTwitter");
                if(!rs.wasNull())
                    keywords.add("@"+username);
            }
            st.close();

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM politico");
            while (rs.next()){
                String username = rs.getString("cuentaTwitter");
                if(!rs.wasNull())
                    keywords.add("@"+username);
            }
            st.close();


            return keywords;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

}
