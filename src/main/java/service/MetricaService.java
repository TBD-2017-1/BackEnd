package service;

import PoliTweetsCL.Core.BD.MySQLController;
import PoliTweetsCL.Core.Resources.Config;
import ejb.CronEJB;
import ejb.PoliticoMetricaFacadeEJB;
import facade.ConglomeradoFacade;
import facade.ConglomeradoMetricaFacade;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
import facade.PartidoFacade;
import facade.PartidoMetricaFacade;
import facade.PoliticoFacade;
import facade.PoliticoMetricaFacade;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Conglomerado;
import model.ConglomeradoMetrica;
import model.Metrica;
import model.Partido;
import model.PartidoMetrica;
import model.PoliticoMetrica;
import model.Politico;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Path("/metricas")
public class MetricaService {
	@EJB
    Config config;
    @EJB 
    MetricaFacade metricaFacadeEJB;
    //EJB Conglomerados
    @EJB
    ConglomeradoMetricaFacade metricaConglomeradoEJB;
    @EJB
    ConglomeradoFacade conglomeradoEJB;
    //EJB Partidos
    @EJB
    PartidoMetricaFacade metricaPartidoEJB;
    @EJB
    PartidoFacade partidoEJB;
    //EJB Politicos
    @EJB
    PoliticoMetricaFacade metricaPoliticoEJB;
    @EJB
    PoliticoFacade politicoEJB;
    @EJB
    CronEJB cron;

	
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
    @Path("{metrica}/conglomerados/{id}")
    @Produces({"application/xml", "application/json"})
    public List<ConglomeradoMetrica> getMetricaConglomerados(@PathParam("metrica") String nombreMetrica, @PathParam("id") Integer idconglomerado) {
        Conglomerado c = conglomeradoEJB.find(idconglomerado);
        Metrica m = metricaFacadeEJB.findByName(nombreMetrica);
        List<ConglomeradoMetrica> listCM = m.getConglomeradoMetrica();//Se obtienen relaciones metrica_conglomerado.
        List<ConglomeradoMetrica> newListCM = new ArrayList<>();
        for(ConglomeradoMetrica cm : listCM){
            if (cm.getConglomerado().getId() == c.getId()){
                newListCM.add(cm);                                                                                                                                                                                                                              
            }
        }

        if(newListCM.size() > 7){
            newListCM =  newListCM.subList(0, 6);
        }
        Collections.reverse(newListCM);
        return newListCM;
    }
    
    @GET
    @Path("{metrica}/partidos")
    @Produces({"application/xml", "application/json"})
    public List<PartidoMetrica> getMetricaPartidos(@PathParam("metrica") String nombreMetrica) {
        return metricaFacadeEJB.findByName(nombreMetrica).getPartidoMetrica();
    }
    
    @GET
    @Path("{metrica}/partidos/{id}")
    @Produces({"application/xml", "application/json"})
    public List<PartidoMetrica> getMetricaPartidos(@PathParam("metrica") String nombreMetrica, @PathParam("id") Integer idpartido) {
        Partido p = partidoEJB.find(idpartido);
        Metrica m = metricaFacadeEJB.findByName(nombreMetrica);
        List<PartidoMetrica> listPM = m.getPartidoMetrica();//Se obtienen relaciones metrica_partido.
        List<PartidoMetrica> newListPM = new ArrayList<>();
        for(PartidoMetrica pm : listPM){
            if (pm.getPartido().getId() == p.getId()){
                newListPM.add(pm);
            }
        }

        if(newListPM.size() > 7){
            newListPM =  newListPM.subList(0, 6);
        }
        Collections.reverse(newListPM);
        return newListPM;
    }
    
    @GET
    @Path("{metrica}/politicos")
    @Produces({"application/xml", "application/json"})
    public List<PoliticoMetrica> getMetricaPoliticos(@PathParam("metrica") String nombreMetrica) {
        return metricaFacadeEJB.findByName(nombreMetrica).getPoliticoMetrica();
    }
    
    @GET
    @Path("{metrica}/politicos/{id}")
    @Produces({"application/xml", "application/json"})
    public List<PoliticoMetrica> getMetricaPoliticos(@PathParam("metrica") String nombreMetrica, @PathParam("id") Integer idpolitico) {
        Politico p = politicoEJB.find(idpolitico);
        Metrica m = metricaFacadeEJB.findByName(nombreMetrica);
        List<PoliticoMetrica> listPM = m.getPoliticoMetrica();//Se obtienen relaciones metrica_politico.
        List<PoliticoMetrica> newListPM = new ArrayList<>();
        for(PoliticoMetrica pm : listPM){
            if (pm.getPolitico().getId() == p.getId()){
                newListPM.add(pm);
            }
        }
        if(newListPM.size() > 7){
            newListPM =  newListPM.subList(0, 6);
        }
        Collections.reverse(newListPM);
        return newListPM;
    }

    @GET
    @Path("{metrica}/ranking/politicos")
    @Produces({"application/xml", "application/json"})
    public Response getRankMetricaPoliticos(@PathParam("metrica") String nombreMetrica) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedNow = formatter.format(now);

        MySQLController mysql = new MySQLController(config.getPropertiesObj());

        String response = mysql.getRankingMetricaPolitico("aprobacion",formattedNow);

        return Response.status(Response.Status.OK).entity(response).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{metrica}/ranking/partidos")
    @Produces({"application/xml", "application/json"})
    public Response getRankMetricaPartidos(@PathParam("metrica") String nombreMetrica) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedNow = formatter.format(now);

        MySQLController mysql = new MySQLController(config.getPropertiesObj());

        String response = mysql.getRankingMetricaEntidad("partido","aprobacion",formattedNow);

        return Response.status(Response.Status.OK).entity(response).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("{metrica}/ranking/conglomerados")
    @Produces({"application/xml", "application/json"})
    public Response getRankMetricaConglomerados(@PathParam("metrica") String nombreMetrica) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedNow = formatter.format(now);

        MySQLController mysql = new MySQLController(config.getPropertiesObj());

        String response = mysql.getRankingMetricaEntidad("conglomerado","aprobacion",formattedNow);

        return Response.status(Response.Status.OK).entity(response).type(MediaType.APPLICATION_JSON).build();
    }


	
    @POST
    @Path("testCreate")
    //@Consumes({"application/xml", "application/json"})
    public void testCreate() {
        MySQLController mysql = new MySQLController(config.getPropertiesObj());
        mysql.dropMetricas();
        cron.createTestMetricas();
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
