package com.example.user.e_sampark;

/**
 * Created by USER on 26-02-2018.
 */

public class Messages {

    private String message,from;
    //private long time;

    public Messages(String message, String from) {
        this.message = message;
        //this.seen = seen;
        //this.type = type;
        //this.time = time;
        this.from=from;
    }

    public Messages() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*
    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    */
}
