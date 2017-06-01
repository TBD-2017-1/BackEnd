package services;

import ejb.DaemonEJB;

import javax.ejb.EJB;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/daemon")
public class DaemonService {
    @EJB
    DaemonEJB daemon;

    @GET
    @Path("toggle")
    @Produces({"application/xml", "application/json"})
    public Response toggleDaemon(){
        String response = Json.createObjectBuilder()
                .add("status",daemon.toggle())
                .build()
                .toString();
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("status")
    @Produces({"application/xml", "application/json"})
    public Response getDaemonStatus(){
        String response = Json.createObjectBuilder()
                .add("status",daemon.getStatus())
                .build()
                .toString();

        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("{status}")
    @Produces({"application/xml", "application/json"})
    public Response setDaemonStatus(@PathParam("status") boolean status){
    	if(status){
    	    daemon.start();
        }else {
    	    daemon.stop();
        }
        return Response.status(Response.Status.OK).build();
    }
	
}
