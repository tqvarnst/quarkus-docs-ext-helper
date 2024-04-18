package com.redhat.quarkus.utils;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * This is necessary to make the registry client classes to work in native
 */
@RegisterForReflection(targets = {
        com.fasterxml.jackson.annotation.SimpleObjectIdResolver.class,
        io.quarkus.registry.config.RegistriesConfigImpl.class,
        io.quarkus.registry.config.RegistryConfigImpl.Builder.class,
        io.quarkus.registry.config.RegistryDescriptorConfigImpl.Builder.class,
        io.quarkus.registry.config.RegistryPlatformsConfigImpl.Builder.class,
        io.quarkus.registry.config.RegistryNonPlatformExtensionsConfigImpl.Builder.class,
        io.quarkus.registry.config.RegistryMavenConfigImpl.Builder.class,
        io.quarkus.registry.config.RegistryMavenRepoConfigImpl.Builder.class,
        io.quarkus.registry.config.RegistryQuarkusVersionsConfigImpl.Builder.class,
        io.quarkus.registry.catalog.CategoryImpl.Builder.class,
        io.quarkus.registry.catalog.ExtensionImpl.Builder.class,
        io.quarkus.registry.catalog.ExtensionCatalogImpl.class,
        io.quarkus.registry.catalog.ExtensionCatalogImpl.Builder.class,
        io.quarkus.registry.catalog.ExtensionOriginImpl.class,
        io.quarkus.registry.catalog.ExtensionOriginImpl.Builder.class,
        io.quarkus.registry.catalog.PlatformCatalogImpl.Builder.class,
        io.quarkus.registry.catalog.PlatformImpl.Builder.class,
        io.quarkus.registry.catalog.PlatformReleaseImpl.Builder.class,
        io.quarkus.registry.catalog.PlatformReleaseVersion.Deserializer.class,
        io.quarkus.registry.catalog.PlatformStreamImpl.Builder.class,
        io.quarkus.registry.json.JsonArtifactCoordsDeserializer.class
}, ignoreNested = true)
public class Reflections {
}
