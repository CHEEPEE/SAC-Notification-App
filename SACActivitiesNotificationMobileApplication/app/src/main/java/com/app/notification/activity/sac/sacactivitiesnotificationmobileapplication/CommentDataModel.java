package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

/**
 * Created by Keji's Lab on 31/10/2017.
 */

public class CommentDataModel {
    private String Author;
    private String msg;
    private String timeStamp;

    public String getTimeStamp(){
        return timeStamp;
    }
    public String getAuthor(){
        return Author;
    }
    public String getMsg(){
        return msg;
    }
    public void setAuthor(String author){
        this.Author = author;
    }
    public void setMsg(String message){
        this.msg = message;
    }
    public void setTimeStamp(String timeDate){
        this.timeStamp = timeDate;
    }
}
