package com.redhat.quarkus.models;

import java.util.List;

public class Stream {
    String id;
    String name;
    List<Release> releases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    @Override
    public String toString() {
        return "Stream{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", releases=" + releases +
                '}';
    }
}
