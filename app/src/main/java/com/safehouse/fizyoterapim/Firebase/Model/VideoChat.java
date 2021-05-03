package com.safehouse.fizyoterapim.Firebase.Model;

import java.io.Serializable;

public class VideoChat implements Serializable {

    private String link;
    private boolean openLink;

    public VideoChat() {
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isOpenLink() {
        return openLink;
    }

    public void setOpenLink(boolean openLink) {
        this.openLink = openLink;
    }
}
