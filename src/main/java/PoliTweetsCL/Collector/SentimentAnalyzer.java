package PoliTweetsCL.Collector;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class SentimentAnalyzer {

    private StanfordCoreNLP pipeline;
    private Set<String> positiveWords;
    private Set<String> negativeWords;


    public SentimentAnalyzer(){
        // create coreNLP object
        Properties props = new Properties();
        props.setProperty("annotators","tokenize");
        props.setProperty("tokenize.language","es");
        props.setProperty("pos.model","edu/stanford/nlp/models/pos-tagger/spanish/spanish-distsim.tagger");

        this.pipeline = new StanfordCoreNLP(props);

        // Load sentiment data
        this.positiveWords = new HashSet<>();
        this.negativeWords = new HashSet<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            positiveWords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("positiveWords.dat"), "UTF-8"));
            negativeWords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("negativeWords.dat"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float findSentiment(String text) {
        int positive = 0;
        int negative = 0;
        float tokenCount = 0;

        if (text != null && text.length() > 0) {
            Annotation annotation = this.pipeline.process(text);

            for (CoreLabel token: annotation.get(CoreAnnotations.TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase();

                if (positiveWords.contains(word)){
                    tokenCount++;
                    positive++;
                }else if(negativeWords.contains(word)){
                    tokenCount++;
                    negative++;
                }
            }
        }

        return (tokenCount!=0)?(positive-negative)/tokenCount:0;
    }
}