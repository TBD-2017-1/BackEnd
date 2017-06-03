package service;

import ejb.PoliticoMetricaFacadeEJB;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.MetricaFacade;
import facade.PoliticoFacade;
import facade.PoliticoMetricaFacade;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import model.ConglomeradoMetrica;
import model.Metrica;
import model.PartidoMetrica;
import model.PoliticoMetrica;
import model.Politico;

@Path("/metricas")
public class MetricaService {
	
    @EJB 
    MetricaFacade metricaFacadeEJB;
    
    @EJB
    PoliticoMetricaFacade metricaPoliticoEJB;
    
    @EJB
    PoliticoFacade politicoEJB;
	
    Logger logger = Logger.getLogger(MetricaService.class.getName());
	
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Metrica> findAll(){
    	return metricaFacadeEJB.findAll();
    }
	
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Metrica find(@PathParam("id") Integer id) {
        return metricaFacadeEJB.find(id);
    }
    
    @GET
    @Path("{metrica}/conglomerados")
    @Produces({"application/xml", "application/json"})
    public List<ConglomeradoMetrica> getMetricaConglomerados(@PathParam("metrica") String nombreMetrica) {
        return metricaFacadeEJB.findByName(nombreMetrica).getConglomeradoMetrica();
    }
    
    @GET
    @Path("{metrica}/partidos")
    @Produces({"application/xml", "application/json"})
    public List<PartidoMetrica> getMetricaPartidos(@PathParam("metrica") String nombreMetrica) {
        return metricaFacadeEJB.findByName(nombreMetrica).getPartidoMetrica();
    }
    
    @GET
    @Path("{metrica}/politicos")
    @Produces({"application/xml", "application/json"})
    public List<PoliticoMetrica> getMetricaPoliticos(@PathParam("metrica") String nombreMetrica) {
        return metricaFacadeEJB.findByName(nombreMetrica).getPoliticoMetrica();
    }
	
    @POST
    @Path("testCreate")
    //@Consumes({"application/xml", "application/json"})
    public void testCreate() {
        float valor = 50;
        Metrica m = metricaFacadeEJB.findByName("Aprobacion");
        List<Politico> p = politicoEJB.findAll();
        PoliticoMetrica pm = new PoliticoMetrica();
        pm.setLugar("Aca");
        pm.setValor(valor);
        pm.setMetrica_politico(m);
        pm.setPolitico_metrica(p.get(2));
        metricaPoliticoEJB.create(pm);
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Metrica entity) {
        metricaFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Metrica entity) {
    	entity.setId(id.intValue());
        metricaFacadeEJB.edit(entity);
    }
	
    @DELETE
    @Path("{id}")
    public void remove(Metrica entity) {
        metricaFacadeEJB.remove(entity);
    }
}
