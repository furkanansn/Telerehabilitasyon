package com.safehouse.fizyoterapim.Firebase.Model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Exercise implements  Serializable {
    private String name;
    private String description;
    private String image1;
    private String image2;
    private String image3;
    private String video;



    public Exercise() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }



}
