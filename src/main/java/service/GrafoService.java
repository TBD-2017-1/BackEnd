package service;

import PoliTweetsCL.Neo4J.GraphAPI;
import java.util.List;

import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

@Path("/influencias")
public class GrafoService {
    @EJB
    GraphAPI graphEJB;

    @GET
    @Path("{entidad}")
    @Produces({"application/xml", "application/json"})
    public Response getInfluencia(@PathParam("entidad") String entidad){
        List<String> grafo = graphEJB.getMasInfluyentes(entidad, 10);
        return Response.status(Response.Status.OK).entity(grafo.toString()).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{entidad}/{limit}")
    @Produces({"application/xml", "application/json"})
    public Response getInfluencia(@PathParam("entidad") String entidad, @PathParam("limit") Integer limit){
        List<String> grafo = graphEJB.getMasInfluyentes(entidad, limit);
        return Response.status(Response.Status.OK).entity(grafo.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
