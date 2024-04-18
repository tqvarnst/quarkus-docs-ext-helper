package com.redhat.quarkus.models;

import java.util.Objects;

@SuppressWarnings("unused")
public class Extension implements Comparable<Extension> {

    String name;
    String description;
    String artifact;

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

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

    public String getArtifactName() {
        String[] gav = this.artifact.split(":");
        if(gav.length>=2)
            return gav[1];
        else
            return artifact;
    }

    @Override
    public String toString() {
        return "Extension{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Extension that) {
        return this.getArtifactName().compareTo(that.getArtifactName());
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Extension thatExtension = (Extension) that;

        return this.getArtifactName().equals(this.getArtifactName());


    }

    @Override
    public int hashCode() {
        return Objects.hash(artifact);
    }
}
