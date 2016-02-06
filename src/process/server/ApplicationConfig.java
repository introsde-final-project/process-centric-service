package process.server;

import process.ExceptionListener;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by bishruti on 2/5/16.
 */

@ApplicationPath("process")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig () {
        packages("process");
        register(ExceptionListener.class);
    }
}