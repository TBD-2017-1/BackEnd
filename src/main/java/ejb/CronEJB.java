package ejb;

import PoliTweetsCL.Core.BD.MongoDBController;
import PoliTweetsCL.Core.Model.Tweet;
import PoliTweetsCL.Core.Resources.Config;
import PoliTweetsCL.Core.Utils.JSONizer;
import PoliTweetsCL.Lucene.TextAPI;
import facade.*;
import model.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class CronEJB {
    @EJB
    private ConglomeradoFacade conglomeradoEJB;
    @EJB
    private PartidoFacade partidoEJB;
    @EJB
    private PoliticoFacade politicoEJB;
    @EJB
    private MetricaFacade metricaEJB;
    @EJB
    private ConglomeradoMetricaFacade conglomeradoMetricaEJB;
    @EJB
    private PartidoMetricaFacade partidoMetricaEJB;
    @EJB
    private PoliticoMetricaFacade politicoMetricaEJB;
    @EJB
    private TextAPI textAPI;
    @EJB
    private Config config;

    private Date now;
    private MongoDBController mongo;

    Logger logger = Logger.getLogger(getClass().getName());

    @PostConstruct
    public void init() {
        // connect to mongo
        mongo = new MongoDBController(config.getPropertiesObj());
        textAPI.nuevoIndice();
    }

    public void createIndex(){
        logger.info("Creando indice");
        textAPI.nuevoIndice();
    }

    public void doIndex(){
        logger.info("Doing CRON: Indexando");
        try {
            // obtener tweets no indexados
            Tweet[] tweets = mongo.getTextUnindexedTweets(true);

            // indexar en lucene
            textAPI.addTweets(tweets);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void doMetricas(){
        // Guardar el tiempo de la metrica
        //now = new Date();
        //now = Date.from(Instant.now().truncatedTo(ChronoUnit.HOURS));

        doIndex();

        logger.info("Doing CRON: Metricas");

        try {
            doMetricasPoliticos();
            //doMetricasPartidos();
            //doMetricasConglomerados();
        }catch (Exception ex){
            logger.severe("Error al crear metrica: "+ Arrays.toString(ex.getStackTrace()));
            ex.printStackTrace();
        }

        logger.info("Doing CRON: Nuevo indice");
        textAPI.nuevoIndice();
    }

    private void doMetricasPoliticos() throws Exception {
        List<Politico> politicos = politicoEJB.findAll();
        // para cada Politico
        for (Politico politico:politicos) {

            logger.info("Metrica de "+politico.getApellido());

            // seleccionar las keyword de politico
            List<Keyword> keywords = politico.getKeywords();
            List<String> kwArray = new ArrayList<>();
            for (Keyword word:keywords) {
                kwArray.add(word.getValue());
            }
            if(politico.getCuentaTwitter()!=null){
                kwArray.add(politico.getCuentaTwitter());
            }

            // hacer busqueda
            int hits = textAPI.buscarKeywords(kwArray.toArray(new String[0]));


            // obtener resultados de la busqueda anterior
            int positiveCount = textAPI.getPositiveCount();
            int negativeCount = textAPI.getNegativeCount();
            int neutralCount = textAPI.getNeutralCount();
            float aprobacion;
            if(hits != 0){
                aprobacion = 50 + 50 * (positiveCount-negativeCount)/(float)hits; // 50% base + (%pos - %neg)/2
            }else{
                aprobacion = 50;
            }

            logger.info(String.format(
                    "Metrica de %s\n" +
                            "Hits: %s\n" +
                            "Positivos: %d\n" +
                            "Negativos: %d\n" +
                            "Neutros: %d\n" +
                            "Aprobacion: %f",
                    politico.getApellido(),
                    String.valueOf(hits),
                    positiveCount,
                    negativeCount,
                    neutralCount,
                    aprobacion
            ));

            // guardar metrica en BD

            PoliticoMetrica registro = new PoliticoMetrica();
            Metrica metrica = metricaEJB.findByName("aprobacion");
            registro.setMetrica_politico(metrica);
            registro.setPolitico_metrica(politico);
            //registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(aprobacion);

            logger.info(JSONizer.toPrettyJSON(registro));

            politicoMetricaEJB.create(registro);

            registro = new PoliticoMetrica();
            registro.setMetrica_politico(metricaEJB.findByName("sentimientoPositivo"));
            registro.setPolitico_metrica(politico);
            //registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(positiveCount/(float)hits);
            politicoMetricaEJB.create(registro);

            registro = new PoliticoMetrica();
            registro.setMetrica_politico(metricaEJB.findByName("sentimientoNegativo"));
            registro.setPolitico_metrica(politico);
            //registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(negativeCount/(float)hits);
            politicoMetricaEJB.create(registro);

            registro = new PoliticoMetrica();
            registro.setMetrica_politico(metricaEJB.findByName("sentimientoNeutro"));
            registro.setPolitico_metrica(politico);
            //registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(neutralCount/(float)hits);
            politicoMetricaEJB.create(registro);
        }
    }


    private void doMetricasPartidos() throws Exception {
        List<Partido> partidos = partidoEJB.findAll();
        // para cada partido
        for (Partido partido:partidos) {
            // seleccionar las keyword de partido,
            List<Keyword> keywords = partido.getKeywords();
            List<String> kwArray = new ArrayList<>();
            for (Keyword word:keywords) {
                kwArray.add(word.getValue());
            }
            if(partido.getCuentaTwitter()!=null){
                kwArray.add(partido.getCuentaTwitter());
            }

            // hacer busqueda
            int hits = textAPI.buscarKeywords(kwArray.toArray(new String[0]));

            // obtener resultados de la busqueda anterior
            int positiveCount = textAPI.getPositiveCount();
            int negativeCount = textAPI.getNegativeCount();
            int neutralCount = textAPI.getNeutralCount();
            float aprobacion = 50 + 50 * (positiveCount-negativeCount)/(float)hits; // 50% base + (%pos - %neg)/2

            // guardar metrica en BD

            // guardar metrica en BD

            PartidoMetrica registro = new PartidoMetrica();
            registro.setMetrica_partido(metricaEJB.findByName("aprobacion"));
            registro.setPartido_metrica(partido);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(aprobacion);
            partidoMetricaEJB.create(registro);

            registro = new PartidoMetrica();
            registro.setMetrica_partido(metricaEJB.findByName("sentimientoPositivo"));
            registro.setPartido_metrica(partido);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(positiveCount/(float)hits);
            partidoMetricaEJB.create(registro);

            registro = new PartidoMetrica();
            registro.setMetrica_partido(metricaEJB.findByName("sentimientoNegativo"));
            registro.setPartido_metrica(partido);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(negativeCount/(float)hits);
            partidoMetricaEJB.create(registro);

            registro = new PartidoMetrica();
            registro.setMetrica_partido(metricaEJB.findByName("sentimientoNeutro"));
            registro.setPartido_metrica(partido);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(neutralCount/(float)hits);
            partidoMetricaEJB.create(registro);

        }
    }

    private void doMetricasConglomerados() throws Exception {
        List<Conglomerado> conglomerados = conglomeradoEJB.findAll();
        // para cada Conglomerado
        for (Conglomerado congl:conglomerados) {
            // seleccionar las keyword de conglomerado,
            List<Keyword> keywords = congl.getKeywords();
            List<String> kwArray = new ArrayList<>();
            for (Keyword word:keywords) {
                kwArray.add(word.getValue());
            }
            if(congl.getCuentaTwitter()!=null){
                kwArray.add(congl.getCuentaTwitter());
            }

            // hacer busqueda
            int hits = textAPI.buscarKeywords(kwArray.toArray(new String[0]));

            // obtener resultados de la busqueda anterior
            int positiveCount = textAPI.getPositiveCount();
            int negativeCount = textAPI.getNegativeCount();
            int neutralCount = textAPI.getNeutralCount();
            float aprobacion = 50 + 50 * (positiveCount-negativeCount)/(float)hits; // 50% base + (%pos - %neg)/2

            // guardar metrica en BD

            ConglomeradoMetrica registro = new ConglomeradoMetrica();
            registro.setMetrica(metricaEJB.findByName("aprobacion"));
            registro.setConglomerado(congl);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(aprobacion);
            conglomeradoMetricaEJB.create(registro);

            registro = new ConglomeradoMetrica();
            registro.setMetrica(metricaEJB.findByName("sentimientoPositivo"));
            registro.setConglomerado(congl);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(positiveCount/(float)hits);
            conglomeradoMetricaEJB.create(registro);

            registro = new ConglomeradoMetrica();
            registro.setMetrica(metricaEJB.findByName("sentimientoNegativo"));
            registro.setConglomerado(congl);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(negativeCount/(float)hits);
            conglomeradoMetricaEJB.create(registro);

            registro = new ConglomeradoMetrica();
            registro.setMetrica(metricaEJB.findByName("sentimientoNeutro"));
            registro.setConglomerado(congl);
            registro.setFecha(now);
            registro.setLugar("Desconocido");
            registro.setValor(neutralCount/(float)hits);
            conglomeradoMetricaEJB.create(registro);
        }

    }

}