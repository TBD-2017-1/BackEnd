package PoliTweetsCL.Core.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.bson.Document;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import java.util.Date;

public class Tweet{
    // Twitter Status
    private long _id;
    private Date createdAt;
    private String text;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private Tweet retweetedStatus;
    private Tweet quotedStatus;
    private User[] userMentions;
    private String[] hashtags;
    private User user;
    private GeoLocation geoLocation;

    // Politweets
    private float sentimiento = 0;
    private boolean textIndexed = false;
    private boolean userIndexed = false;
    private boolean geoIndexed = false;

    public Tweet(Status s){
        _id = s.getId();
        createdAt = s.getCreatedAt();
        text = s.getText();
        inReplyToStatusId = s.getInReplyToStatusId();
        inReplyToUserId = s.getInReplyToUserId();
        retweetedStatus = (s.getRetweetedStatus()!=null)?new Tweet(s.getRetweetedStatus()):null;
        quotedStatus = (s.getQuotedStatus()!=null)?new Tweet(s.getQuotedStatus()):null;
        user = new User(s.getUser());
        geoLocation = new GeoLocation(s.getGeoLocation(),user.getLocation());

        UserMentionEntity[] menciones = s.getUserMentionEntities();
        userMentions = null;
        if(menciones.length>0){
            userMentions = new User[menciones.length];
            for (int i=0;i<menciones.length;i++) {
                userMentions[i] = new User(menciones[i]);
            }
        }

        HashtagEntity[] statusHashtags = s.getHashtagEntities();
        hashtags = null;
        if(statusHashtags.length>0){
            hashtags = new String[statusHashtags.length];
            for (int i=0;i<statusHashtags.length;i++) {
                hashtags[i] = statusHashtags[i].getText();
            }
        }

    }

    public static Tweet fromDocument(Document doc){
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(doc), Tweet.class);
    }

    public static Tweet fromJSON(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Tweet.class);
    }


    public String toJSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String toPrettyJSON(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public BasicDBObject toBDObject(){
        return (BasicDBObject) JSON.parse(this.toJSON());
    }

    // GETTERS
    public long getId() { return _id;}
    public String getText() {return text;}
    public Date getCreatedAt() {return createdAt;}
    public User getUser() {return user;}
    public User[] getUserMentions() {return userMentions;}
    public String[] getHashtags() {return hashtags;}
    public long getInReplyToStatusId() { return inReplyToStatusId;}
    public long getInReplyToUserId() {return inReplyToUserId;}
    public Tweet getQuotedStatus() {return quotedStatus;}
    public GeoLocation getGeoLocation() {return geoLocation;}
    public Tweet getRetweetedStatus() {return retweetedStatus;}
    public float getSentimiento() {return sentimiento;}
    public boolean isGeoIndexed() {return geoIndexed;}
    public boolean isTextIndexed() {return textIndexed;}
    public boolean isUserIndexed() {return userIndexed;}

    // SETTERS
    public void setSentimiento(float sentimiento) {this.sentimiento = sentimiento;}
    public void setTextIndexed(boolean textIndexed) {this.textIndexed = textIndexed;}
    public void setUserIndexed(boolean userIndexed) {this.userIndexed = userIndexed;}
    public void setGeoIndexed(boolean geoIndexed) {this.geoIndexed = geoIndexed;}


}
