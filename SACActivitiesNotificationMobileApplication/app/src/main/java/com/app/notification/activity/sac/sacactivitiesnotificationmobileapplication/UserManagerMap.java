package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

/**
 * Created by Keji's Lab on 19/02/2018.
 */

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keji's Lab on 26/11/2017.
 */

public class UserManagerMap {
    public String email;
    public String studentNumber;
    public String username;

    public UserManagerMap(){

    }
    public UserManagerMap(String email, String studentNumber,String username){
        this.studentNumber = studentNumber;
        this.email = email;
        this.username = username;
    }
    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("email", email);
        result.put("studentNumber", studentNumber);
        result.put("username",username);

        return result;
    }
}
