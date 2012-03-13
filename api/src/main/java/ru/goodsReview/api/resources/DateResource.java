package ru.goodsReview.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Date;

/**
 * Artemij Chugreev
 * Date: 07.03.12
 * Time: 2:22
 * email: achugr@yandex-team.ru
 * skype: achugr
 */
@Path("/currentDate")
public class DateResource {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media
    // type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage(@PathParam("name") String name) {
        // Return some cliched textual content
        Date date = new Date();
        return "current date is: " + date;
    }
}
