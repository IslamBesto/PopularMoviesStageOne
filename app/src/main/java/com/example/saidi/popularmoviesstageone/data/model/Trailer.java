package com.example.saidi.popularmoviesstageone.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by saidi on 12/03/2018.
 */

public class Trailer implements Serializable {

    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("type")
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String buildThumbnails() {
        StringBuilder thumbnailsLink = new StringBuilder()
                .append("https://img.youtube.com/vi/")
                .append(getKey()+"/")
                .append("0.jpg");
        return thumbnailsLink.toString();
    }

    public String getVideoLink() {
        StringBuilder trailerVideoLink = new StringBuilder()
                .append("https://www.youtube.com/watch?v=")
                .append(getKey());
        return trailerVideoLink.toString();
    }
}
