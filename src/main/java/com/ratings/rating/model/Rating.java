package com.ratings.rating.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rating {
    public Boolean success = false;
    public String platform;
    public String userId;
    public int rating;
    public int totalprb = 0;
    public int globrank;
    public String picurl;
    public String name;
    public String strsty;
    public String cfleag;
    public String stars = "";
    public Map<String,Integer> prbl = new HashMap<String,Integer>();
    public ArrayList<ArrayList<String>> tbl = new ArrayList<ArrayList<String>>();
    public ArrayList<ArrayList<String>> uns = new ArrayList<ArrayList<String>>();
    public Map<String,ArrayList<String>> wea = new HashMap<String,ArrayList<String>>();
    public Map<String,ArrayList<String>> unss = new HashMap<String,ArrayList<String>>();

    

    public Rating() {
    }

    public Rating(String platform, String userId, int rating) {
        this.platform = platform;
        this.userId = userId;
        this.rating = rating;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
