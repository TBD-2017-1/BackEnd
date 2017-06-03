package PoliTweetsCL.Lucene;

import PoliTweetsCL.Core.BD.MongoDBController;
import PoliTweetsCL.Core.BD.MySQLController;
import PoliTweetsCL.Core.Model.Tweet;
import PoliTweetsCL.Core.Resources.Config;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class TextAPI {
    @EJB
    private Config config;

    Logger logger = Logger.getLogger(getClass().getName());

    private Directory dir;
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
        if(config.get("lucene.useRamDirectory").equals("true")){
            isRamDir = true;
        }else{
            isRamDir = false;
        }
    }

    public void nuevoIndice(){
        try {

            if(dir!=null) {
                dir.close();
                dir = null;
            }
            if(isRamDir){
                dir = new RAMDirectory();
            }else{
                dir = FSDirectory.open( new File("indice/"));// directorio donde se guarda el indice
            }
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter w = new IndexWriter(dir, config);
            w.close();

            logger.info("Se ha creado un nuevo indice");
        }catch (Exception ex){
            ex.printStackTrace();
            logger.info("NO se ha creado un nuevo indice");
            logger.info(ex.getLocalizedMessage());
            logger.info(ex.getMessage());
            logger.info(ex.toString());
        }


    }


    public int addTweets(Tweet[] tweets){// metodo que crea el indice con todos los archivos dentro del path

        try {
            SpanishAnalyzer analyzer = new SpanishAnalyzer(Version.LUCENE_43);
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter w = new IndexWriter(dir, config);

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
            SpanishAnalyzer analyzer = new SpanishAnalyzer();
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser("texto", analyzer);

            // construir consulta
            BooleanQuery bq =  new BooleanQuery();
            for (String keyword : keywords) {
                Query query = parser.parse(keyword);//la palabra que se quiere buscar
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

}
