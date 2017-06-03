package PoliTweetsCL.Neo4J;

import PoliTweetsCL.Core.Resources.Config;
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

@Stateless
public class GraphAPI {
    @EJB
    private Config config;

    private Driver driver;
    private Session session;

    @PostConstruct
    public void openConnection(){
      this.driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( config.get("neo4j.user"), config.get("neo4j.pass") ) );
      this.session = this.driver.session();
    }

    public void cleanDatabase(){
        this.session.run("match (a)-[r]->(b) delete r");
        this.session.run("match (n) delete n");
    }

    public void closeConnection(){
        this.session.close();
        this.driver.close();
    }

    public void createEntidadPolitica(String type,String name,String cuenta){
        if( cuenta!=null && cuenta.charAt(0) != '@' )
            cuenta = "@"+cuenta;

        String clase;
        if(cuenta!=null){
            clase = ":Entidad:Usuario:"+type;
        }else{
            clase = ":Entidad:"+type;
        }

        this.session.run( "CREATE ("+clase+" {nombre:'"+name+"', cuenta:'"+cuenta+"'})");
    }

    public void createUsuario(String cuenta){
        if( cuenta!=null && cuenta.charAt(0) != '@' )
            cuenta = "@"+cuenta;

        this.session.run( "CREATE (:Usuario {cuenta:'"+cuenta+"'})");
    }

    private void createRelacion_Usuario_Entidad(String cuentaUsuario, String nombreEntidad){
        if( cuentaUsuario!=null && cuentaUsuario.charAt(0) != '@' )
            cuentaUsuario = "@"+cuentaUsuario;

        this.session.run(
                "match (a:Usuario {cuenta:'"+cuentaUsuario+"'}) " +
                    "match (b:Entidad {nombre:"+nombreEntidad+"}) "+
                    "create (a)-[" +
                        "r:Tweet{" +
                            "retweet:0, " +
                            "menciones:0, " +
                            "sentimiento:0 "+
                    "}]->(b)"
        );
    }

    private void createRelacion_Usuario_Usuario(String cuenta1, String cuenta2){
        if( cuenta1!=null && cuenta1.charAt(0) != '@' )
            cuenta1 = "@"+cuenta1;
        if( cuenta2!=null && cuenta2.charAt(0) != '@' )
            cuenta2 = "@"+cuenta2;

        this.session.run(
                "match (a:Usuario {cuenta:'"+cuenta1+"'}) " +
                    "match (a:Usuario {cuenta:'"+cuenta2+"'}) "+
                    "create (a)-[" +
                        "r:Tweet{" +
                            "retweet:0, " +
                            "menciones:0, " +
                            "sentimiento:0.0 "+
                    "}]->(b)"
        );
    }

    public void addRetweet(String cuentaUsuario, String cuentaOrigen, float sentimiento){
        if( cuentaUsuario!=null && cuentaUsuario.charAt(0) != '@' )
            cuentaUsuario = "@"+cuentaUsuario;
        if( cuentaOrigen!=null && cuentaOrigen.charAt(0) != '@' )
            cuentaOrigen = "@"+cuentaOrigen;

        StatementResult result;

        if (!existRelation_Usuario_Usuario(cuentaUsuario,cuentaOrigen))
            createRelacion_Usuario_Usuario(cuentaUsuario,cuentaOrigen);


        result = this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Usuario {cuenta:'"+cuentaOrigen+"'}) "+
                    "return r"
                );

        Record record = result.next();
        int retweet = Integer.parseInt(record.get("retweet").toString());
        int menciones = Integer.parseInt(record.get("menciones").toString());
        float sentAcumulado = Float.parseFloat(record.get("sentimiento").toString());

        int interacciones = menciones + retweet;
        retweet++;
        sentAcumulado = sentimiento + sentAcumulado*interacciones/(interacciones+1);

        this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Usuario {cuenta:'"+cuentaOrigen+"'}) " +
                        "set r.retweet="+retweet+", r.sentiminento="+sentAcumulado);
    }

    public void addMencion(String cuentaUsuario, String nombreEntidad, float sentimiento){
        if( cuentaUsuario!=null && cuentaUsuario.charAt(0) != '@' )
            cuentaUsuario = "@"+cuentaUsuario;

        StatementResult result;

        if (!existRelation_Usuario_Entidad(cuentaUsuario,nombreEntidad))
            createRelacion_Usuario_Entidad(cuentaUsuario,nombreEntidad);


        result = this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Entidad {nombre:'"+nombreEntidad+"'}) "+
                        "return r"
        );

        Record record = result.next();
        int retweet = Integer.parseInt(record.get("retweet").toString());
        int menciones = Integer.parseInt(record.get("menciones").toString());
        float sentAcumulado = Float.parseFloat(record.get("sentimiento").toString());

        int interacciones = menciones + retweet;
        menciones++;
        sentAcumulado = sentimiento + sentAcumulado*interacciones/(interacciones+1);

        this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUsuario+"'})-[r:Tweet]->(:Entidad {nombre:'"+nombreEntidad+"'}) " +
                        "set r.menciones="+menciones+", r.sentiminento="+sentAcumulado);
    }

    private boolean existEntidad(String name){
        StatementResult result;
        result = this.session.run("match (:Entidad {nombre:'"+name+"'}) return a");
        if(result.hasNext()){
            return true;
        }
        return false;
    }

    private boolean existUsuario(String cuenta){
        if( cuenta!=null && cuenta.charAt(0) != '@' )
            cuenta = "@"+cuenta;

        StatementResult result;
        result = this.session.run("match (:Usuario {cuenta:'"+cuenta+"'}) return a");
        if(result.hasNext()){
          return true;
        }
        return false;
    }

    private boolean existRelation_Usuario_Entidad(String cuentaUser, String nombreEntity){
        if( cuentaUser!=null && cuentaUser.charAt(0) != '@' )
            cuentaUser = "@"+cuentaUser;

        StatementResult result;
        result = this.session.run(
                "match (:Usuario {cuenta:'"+cuentaUser+"'})-[r:Tweet]->(:Entidad {nombre:"+nombreEntity+"}) " +
                        "return r"
        );

        if(result.hasNext()){
          return true;
        }
        return false;
    }

    private boolean existRelation_Usuario_Usuario(String cuenta1, String cuenta2){
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
            return true;
        }
        return false;
    }
/*
    //Este procedimiento debe realizarse 3 veces, para conglomerados, partidos, y politicos.
    public void mapDatabase(Tweet[] tweets, Map<String, String> users, String entidad){
        StatementResult result;

        for (Map.Entry<String, String> entry : users.entrySet()){
          this.createNode(entidad, entry.getKey(), entry.getValue());
        }

        for(Tweet t : tweets){
          if(t.getRetweetedStatus() != null){
            String twitter = t.getUser().getScreenName();
            String retweeted = t.getRetweetedStatus().getUser().getScreenName();
            if(users.containsValue(retweeted)){
              if(!existNode("User", twitter)){
                this.createNode("User", twitter, twitter);
              }
              this.createTweetRelation(twitter, retweeted);
            }
          }
        }
    }

    */
}
