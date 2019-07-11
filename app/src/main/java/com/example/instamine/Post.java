package com.example.instamine;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


@ParseClassName("Post")
public class Post extends ParseObject  {

    public  static final   String KEY_DESCRIPTION="description";
    public  static final   String KEY_IMAGE="image";
    public  static final   String KEY_USER="user";
    public  static final  String  KEY_GEOLOCALIZATION = "geolocalization";
    public  static final  String  KEY_LIKED_BY= "LikedBy";

    public String getDescription() {
        return  getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION,description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE,parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);
    }

    public void setGeolocalization(String geo){put(KEY_GEOLOCALIZATION, geo);}

    public String getGeolocalization(){return getString(KEY_GEOLOCALIZATION);}

    public JSONArray getUsersWhoLikedPost(){
        return getJSONArray(KEY_LIKED_BY);
    }
    public void likePost(ParseUser user){
        add(KEY_LIKED_BY, user);
    }

    public void unlikePost(ParseUser user){
        ArrayList<ParseUser> users = new ArrayList<ParseUser>();
        users.add(user);
        removeAll(KEY_LIKED_BY, users);
    }

    public boolean isLiked(){
        JSONArray a = getUsersWhoLikedPost();
        if(a!= null ) {
            for (int i = 0; i < a.length(); i++) {
                try{
                    if(a.getJSONObject(i).getString("objectId").equals(ParseUser.getCurrentUser().getObjectId())){
                        return true;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
