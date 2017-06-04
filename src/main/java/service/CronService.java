package service;

import ejb.CronEJB;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/cron")
public class CronService {
    @EJB
    CronEJB cron;

    @POST
    @Path("index")
    public void doIndex(){
        cron.doIndex();
    }

    @POST
    @Path("newindex")
    public void doNewIndex(){
        cron.createIndex();
    }

    @POST
    @Path("metricas")
    public void doMetrica(){
        cron.doMetricas();
    }

}