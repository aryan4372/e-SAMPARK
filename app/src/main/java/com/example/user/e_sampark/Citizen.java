package com.example.user.e_sampark;

/**
 * Created by USER on 14-03-2018.
 */

public class Citizen {

    private String message;
    private String from;

    public Citizen()
    {}

    public Citizen(String message,String from)
    {
        this.message=message;
        this.from=from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
