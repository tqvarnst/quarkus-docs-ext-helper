package com.redhat.quarkus.models;

import java.util.List;

@SuppressWarnings("unused")
public class ExtensionList {

    List<Extension> extensions;

    public List<Extension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }
}
