package com.app.notification.activity.sac.sacactivitynotificationstudent;

/**
 * Created by Keji's Lab on 08/02/2018.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class UserProfileLogin {

    public String studentNumber;
    public String email;

    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    public UserProfileLogin() {

    }

    public UserProfileLogin(String studentNumber, String email) {
        this.studentNumber = studentNumber;
        this.email = email;

    }
    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email",email);
        result.put("studentNumber",studentNumber);


        return result;
    }
    // [END post_to_map]

}