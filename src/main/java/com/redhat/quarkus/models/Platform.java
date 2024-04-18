package com.redhat.quarkus.models;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class Platform {

    String platformKey;
    String name;
    List<Stream> streams;

    public String getPlatformKey() {
        return platformKey;
    }

    @JsonSetter("platform-key")
    public void setPlatformKey(String platformKey) {
        this.platformKey = platformKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "platformKey='" + platformKey + '\'' +
                ", name='" + name + '\'' +
                ", streams=" + streams +
                '}';
    }
}
