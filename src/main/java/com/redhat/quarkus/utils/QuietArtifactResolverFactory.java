package com.redhat.quarkus.utils;

import io.quarkus.bootstrap.resolver.maven.BootstrapMavenException;
import io.quarkus.bootstrap.resolver.maven.MavenArtifactResolver;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.transfer.AbstractTransferListener;

@ApplicationScoped
public class QuietArtifactResolverFactory {

    @Inject
    MavenArtifactResolver artifactResolver;

    @Produces
    @QuietResolver
    public MavenArtifactResolver produce()  {
        Log.info("Using the Quiet MavenArtifactResolver");
        try {
            var session = new DefaultRepositorySystemSession(artifactResolver.getSession());
            session.setTransferListener(new AbstractTransferListener(){});
            return MavenArtifactResolver.builder()
                    .setRepositorySystem(artifactResolver.getSystem())
                    .setRepositorySystemSession(session)
                    .setRemoteRepositoryManager(artifactResolver.getRemoteRepositoryManager())
                    .setRemoteRepositories(artifactResolver.getRepositories())
                    .setWorkspaceDiscovery(false)
                    .build();
        } catch (BootstrapMavenException e) {
            Log.error("Failed to Bootstrap Maven, producing default Maven Artifact Resolver instead");
            Log.debug(e);
            return artifactResolver;
        }
    }
}
