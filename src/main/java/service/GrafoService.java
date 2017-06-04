package service;

import ejb.GraphEJB;

import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/grafo")
public class GrafoService {
    @EJB GraphEJB graphEJB;



}
