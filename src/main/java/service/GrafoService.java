package service;

import PoliTweetsCL.Neo4J.GraphAPI;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import twitter4j.JSONArray;

@Path("/influencias")
public class GrafoService {
    @EJB
    GraphAPI graphEJB;

    @GET
    @Path("{entidad}")
    @Produces({"application/xml", "application/json"})
    public JSONArray getInfluencia(@PathParam("entidad") String entidad){
        List<String> grafo = graphEJB.getMasInfluyentes(entidad, 10);
        return new JSONArray(grafo);
    }

    @GET
    @Path("{entidad}/{limit}")
    @Produces({"application/xml", "application/json"})
    public JSONArray getInfluencia(@PathParam("entidad") String entidad, @PathParam("limit") Integer limit){
        List<String> grafo = graphEJB.getMasInfluyentes(entidad, 10);
        return new JSONArray(grafo);
    }
}
