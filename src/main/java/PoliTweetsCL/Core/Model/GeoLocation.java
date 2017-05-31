package PoliTweetsCL.Core.Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class GeoLocation {
    private String location;
    private double latitude;
    private double longitude;

    public GeoLocation(){
        location = null;
        latitude = -1;
        longitude = -1;
    }

    public GeoLocation(twitter4j.GeoLocation geo){
        latitude = geo.getLatitude();
        longitude = geo.getLongitude();
    }

    public GeoLocation(twitter4j.GeoLocation geo, String userLocation){
        if(geo != null){
            latitude = geo.getLatitude();
            longitude = geo.getLongitude();
        }
        location = userLocation;
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

    public static GeoLocation fromJSON(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, GeoLocation.class);
    }

    // GETTER
    public String getLocation() {return location;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}

    //SETTER
    public void setLocation(String location) {this.location = location;}
}
