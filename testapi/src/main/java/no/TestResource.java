package no;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by dolplads on 04/12/2016.
 */
@Path("tests")
public class TestResource {
    @Produces("text/html")
    @GET
    public String text() {
        return "halla";
    }
}
