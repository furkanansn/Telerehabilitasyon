package com.safehouse.fizyoterapim.Firebase.Model;

import java.io.Serializable;

public class Meet implements Serializable {


    private String date;
    private String description;

    public Meet() {
    }





    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
