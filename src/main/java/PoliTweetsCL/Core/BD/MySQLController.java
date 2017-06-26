package PoliTweetsCL.Core.BD;

import PoliTweetsCL.Core.Resources.Config;
import PoliTweetsCL.Core.Resources.Resources;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;


public class MySQLController {
    private Connection conn = null;

    public MySQLController(Properties prop) {
        String host;
        String user;
        String pass;

        if(prop == null) prop = new Properties();

        host = prop.getProperty("mysql.host","localhost");
        user = prop.getProperty("mysql.user","root");
        pass = prop.getProperty("mysql.pass","DigitalOceanServer");

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/PoliTweets?user="+user+"&password="+pass+"&useSSL=false");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Set<String> getKeywords(){
        try {
            /* Keywords */
            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery("SELECT k.value as value FROM keyword k join conglomerado_keyword c on k.id = c.idkeyword");

            Set<String> keywords = new HashSet<>();

            // get keyword value
            while (rs.next()){
                keywords.add(rs.getString("value"));
            }
            st.close();

            st = conn.createStatement();
            rs = st.executeQuery("SELECT k.value as value FROM keyword k join partido_keyword c on k.id = c.idkeyword");
            while (rs.next()){
                keywords.add(rs.getString("value"));
            }
            st.close();

            st = conn.createStatement();
            rs = st.executeQuery("SELECT k.value as value FROM keyword k join politico_keyword c on k.id = c.idkeyword");
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

    public void dropMetricas(){
        try {
            Statement st = conn.createStatement();
            String sql = "DELETE FROM politico_metrica";
            st.executeUpdate(sql);
            st.close();
            st = conn.createStatement();
            sql = "DELETE FROM partido_metrica";
            st.executeUpdate(sql);
            st.close();
            st = conn.createStatement();
            sql = "DELETE FROM conglomerado_metrica";
            st.executeUpdate(sql);
            st.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void execUpdate(String query){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate(query);
	        st.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }


    public ResultSet execQuery(String query){
        try{
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(query);
            st.close();
            return result;

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return null;
    }

    public JSONObject getRankingMetricaPolitico(String metrica, String fecha){
        try {
            String query = "SELECT CONCAT(p.nombre, ' ', p.apellido) AS nombre, m.valor AS valor\n" +
                    "FROM ( SELECT id FROM metrica WHERE nombre = '" + metrica + "') a\n" +
                    "    JOIN politico_metrica m ON a.id = m.idmetrica\n" +
                    "    JOIN politico p ON m.idpolitico = p.id\n" +
                    "WHERE m.fecha = '" + fecha + "' ORDER BY valor DESC LIMIT 10";

            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(query);

            // get results
            JSONArray array = new JSONArray();
            JSONObject row;
            while (result.next()) {
                row = new JSONObject();
                row.put("nombre", result.getString("nombre"));
                row.put("valor", result.getDouble("valor"));
                array.add(row);
            }

            st.close();

            row = new JSONObject();
            row.put("ranking",array);

            return row;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public JSONObject getRankingMetricaPoliticoASC(String metrica, String fecha){
        try {
            String query = "SELECT CONCAT(p.nombre, ' ', p.apellido) AS nombre, m.valor AS valor\n" +
                    "FROM ( SELECT id FROM metrica WHERE nombre = '" + metrica + "') a\n" +
                    "    JOIN politico_metrica m ON a.id = m.idmetrica\n" +
                    "    JOIN politico p ON m.idpolitico = p.id\n" +
                    "WHERE m.fecha = '" + fecha + "' ORDER BY valor ASC LIMIT 10";

            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(query);

            // get results
            JSONArray array = new JSONArray();
            JSONObject row;
            while (result.next()) {
                row = new JSONObject();
                row.put("nombre", result.getString("nombre"));
                row.put("valor", result.getDouble("valor"));
                array.add(row);
            }

            st.close();

            row = new JSONObject();
            row.put("ranking",array);

            return row;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public JSONObject getRankingMetricaEntidad(String entidad,String metrica, String fecha){
        try {
            String query = "SELECT p.nombre AS nombre, m.valor AS valor\n" +
                    "FROM ( SELECT id FROM metrica WHERE nombre = '" + metrica + "') a\n" +
                    "    JOIN "+entidad+"_metrica m ON a.id = m.idmetrica\n" +
                    "    JOIN "+entidad+" p ON m.id"+entidad+" = p.id\n" +
                    "WHERE m.fecha = '" + fecha + "' ORDER BY valor DESC LIMIT 10";

            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(query);

            // get results
            JSONArray array = new JSONArray();
            JSONObject row;
            while (result.next()) {
                row = new JSONObject();
                row.put("nombre", result.getString("nombre"));
                row.put("valor", result.getDouble("valor"));
                array.add(row);
            }

            st.close();

            row = new JSONObject();
            row.put("ranking",array);

            return row;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
