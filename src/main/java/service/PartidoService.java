package service;

import facade.KeywordFacade;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.PartidoFacade;
import model.Conglomerado;
import model.Keyword;
import model.Partido;
import model.Politico;

@Path("/partidos")
public class PartidoService {
	
    @EJB 
    PartidoFacade partidoFacadeEJB;
    @EJB
    KeywordFacade keywordFacadeEJB;
	
    Logger logger = Logger.getLogger(PartidoService.class.getName());
	
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Partido> findAll(){
    	return partidoFacadeEJB.findAll();
    }
	
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Partido find(@PathParam("id") Integer id) {
        return partidoFacadeEJB.find(id);
    }
	
    @GET
    @Path("{id}/politicos")
    @Produces({"application/xml", "application/json"})
    public List<Politico> getPoliticos(@PathParam("id") Integer id) {
        return partidoFacadeEJB.find(id).getListaPoliticos();
    }
	
    @GET
    @Path("{id}/keywords")
    @Produces({"application/xml", "application/json"})
    public List<Keyword> getKeywords(@PathParam("id") Integer id) {
        return partidoFacadeEJB.find(id).getKeywords();
    }
	
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Partido entity) {
        partidoFacadeEJB.create(entity);
    }
	
    @POST
    @Path("{id}/addkeyword")
    @Consumes({"application/xml", "application/json"})
    public void addKeyword(@PathParam("id") Integer id, Keyword keyword) {
        //Creacion nueva keyword
        keywordFacadeEJB.create(keyword);
        keyword = keywordFacadeEJB.findByValue(keyword);
        //Join
        Partido partido = partidoFacadeEJB.find(id);
        partido.addKeyword(keyword);
        keyword.addPartido(partido);
        //Merge a BD
        partidoFacadeEJB.edit(partido);
        keywordFacadeEJB.edit(keyword);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Partido entity) {
    	entity.setId(id.intValue());
        partidoFacadeEJB.edit(entity);
    }
    
    @PUT
    @Path("{id}/setconglomerado")
    @Consumes({"application/xml", "application/json"})
    public void editConglomerado(@PathParam("id") Integer id, Conglomerado conglomerado) {
        partidoFacadeEJB.find(id).setConglomerado(conglomerado);
    }
	
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        partidoFacadeEJB.remove(partidoFacadeEJB.find(id));
    }
    
    @DELETE
    @Path("{id}/removekeyword/{idkeyword}")
    public void removeKeyword(@PathParam("id") Integer id, @PathParam("idkeyword") Integer idkeyword){
        //Join
        Partido partido = partidoFacadeEJB.find(id);
        Keyword keyword = keywordFacadeEJB.find(idkeyword);
        partido.removeKeyword(keyword);
        keyword.removePartido(partido);
        //Merge a BD
        partidoFacadeEJB.edit(partido);
        if(keyword.isUnused()){
            keywordFacadeEJB.remove(keyword);
        }else{
            keywordFacadeEJB.edit(keyword);   
        }
    }
}
