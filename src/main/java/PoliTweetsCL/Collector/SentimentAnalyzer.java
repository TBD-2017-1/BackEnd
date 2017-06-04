package PoliTweetsCL.Collector;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class SentimentAnalyzer {
    private Set<String> positiveWords;
    private Set<String> negativeWords;


    public SentimentAnalyzer(){
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
            // tokenizar
            String[] tokens = text.trim().split("[\\s\\p{Punct}¿¡]+");

            for (String word: tokens) {
                word = word.toLowerCase();

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