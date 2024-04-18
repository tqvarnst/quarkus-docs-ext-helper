package com.redhat.quarkus;

import com.redhat.quarkus.models.ExtensionSupport;
import com.redhat.quarkus.services.PlatformService;
import io.quarkus.registry.RegistryResolutionException;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.OutputStream;
import java.io.PrintStream;

import static com.redhat.quarkus.services.PlatformService.*;

@Command(name = "quarkus-docs-ext-helper", mixinStandardHelpOptions = true)
public class MainCommand implements Runnable {

    @Parameters(paramLabel = "<name>", defaultValue = "picocli",
        description = "Your name.")
    String name;

    @CommandLine.Option(names = {"-S","--stream"},required = true)
    String stream;

    @CommandLine.Option(names = {"-e","--extension"},description = "The extensions to check support status of")
    String extension;

    @Inject
    PlatformService platformVersionService;

    static final PrintStream original = System.out;

    @Override
    public void run() {
        //Verify the version specified
        if(!platformVersionService.verifyVersion(stream)) {
            System.out.printf("The stream version specified %s is not a valid version. The valid streams are [%s]\n",stream, String.join(", ", platformVersionService.getStreamIds()));
        }

        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                //DO NOTHING
            }
        }));

        if(extension!=null) {
            String version = null;
            try {
                version = platformVersionService.getLatestVersionForStream(stream);
                ExtensionSupport extensionSupport = platformVersionService.getSupportLevelForExtensionInVersion(extension, version);

                System.setOut(original);
                System.out.println(extensionSupport.getSupportLevel().toString());

            } catch (StreamNotFoundException e) {
                System.err.printf("Failed to find a stream matching %s%n",stream);
                System.exit(99);
            } catch (ReleaseNotFoundException e) {
                System.err.printf("Didn't find a matching release version to the stream %s%n",stream);
                System.exit(98);
            } catch (ExtensionNotFoundException e) {
                System.err.printf("Didn't fine an extension, named %s%n",extension);
                System.exit(97);
            } catch (RegistryResolutionException e) {
                System.err.println("Failed to resolve extension information from registry");
                System.exit(96);
            }

        }
    }

}
