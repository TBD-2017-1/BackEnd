 package service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.KeywordFacade;
import model.Conglomerado;
import model.Keyword;

@Path("/keywords")
public class KeywordService {
	
    @EJB 
    KeywordFacade keywordFacadeEJB;
	
    Logger logger = Logger.getLogger(KeywordService.class.getName());
	
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Keyword> findAll(){
            return keywordFacadeEJB.findAll();
    }
	
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Keyword find(@PathParam("id") Integer id) {
        return keywordFacadeEJB.find(id);
    }
	
    @GET
    @Path("{id}/partidos")
    @Produces({"application/xml", "application/json"})
    public List<Conglomerado> getConglomerados(@PathParam("id") Integer id) {
        return keywordFacadeEJB.find(id).getConglomerados();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Keyword entity) {
    	entity.setId(id.intValue());
        keywordFacadeEJB.edit(entity);
    }
	
}
