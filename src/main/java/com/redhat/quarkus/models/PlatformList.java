package com.redhat.quarkus.models;


import java.util.List;

public class PlatformList {

    List<Platform> platforms;

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    @Override
    public String toString() {
        return "AllPlatforms{" +
                "platforms=" + platforms +
                '}';
    }
}
