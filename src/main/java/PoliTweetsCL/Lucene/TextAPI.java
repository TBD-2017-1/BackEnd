package PoliTweetsCL.Lucene;

import PoliTweetsCL.Core.Model.Tweet;
import PoliTweetsCL.Core.Resources.Config;
import facade.ConglomeradoFacade;
import facade.PartidoFacade;
import facade.PoliticoFacade;
import model.Conglomerado;
import model.Keyword;
import model.Partido;
import model.Politico;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class TextAPI {
    @EJB private Config config;
    @EJB private ConglomeradoFacade conglomeradosEJB;
    @EJB private PartidoFacade partidosEJB;
    @EJB private PoliticoFacade politicosEJB;

    private Logger logger = Logger.getLogger(getClass().getName());

    private Directory dirTweets;
    private Directory dirMenciones;
    private boolean isRamDir;

    private int hitCount = 0;
    private int positiveCount = 0;
    private int negativeCount = 0;
    private int neutralCount = 0;
    private float positiveValue = 0;
    private float negativeValue = 0;


    public int getHitCount() {return hitCount;}
    public int getNegativeCount() {return negativeCount;}
    public int getNeutralCount() {return neutralCount;}
    public int getPositiveCount() {return positiveCount;}

    public float getNegativeValue() {return negativeValue;}
    public float getPositiveValue() {return positiveValue;}


    @PostConstruct
    private void init(){
        isRamDir = config.get("lucene.useRamDirectory").equals("true");
    }

    public void nuevoIndiceTweets(){
        try {
            if(dirTweets !=null) {
                dirTweets.close();
                dirTweets = null;
            }
            if(isRamDir){
                dirTweets = new RAMDirectory();
            }else{
                dirTweets = FSDirectory.open( new File("indiceTweets/"));// directorio donde se guarda el indice
            }
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter w = new IndexWriter(dirTweets, config);
            w.close();

            logger.info("Se ha creado un nuevo indice de tweets");
        }catch (Exception ex){
            ex.printStackTrace();
            logger.info("NO se ha creado un nuevo indice");
        }

    }

    public void nuevoIndiceMenciones(){
        try {
            if(dirMenciones !=null) {
                dirMenciones.close();
                dirMenciones = null;
            }
            if(isRamDir){
                dirMenciones = new RAMDirectory();
            }else{
                dirMenciones = FSDirectory.open( new File("indiceMenciones/"));// directorio donde se guarda el indice
            }
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter w = new IndexWriter(dirMenciones, config);


            // Agregar Conglomerados
            List<Conglomerado> conglomerados = conglomeradosEJB.findAll();
            for (Conglomerado conglomerado:conglomerados) {
                Document doc = new Document();

                // obtener keywords
                List<Keyword> keywords = conglomerado.getKeywords();
                String texto = conglomerado.getNombre()+" @"+conglomerado.getCuentaTwitter();
                for (Keyword keyword: keywords) {
                    texto += " "+keyword.getValue();
                }

                // agregar datos al documento
                doc.add(new TextField("texto", texto, Field.Store.NO));
                doc.add(new StoredField("nombre", conglomerado.getNombre()));

                if(conglomerado.getCuentaTwitter()!=null)
                    doc.add(new StoredField("cuenta", "@"+conglomerado.getCuentaTwitter()));

                doc.add(new StoredField("tipo", "Conglomerado"));

                // Agregar documento al indice
                w.addDocument(doc);
            }

            // Agregar Partidos
            List<Partido> partidos = partidosEJB.findAll();
            for (Partido partido:partidos) {
                Document doc = new Document();

                // obtener keywords
                List<Keyword> keywords = partido.getKeywords();
                String texto = partido.getNombre()+" @"+partido.getCuentaTwitter();
                for (Keyword keyword: keywords) {
                    texto += " "+keyword.getValue();
                }

                // agregar datos al documento
                doc.add(new TextField("texto", texto, Field.Store.NO));
                doc.add(new StoredField("nombre", partido.getNombre()));
                if(partido.getCuentaTwitter()!=null)
                    doc.add(new StoredField("cuenta", "@"+partido.getCuentaTwitter()));
                doc.add(new StoredField("tipo", "Partido"));

                // Agregar documento al indice
                w.addDocument(doc);
            }

            // Agregar Politicos
            List<Politico> politicos = politicosEJB.findAll();
            for (Politico politico:politicos) {
                Document doc = new Document();

                // obtener keywords
                List<Keyword> keywords = politico.getKeywords();
                String texto = politico.getApellido()+" @"+politico.getCuentaTwitter();
                for (Keyword keyword: keywords) {
                    texto += " "+keyword.getValue();
                }

                // agregar datos al documento
                doc.add(new TextField("texto", texto, Field.Store.NO));
                doc.add(new StoredField("nombre", politico.getNombre()));
                if(politico.getCuentaTwitter()!=null)
                    doc.add(new StoredField("cuenta", "@"+politico.getCuentaTwitter()));
                doc.add(new StoredField("tipo", "Politico"));

                // Agregar documento al indice
                w.addDocument(doc);
            }

            w.close();

            logger.info("Se ha creado un nuevo indice de menciones");
        }catch (Exception ex){
            ex.printStackTrace();
            logger.info("NO se ha creado un nuevo indice");
        }

    }


    public int addTweets(Tweet[] tweets){// metodo que crea el indice con todos los archivos dentro del path

        try {
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter w = new IndexWriter(dirTweets, config);

            // por cada tweet
            for (Tweet tweet : tweets) {
                Document doc = new Document();

                // obtener texto
                String texto = tweet.getText(); // mensaje original
                if(tweet.getRetweetedStatus()!=null){
                    texto += " "+tweet.getRetweetedStatus().getText(); // agregar RT
                }

                // agregar datos al documento
                doc.add(new TextField("texto", texto, Field.Store.NO));
                doc.add(new StoredField("sentimiento", tweet.getSentimiento()));

                // Agregar documento al indice
                w.addDocument(doc);
            }

            // cerrar indice
            int docsIndexados = w.numDocs();
            w.close();

            return docsIndexados;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }


    public int buscarKeywords(String keywords[]){// metodo para busqueda dada alguna palabra
        // reiniciar la cache de consulta
        this.hitCount = 0;
        this.positiveCount = 0;
        this.negativeCount = 0;
        this.neutralCount = 0;
        this.positiveValue = 0;
        this.negativeValue = 0;

        try{
            // Preparar indice
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexReader reader = DirectoryReader.open(dirTweets);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser(Version.LUCENE_43,"texto", analyzer);

            // construir consulta
            BooleanQuery bq =  new BooleanQuery();
            for (String keyword : keywords) {
                Query query = parser.parse(QueryParser.escape(keyword));//la palabra que se quiere buscar
                bq.add(query, BooleanClause.Occur.SHOULD);
            }
            bq.setMinimumNumberShouldMatch(1);

            // buscar TODAS las coincidencias
            TotalHitCountCollector collector = new TotalHitCountCollector();
            searcher.search(bq,collector);
            this.hitCount = collector.getTotalHits();
            TopDocs results = searcher.search(bq,Math.max(1, this.hitCount));

            // Guardar los hits
            ScoreDoc[] hits = results.scoreDocs;

            // actualizar cache de consulta
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);
                float sentiment = Float.valueOf(doc.get("sentimiento"));
                if (sentiment == 0){
                    neutralCount++;
                }else if (sentiment > 0){
                    positiveCount++;
                    positiveValue += sentiment;
                }else if (sentiment < 0){
                    negativeCount++;
                    negativeValue += sentiment;
                }
            }

            // cerrar indice
            reader.close();

            // retornar cantidad de hits
            return collector.getTotalHits();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return 0;
    }

    public List<String[]> getMenciones(Tweet tweet){
        try{
            // Preparar indice
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexReader reader = DirectoryReader.open(dirMenciones);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser(Version.LUCENE_43,"texto", analyzer);

            // construir consulta
            BooleanQuery bq =  new BooleanQuery();
            Query query = parser.parse(QueryParser.escape(tweet.getText()));//la palabra que se quiere buscar
            bq.add(query, BooleanClause.Occur.SHOULD);
            bq.setMinimumNumberShouldMatch(1);

            // buscar 3 mejores coincidencias
            TotalHitCountCollector collector = new TotalHitCountCollector();
            searcher.search(bq,collector);
            this.hitCount = collector.getTotalHits();
            TopDocs results = searcher.search(bq,3);

            // Guardar los hits
            ScoreDoc[] hits = results.scoreDocs;

            // Lista de menciones
            List<String[]> menciones = new ArrayList<>();

            // actualizar cache de consulta
            for (ScoreDoc hit : hits) {
                Document doc = searcher.doc(hit.doc);

                String[] mencion = new String[3];

                mencion[0] = doc.get("tipo");
                mencion[1] = doc.get("nombre");
                mencion[2] = doc.get("cuenta"); // puede ser null

                menciones.add(mencion);
            }

            // cerrar indice
            reader.close();

            // retornar cantidad de hits
            return menciones;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
