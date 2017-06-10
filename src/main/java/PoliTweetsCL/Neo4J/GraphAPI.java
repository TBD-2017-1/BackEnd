package PoliTweetsCL.Neo4J;

import PoliTweetsCL.Core.Resources.Config;
import PoliTweetsCL.Core.Utils.JSONizer;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import PoliTweetsCL.Core.Model.Tweet;
import PoliTweetsCL.Core.Model.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class GraphAPI {
    @EJB
    private Config config;

    private Driver driver;
    private Session session;

    private Logger logger = Logger.getLogger(getClass().getName());

    @PostConstruct
    public void openConnection(){
      this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( config.get("neo4j.user"), config.get("neo4j.pass") ) );
      this.session = null;
    }

    // retorna true si creo una nueva session, false si no lo hizo
    private boolean openSession() {
        if (this.session == null){
            this.session = this.driver.session();
            return true;
        }
        return false;
    }

    private void closeSession(boolean openedHere){
        if(openedHere){
            this.session.close();
            this.session = null;
        }
    }

    public void cleanDatabase(){
        boolean sessionFlag = openSession();
        this.session.run("match (a)-[r]->(b) delete r");
        this.session.run("match (n) delete n");
        closeSession(sessionFlag);
    }

    public void closeConnection(){
        this.driver.close();
    }

    public void createEntidadPolitica(String type,String name,String cuenta){
        boolean sessionFlag = openSession();
        if( cuenta!=null && cuenta.charAt(0) != '@' )
            cuenta = "@"+cuenta;

        String clase;
        if(cuenta!=null){
            clase = ":Entidad:Usuario:"+type;
        }else{
            clase = ":Entidad:"+type;
        }

        this.session.run( "CREATE ("+clase+" {nombre:'"+name+"', cuenta:'"+cuenta+"'})");
        closeSession(sessionFlag);
    }

    public void createUsuario(String cuenta){
        if (cuenta==null){
            return;
        }

        boolean sessionFlag = openSession();
        if( cuenta.charAt(0) != '@' )
            cuenta = "@"+cuenta;

        String query = "CREATE (:Usuario {cuenta:'"+cuenta+"'})";

        this.session.run( query);
        closeSession(sessionFlag);
    }

    private void createRelacion_Usuario_Entidad(String cuentaUsuario, String nombreEntidad){
        if (cuentaUsuario==null){
            return;
        }

        boolean sessionFlag = openSession();

        if( cuentaUsuario!=null && cuentaUsuario.charAt(0) != '@' )
            cuentaUsuario = "@"+cuentaUsuario;

        if(!existUsuario(cuentaUsuario))
            createUsuario(cuentaUsuario);

        String query = "match (a:Usuario {cuenta:'"+cuentaUsuario+"'}) " +
                        "match (b:Entidad {nombre:'"+nombreEntidad+"'}) "+
                        "create (a)-[" +
                            "r:Tweet{" +
                                "retweet:0, " +
                                "menciones:0, " +
                                "sentimiento:0 "+
                        "}]->(b)";

        this.session.run(query);
        closeSession(sessionFlag);
    }

    private void createRelacion_Usuario_Usuario(String cuenta1, String cuenta2){
        if(cuenta1==null || cuenta2 == null){
            return;
        }

        boolean sessionFlag = openSession();
        if( cuenta1.charAt(0) != '@' )
            cuenta1 = "@"+cuenta1;
        if( cuenta2.charAt(0) != '@' )
            cuenta2 = "@"+cuenta2;

        if(!existUsuario(cuenta1))
            createUsuario(cuenta1);
        if(!existUsuario(cuenta2))
            createUsuario(cuenta2);

        String query = "match (a:Usuario {cuenta:'"+cuenta1+"'}) " +
                        "match (b:Usuario {cuenta:'"+cuenta2+"'}) "+
                        "create (a)-[" +
                            "r:Tweet{" +
                                "retweet:0, " +
                                "menciones:0, " +
                                "sentimiento:0.0 "+
                        "}]->(b)";

        this.session.run(query);
        closeSession(sessionFlag);
    }

    public void addRetweet(String cuentaUsuario, String cuentaOrigen, float sentimiento){
        if(cuentaUsuario == null || cuentaOrigen == null) {
            return;
        }

        boolean sessionFlag = openSession();
        if( cuentaUsuario.charAt(0) != '@' )
            cuentaUsuario = "@"+cuentaUsuario;
        if( cuentaOrigen.charAt(0) != '@' )
            cuentaOrigen = "@"+cuentaOrigen;

        if(!existUsuario(cuentaUsuario))
            createUsuario(cuentaUsuario);
        if(!existUsuario(cuentaOrigen))
            return;

        StatementResult result;

        if (!existRelation_Usuario_Usuario(cuentaUsuario,cuentaOrigen))
            createRelacion_Usuario_Usuario(cuentaUsuario,cuentaOrigen);


        String query = "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Usuario:Entidad {cuenta:'"+cuentaOrigen+"'}) "+
                "return r";

        result = this.session.run(query);

        if (result.hasNext()) {
            Record record = result.next();

            int retweet = record.get("r").get("retweet").asInt();
            int menciones = record.get("r").get("menciones").asInt();
            double sentAcumulado = record.get("r").get("sentimiento").asFloat();

            int interacciones = menciones + retweet;
            retweet++;
            sentAcumulado = (sentimiento + sentAcumulado * interacciones) / (interacciones + 1);

            this.session.run(
                    "match (:Usuario {cuenta:'" + cuentaUsuario + "'})-[r:Tweet]->(:Usuario {cuenta:'" + cuentaOrigen + "'}) " +
                            "set r.retweet=" + retweet + ", r.sentimiento=" + sentAcumulado);
        }
        closeSession(sessionFlag);
    }

    public void addMencion(String cuentaUsuario, String nombreEntidad, float sentimiento){
        if(cuentaUsuario == null || nombreEntidad == null) {
            return;
        }

        boolean sessionFlag = openSession();
        if( cuentaUsuario.charAt(0) != '@' )
            cuentaUsuario = "@"+cuentaUsuario;

        if(!existUsuario(cuentaUsuario))
            createUsuario(cuentaUsuario);

        StatementResult result;

        if (!existRelation_Usuario_Entidad(cuentaUsuario,nombreEntidad))
            createRelacion_Usuario_Entidad(cuentaUsuario,nombreEntidad);

        String query = "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Entidad {nombre:'"+nombreEntidad+"'}) "+
                "return r";

        result = this.session.run(query);

        Record record = result.next();

        int retweet = record.get("r").get("retweet").asInt();
        int menciones = record.get("r").get("menciones").asInt();
        double sentAcumulado = record.get("r").get("sentimiento").asFloat();

        int interacciones = menciones + retweet;
        menciones++;
        sentAcumulado = (sentimiento + sentAcumulado*interacciones)/(interacciones+1);

        this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Entidad {nombre:'"+nombreEntidad+"'}) " +
                        "set r.menciones="+menciones+", r.sentimiento="+sentAcumulado);

        closeSession(sessionFlag);
    }

    private boolean existEntidad(String name){
        boolean sessionFlag = openSession();
        StatementResult result;
        result = this.session.run("match (a:Entidad {nombre:'"+name+"'}) return a");
        if(result.hasNext()){
            closeSession(sessionFlag);
            return true;
        }
        closeSession(sessionFlag);
        return false;
    }

    private boolean existUsuarioEntidad(String cuenta){
        boolean sessionFlag = openSession();
        StatementResult result;
        result = this.session.run("match (a:Entidad {cuenta:'"+cuenta+"'}) return a");
        if(result.hasNext()){
            closeSession(sessionFlag);
            return true;
        }
        closeSession(sessionFlag);
        return false;
    }

    private boolean existUsuario(String cuenta){
        boolean sessionFlag = openSession();
        if( cuenta!=null && cuenta.charAt(0) != '@' )
            cuenta = "@"+cuenta;

        StatementResult result;
        result = this.session.run("match (a:Usuario {cuenta:'"+cuenta+"'}) return a");
        if(result.hasNext()){
            closeSession(sessionFlag);
            return true;
        }
        closeSession(sessionFlag);
        return false;
    }

    private boolean existRelation_Usuario_Entidad(String cuentaUser, String nombreEntity){
        boolean sessionFlag = openSession();
        if( cuentaUser!=null && cuentaUser.charAt(0) != '@' )
            cuentaUser = "@"+cuentaUser;

        StatementResult result;
        result = this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUser+"'})-[r:Tweet]->(:Entidad {nombre:'"+nombreEntity+"'}) " +
                        "return r"
        );

        if(result.hasNext()){
            closeSession(sessionFlag);
            return true;
        }
        closeSession(sessionFlag);
        return false;
    }

    private boolean existRelation_Usuario_Usuario(String cuenta1, String cuenta2){
        boolean sessionFlag = openSession();
        if( cuenta1!=null && cuenta1.charAt(0) != '@' )
            cuenta1 = "@"+cuenta1;

        if( cuenta2!=null && cuenta2.charAt(0) != '@' )
            cuenta2 = "@"+cuenta2;

        StatementResult result;
        result = this.session.run(
                "match (:Usuario {cuenta:'"+cuenta1+"'})-[r:Tweet]->(:Usuario {cuenta:'"+cuenta2+"'}) " +
                        "return r"
        );

        if(result.hasNext()){
            closeSession(sessionFlag);
            return true;
        }
        closeSession(sessionFlag);
        return false;
    }
    
    //Metodos para consultas
    public StatementResult getMasInfluyentes(String entidad, int limit){
        boolean sessionFlag = openSession();
        StatementResult result;
        result = this.session.run("match (a:"+entidad+") with a limit "+limit+" "
                                + "match (b)-[r]->(a) return a, b, r");
        closeSession(sessionFlag);
        return result;
    }

}
