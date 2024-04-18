package com.redhat.quarkus.clients;

import com.redhat.quarkus.models.PlatformList;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/platforms")
@RegisterRestClient(configKey="product-registry-client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface PlatformVersionRestClient {

    @Path("/all")
    @GET
    PlatformList getAllPlatforms();


    @GET
    PlatformList getLatestPlatforms();
}
