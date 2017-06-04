package service;

import PoliTweetsCL.Neo4J.GraphAPI;
import ejb.GraphEJB;

import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.neo4j.driver.v1.StatementResult;

@Path("/influencias")
public class GrafoService {
    @EJB
    GraphAPI graphEJB;

    @GET
    @Path("conglomerados")
    @Produces({"application/xml", "application/json"})
    public StatementResult getInfluenciaConglomerados(){
        StatementResult grafo = graphEJB.getMasInfluyentes("conglomerado", 100);
        return grafo;
    }
    
    @GET
    @Path("partidos")
    @Produces({"application/xml", "application/json"})
    public StatementResult getInfluenciaPartidos(){
        StatementResult grafo = graphEJB.getMasInfluyentes("partido", 100);
        return grafo;
    }
    
    @GET
    @Path("politicos")
    @Produces({"application/xml", "application/json"})
    public StatementResult getInfluenciaPoliticos(){
        StatementResult grafo = graphEJB.getMasInfluyentes("politico", 100);
        return grafo;
    }

}
