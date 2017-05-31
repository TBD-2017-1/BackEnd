package PoliTweetsCL.Core.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import twitter4j.UserMentionEntity;

public class User {
    // Twitter
    private long _id;
    private String name;
    private String screenName;
    private int followersCount;
    private int friendsCount;
    private String location;

    public User(twitter4j.User u){
        _id = u.getId();
        name = u.getName();
        screenName = u.getScreenName();
        followersCount = u.getFollowersCount();
        friendsCount = u.getFriendsCount();
        location = u.getLocation();
    }

    public User(UserMentionEntity m){
        _id = m.getId();
        name = m.getName();
        screenName = m.getScreenName();
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

    public static User fromJSON(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }

    // GETTERS
    public long get_id() {return _id;}
    public String getName() {return name;}
    public String getScreenName() {return screenName;}
    public int getFollowersCount() {return followersCount;}
    public int getFriendsCount() {return friendsCount;}
    public String getLocation() {return location;}

}
