package service;

import ejb.CronEJB;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/cron")
public class CronService {
    @EJB
    CronEJB cron;

    @POST
    @Path("index")
    @Produces({"application/xml", "application/json"})
    public Response doIndex(){
        cron.doIndex();
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("metricas")
    @Produces({"application/xml", "application/json"})
    public Response doMetrica(){
        cron.doMetricas();
        return Response.status(Response.Status.OK).build();
    }

}