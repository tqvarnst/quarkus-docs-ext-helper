package com.redhat.quarkus.models;

import io.quarkus.registry.catalog.Extension;

import java.lang.module.ModuleDescriptor;
import java.util.List;
import java.util.Map;

public class ExtensionSupport implements Comparable<ExtensionSupport> {

    String artifactId;
    String version;
    SupportLevel supportLevel;

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SupportLevel getSupportLevel() {
        return supportLevel;
    }

    public void setSupportLevel(SupportLevel supportLevel) {
        this.supportLevel = supportLevel;
    }


    public ExtensionSupport() {
    }

    public ExtensionSupport(String version, SupportLevel supportLevel) {
        this.version = version;
        this.supportLevel = supportLevel;
    }

    @Override
    public int compareTo(ExtensionSupport that) {
        ModuleDescriptor.Version thisVersion = ModuleDescriptor.Version.parse(this.getVersion());
        ModuleDescriptor.Version thatVersion = ModuleDescriptor.Version.parse(that.getVersion());
        return thisVersion.compareTo(thatVersion);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass())
            return false;
        ExtensionSupport that = (ExtensionSupport) obj;
        return that.version.equals(this.version);
    }

    public static SupportLevel getFromMetaData(String metadata) {
        //supported tech-preview deprecated dev-support supported-in-jvm dev-preview
        return switch (metadata) {
            case "supported" -> SupportLevel.SUPPORTED;
            case "supported-in-jvm" -> SupportLevel.SUPPORTED_IN_JVM;
            case "tech-preview" -> SupportLevel.TECH_PREVIEW;
            case "deprecated" -> SupportLevel.DEPRECATED;
            case "dev-support" -> SupportLevel.DEV_SUPPORTED;
            case "dev-preview" -> SupportLevel.DEV_PREVIEW;
            default -> SupportLevel.UNSUPPORTED;
        };
    }

    public static ExtensionSupport fromCatalogExtension(Extension extension) {
        ExtensionSupport extensionSupport = new ExtensionSupport();
        extensionSupport.setArtifactId(extension.getArtifact().getArtifactId());
        extensionSupport.setVersion(extension.getArtifact().getVersion());
        Map<String, Object> metadata = extension.getMetadata();
        if(metadata.containsKey("redhat-support")) {
            Object metadataObj = metadata.get("redhat-support");
            if(metadataObj instanceof List) {
                String metadataStr = ((List<String>) metadata.get("redhat-support")).getFirst();
                extensionSupport.setSupportLevel(ExtensionSupport.getFromMetaData(metadataStr));
            }
            else {
                extensionSupport.setSupportLevel(SupportLevel.UNSUPPORTED);
            }
        } else {
            extensionSupport.setSupportLevel(SupportLevel.UNSUPPORTED);
        }
        return extensionSupport;
    }
}
