package ejb;

import PoliTweetsCL.Core.BD.MongoDBController;
import PoliTweetsCL.Core.BD.MySQLController;
import PoliTweetsCL.Core.Model.Tweet;
import PoliTweetsCL.Core.Resources.Config;
import PoliTweetsCL.Core.Utils.JSONizer;
import PoliTweetsCL.Lucene.TextAPI;
import facade.*;
import java.text.SimpleDateFormat;
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
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton
@LocalBean
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

    private MongoDBController mongo;
    private MySQLController mysql;
    private Date now;
    private String formattedNow;

    Logger logger = Logger.getLogger(getClass().getName());

    @PostConstruct
    public void init() {
        // connect to mongo
        mongo = new MongoDBController(config.getPropertiesObj());
	// connect to MySQL - for INSERT queries
	mysql = new MySQLController(config.getPropertiesObj());
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
        this.now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.formattedNow = formatter.format(now);
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

            //Guardar metrica en BD
            Metrica metrica;
            int idpolitico = politico.getId();
            int idmetrica;
            String lugar = "Desconocido";
            String query;
            
            //Aprobacion
            metrica = metricaEJB.findByName("aprobacion");
            idmetrica = metrica.getId();
            query = "INSERT INTO politico_metrica (idpolitico, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpolitico+", "+idmetrica+", "+aprobacion+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimiento Positivo
            metrica = metricaEJB.findByName("sentimientoPositivo");
            idmetrica = metrica.getId();
            query = "INSERT INTO politico_metrica (idpolitico, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpolitico+", "+idmetrica+", "+(float)positiveCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimiento Negativo
            metrica = metricaEJB.findByName("sentimientoNegativo");
            idmetrica = metrica.getId();
            query = "INSERT INTO politico_metrica (idpolitico, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpolitico+", "+idmetrica+", "+(float)negativeCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimient Neutro
            metrica = metricaEJB.findByName("sentimientoNeutro");
            idmetrica = metrica.getId();
            query = "INSERT INTO politico_metrica (idpolitico, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpolitico+", "+idmetrica+", "+(float)neutralCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Antes de calculaba: 
            //      sentimiento(Positivo|Negativo|Neutral) = (positive|negative|neutral)Count/(float)hits;
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

            //Guardar metrica en BD
            Metrica metrica;
            int idpartido = partido.getId();
            int idmetrica;
            String lugar = "Desconocido";
            String query;
            
            //Aprobacion
            metrica = metricaEJB.findByName("aprobacion");
            idmetrica = metrica.getId();
            query = "INSERT INTO partido_metrica (idpartido, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpartido+", "+idmetrica+", "+aprobacion+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimiento Positivo
            metrica = metricaEJB.findByName("sentimientoPositivo");
            idmetrica = metrica.getId();
            query = "INSERT INTO partido_metrica (idpartido, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpartido+", "+idmetrica+", "+(float)positiveCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimiento Negativo
            metrica = metricaEJB.findByName("sentimientoNegativo");
            idmetrica = metrica.getId();
            query = "INSERT INTO partido_metrica (idpartido, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpartido+", "+idmetrica+", "+(float)negativeCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimient Neutro
            metrica = metricaEJB.findByName("sentimientoNeutro");
            idmetrica = metrica.getId();
            query = "INSERT INTO partido_metrica (idpartido, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idpartido+", "+idmetrica+", "+(float)neutralCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Antes de calculaba: 
            //      sentimiento(Positivo|Negativo|Neutral) = (positive|negative|neutral)Count/(float)hits;

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

            //Guardar metrica en BD
            Metrica metrica;
            int idconglomerado = congl.getId();
            int idmetrica;
            String lugar = "Desconocido";
            String query;
            
            //Aprobacion
            metrica = metricaEJB.findByName("aprobacion");
            idmetrica = metrica.getId();
            query = "INSERT INTO conglomerado_metrica (idconglomerado, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idconglomerado+", "+idmetrica+", "+aprobacion+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimiento Positivo
            metrica = metricaEJB.findByName("sentimientoPositivo");
            idmetrica = metrica.getId();
            query = "INSERT INTO conglomerado_metrica (idconglomerado, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idconglomerado+", "+idmetrica+", "+(float)positiveCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimiento Negativo
            metrica = metricaEJB.findByName("sentimientoNegativo");
            idmetrica = metrica.getId();
            query = "INSERT INTO conglomerado_metrica (idconglomerado, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idconglomerado+", "+idmetrica+", "+(float)negativeCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Sentimient Neutro
            metrica = metricaEJB.findByName("sentimientoNeutro");
            idmetrica = metrica.getId();
            query = "INSERT INTO conglomerado_metrica (idconglomerado, idmetrica, valor, lugar, fecha) "
                    + "VALUES ("+idconglomerado+", "+idmetrica+", "+(float)neutralCount+", '"+lugar+"', '"+this.formattedNow+"')";
            mysql.execQuery(query);
            
            //Antes de calculaba: 
            //      sentimiento(Positivo|Negativo|Neutral) = (positive|negative|neutral)Count/(float)hits;
        }

    }

}
