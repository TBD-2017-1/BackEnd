package service;

import PoliTweetsCL.Neo4J.GraphAPI;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/influencias")
public class GrafoService {
    @EJB
    GraphAPI graphEJB;

    @GET
    @Path("{entidad}")
    @Produces({"application/xml", "application/json"})
    public Gson getInfluencia(@PathParam("entidad") String entidad){
        Gson grafo = graphEJB.getMasInfluyentes(entidad, 10);
        return grafo;
    }

    @GET
    @Path("{entidad}/{limit}")
    @Produces({"application/xml", "application/json"})
    public Gson getInfluencia(@PathParam("entidad") String entidad, @PathParam("limit") Integer limit){
        Gson grafo = graphEJB.getMasInfluyentes(entidad, limit);
        return grafo;
    }
}
