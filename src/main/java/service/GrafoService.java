package service;

import PoliTweetsCL.Neo4J.GraphAPI;

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
    @Path("{entidad}")
    @Produces({"application/xml", "application/json"})
    public StatementResult getInfluencia(@PathParam("entidad") String entidad){
        StatementResult grafo = graphEJB.getMasInfluyentes(entidad, 10);
        return grafo;
    }

    @GET
    @Path("{entidad}/{limit}")
    @Produces({"application/xml", "application/json"})
    public StatementResult getInfluencia(@PathParam("entidad") String entidad, @PathParam("limit") Integer limit){
        StatementResult grafo = graphEJB.getMasInfluyentes(entidad, limit);
        return grafo;
    }
}
