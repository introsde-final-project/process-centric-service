package process.server.endpoint;

import org.glassfish.jersey.client.ClientConfig;
import process.server.model.HealthMeasureHistory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

/**
 * Created by bishruti on 2/6/16.
 */
public class BusinessLogicImplementation {
    BusinessLogicImplementation logicImpl = new BusinessLogicImplementation();
    private static ClientConfig clientConfig = new ClientConfig();
    private static Client client = ClientBuilder.newClient(clientConfig);
    private static WebTarget service = client.target(getBaseURI());
    private static Response response;

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://127.0.1.1:8004/storage/user").build();
    }

    /* Request to obtain all measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
                       measureType (String)
       Expected Output: List of details of measure types. (String) */

    public static List<HealthMeasureHistory> getUserHistory(int uId, String measureType) throws Exception {
        response = service.path(String.valueOf(uId) + "/"  + measureType).request().accept(MediaType.APPLICATION_JSON).get();
        List<HealthMeasureHistory> healthMeasureHistory = response.readEntity(List.class);
        return healthMeasureHistory;
    }

     /*  Request to obtain measure details about a particular measure of a user in the list.
        Expected Input: uId (Integer)
                        measureType (String)
                        hmhId (Integer)
        Expected Output: Details of a particular measure. (String) */

    public static List<HealthMeasureHistory> getUserMeasure(int uId, String measureType, int hmhId) throws Exception {
        response = service.path(String.valueOf(uId) + "/"  + measureType + "/" + String.valueOf(hmhId)).request().accept(MediaType.APPLICATION_JSON).get();
        List<HealthMeasureHistory> healthMeasureHistory = response.readEntity(List.class);
        return healthMeasureHistory;
    }
}
