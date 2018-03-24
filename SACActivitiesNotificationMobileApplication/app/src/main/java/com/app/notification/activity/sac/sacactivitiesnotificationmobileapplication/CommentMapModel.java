package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class CommentMapModel {

    public String author;
    public String message;
    public String timeStamp;

    public CommentMapModel() {

    }

    public CommentMapModel(String author, String msg, String time_date) {
        this.author= author;
        this.message = msg;
        this.timeStamp  = time_date;

    }
    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("author",author);
        result.put("message", message);
        result.put("timeStamp",timeStamp);




        return result;
    }
}