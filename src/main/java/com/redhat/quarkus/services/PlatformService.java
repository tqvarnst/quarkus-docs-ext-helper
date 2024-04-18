package com.redhat.quarkus.services;

import com.redhat.quarkus.clients.PlatformVersionRestClient;
import com.redhat.quarkus.models.ExtensionSupport;
import com.redhat.quarkus.models.Platform;
import com.redhat.quarkus.models.Release;
import com.redhat.quarkus.models.Stream;
import io.quarkus.bootstrap.resolver.maven.MavenArtifactResolver;
import io.quarkus.logging.Log;
import io.quarkus.maven.ArtifactCoords;
import io.quarkus.registry.ExtensionCatalogResolver;
import io.quarkus.registry.RegistryResolutionException;
import io.quarkus.registry.catalog.Extension;
import io.quarkus.registry.catalog.ExtensionCatalog;
import io.quarkus.registry.config.RegistriesConfig;
import io.quarkus.registry.config.RegistriesConfigLocator;
import io.quarkus.registry.config.RegistryConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlatformService {

    private static final String RH_QUARKUS_REGISTRY = "registry.quarkus.redhat.com";

    @RestClient
    PlatformVersionRestClient restClient;

    @Inject
    MavenArtifactResolver artifactResolver;

    public List<String> getVersions() {
        Platform rhbqPlatform = restClient.getAllPlatforms().getPlatforms().getFirst();
        List<Stream> streams = rhbqPlatform.getStreams();
        List<Release> releases = streams.stream().flatMap(stream -> stream.getReleases().stream()).toList();
        return releases.stream().map(Release::getVersion).toList();

    }

    public String getLatestVersionForStream(String stream) throws StreamNotFoundException, ReleaseNotFoundException {
        return this.getStreams().stream()
//                .peek(s -> System.out.printf("Found stream %s%n",s.getId()))
                .filter(s -> s.getId().equals(stream))
                .findFirst().orElseThrow(StreamNotFoundException::new)
                .getReleases().stream()
                    .findFirst().orElseThrow(ReleaseNotFoundException::new)
                    .getVersion();
    }

    public ExtensionSupport getSupportLevelForExtensionInVersion(String extension, String version) throws ExtensionNotFoundException, RegistryResolutionException {
        final ArtifactCoords platformQuarkusBom = ArtifactCoords.fromString(String.format("com.redhat.quarkus.platform:quarkus-bom::pom:%s",version));
        Log.debugf("Trying to resolve extension catalog for version %s",version);
        ExtensionCatalog catalog = getCatalogResolver().resolveExtensionCatalog(List.of(platformQuarkusBom));
        Extension catalogExtension = catalog.getExtensions().stream()
//                .peek(e -> System.out.println(e.getArtifact().getArtifactId()))
                .filter(e -> e.getArtifact().getArtifactId().equals(extension))
                .findAny().orElseThrow(ExtensionNotFoundException::new);
        System.out.printf("Extensions %s has meta-data %s%n",catalogExtension.getArtifact().getArtifactId(),
                catalogExtension.getMetadata().get("redhat-support"));
        return ExtensionSupport.fromCatalogExtension(catalogExtension);
    }

    private ExtensionCatalogResolver getCatalogResolver() throws RegistryResolutionException {
            return ExtensionCatalogResolver.builder()
                    .artifactResolver(artifactResolver)
                    .config(getRegistriesConfig())
                    .build();
    }

    private RegistriesConfig getRegistriesConfig() {
        RegistriesConfig config = RegistriesConfigLocator.resolveConfig();
        for (RegistryConfig registry : config.getRegistries()) {
            if (registry.getId().equals(RH_QUARKUS_REGISTRY)) {
                if(registry.isEnabled()) {
                    return config;
                }
                final StringBuilder sb = new StringBuilder();
                sb.append(RH_QUARKUS_REGISTRY).append(" was found disabled in ");
                if(config.getSource() != null && config.getSource().getFilePath() != null) {
                    sb.append(config.getSource().getFilePath());
                } else {
                    sb.append("the configuration");
                }
                sb.append(", it will be automatically enabled for this session");
                Log.warn(sb.toString());
                break;
            }
        }
        final List<RegistryConfig> registries = new ArrayList<>(config.getRegistries().size() + 1);
        registries.add(RegistryConfig.builder().setId(RH_QUARKUS_REGISTRY).build());
        registries.addAll(config.getRegistries());
        return config.mutable().setRegistries(registries).build();
    }


    private List<Stream> getStreams() {
        //Currently there is only support for one platform in the registry, from that we get the streams and map it into a List of stream ids
        return restClient.getAllPlatforms().getPlatforms().getFirst().getStreams();
    }

    public List<String> getStreamIds() {
        return getStreams().stream().map(Stream::getId).collect(Collectors.toList());
    }

    public boolean verifyVersion(String version) {
        return getStreamIds().contains(version);
    }

    public final static class StreamNotFoundException extends Exception {
        public StreamNotFoundException() {
            super("Stream was not found");
        }

        public StreamNotFoundException(String stream) {
            super(String.format("Stream %s was not found",stream));
        }
    }

    public final static class ReleaseNotFoundException extends Exception {
        public ReleaseNotFoundException() {
            super("Release not found");
        }

        public ReleaseNotFoundException(String stream) {
            super(String.format("Release not found for stream %s",stream));
        }
    }

    public final static class ExtensionNotFoundException extends Exception {
        public ExtensionNotFoundException() {
            super("Extension not found");
        }
    }


//    private List<String> filterPlatformVersionsByShortNames(List<String> versionList) {
//        Map<String,String> shortVersionMap = new HashMap<>();
//
//        versionList.forEach( v -> {
//            String shortVersion = VersionUtils.shortVersion(v);
//            /**
//             * Because there is an issue where a number of 2.7.6 releases that was SP release was named Final but with
//             * a new build number we need to treat 2.7.6 as a special case.
//             */
//            if("2.7.6.Final".equals(shortVersion)) {
//              switch(v) {
//                  case "2.7.6.Final-redhat-00006" -> shortVersionMap.put("2.7.6.Final",v);
//                  case "2.7.6.Final-redhat-00009" -> shortVersionMap.put("2.7.6.SP1",v);
//                  case "2.7.6.Final-redhat-00011" -> shortVersionMap.put("2.7.6.SP2",v);
//                  case "2.7.6.Final-redhat-00012" -> shortVersionMap.put("2.7.6.SP3",v);
//              }
//            } else if(shortVersionMap.containsKey(shortVersion)) {
//                if(largerThanExistingVersion(v,shortVersionMap.get(shortVersion))) {
//                    shortVersionMap.replace(shortVersion,v);
//                }
//            } else {
//                shortVersionMap.put(shortVersion,v);
//            }
//        });
//
//        return new ArrayList<>(shortVersionMap.values());
//
//    }
//
//    private boolean largerThanExistingVersion(String v, String s) {
//        String shortName = VersionUtils.shortVersion(v);
//        String vBuildNumber = v.replace(shortName+"-redhat-","");
//        String sBuildNumber = s.replace(shortName+"-redhat-","");
//        return Integer.parseInt(vBuildNumber) > Integer.parseInt(sBuildNumber);
//    }
}



