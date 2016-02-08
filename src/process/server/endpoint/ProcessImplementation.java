package process.server.endpoint;

import org.glassfish.jersey.client.ClientConfig;
import process.server.model.HealthMeasureHistory;
import process.server.model.User;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

/**
 * Created by bishruti on 2/6/16.
 */
public class ProcessImplementation {
    ProcessImplementation processImpl = new ProcessImplementation();
    private static ClientConfig clientConfig = new ClientConfig();
    private static Client client = ClientBuilder.newClient(clientConfig);
    private static WebTarget serviceStorage = client.target(getStorageServiceURI());
    private static Response responseStorage;

    private static URI getStorageServiceURI() {
        return UriBuilder.fromUri("http://127.0.1.1:8004/storage/user").build();
    }

    /*  Request to add a new user in the list.
        Expected Input: User (Object)
        Expected Output: Newly created User with the details associated to that user. (Object) */

    public static User createUser(User userDetail) throws Exception {
        responseStorage = serviceStorage.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(userDetail));
        User user = responseStorage.readEntity(User.class);
        int uId = user.getuId();
        responseStorage = serviceStorage.path(String.valueOf(uId)).request().accept(MediaType.APPLICATION_JSON).get();
        return user;
    }

     /*  Request to edit a user in the list.
        Expected Input: uId (Integer) and User (Object)
        Expected Output: Edited User with the details associated to that user. (Object) */

    public static User updateUser(int userId, User userDetail) throws Exception {
        responseStorage = serviceStorage.path(String.valueOf(userId)).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(userDetail));
        responseStorage = serviceStorage.path(String.valueOf(userId)).request().accept(MediaType.APPLICATION_JSON).get();
        User user = responseStorage.readEntity(User.class);
        return user;
    }

    /*  Request to delete a user from the list.
        Expected Input: uId (Integer)
        Expected Output: Response Message. */

    public static void deleteUser(int userId) throws Exception {
        responseStorage = serviceStorage.path(String.valueOf(userId)).request().accept(MediaType.APPLICATION_JSON).delete();
    }

    /* Request to create measure details about a measure of a user in the list.
       Expected Input: uId (Integer)
       measureType (String)
       MeasureDetails (Object)
       Expected Output:
       List of newly created measure. (List) */

    public static List<HealthMeasureHistory> createUserMeasure(int uId, String measuretype, HealthMeasureHistory healthMeasureHistoryDetails) throws Exception {
        responseStorage = serviceStorage.path(uId + "/" + measuretype).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(healthMeasureHistoryDetails));
        responseStorage = serviceStorage.path(String.valueOf(uId) + "/"  + measuretype).request().accept(MediaType.APPLICATION_JSON).get();
        List<HealthMeasureHistory> newHealthMeasureHistoryList = responseStorage.readEntity(List.class);
        return newHealthMeasureHistoryList;
    }

    /*  Request to update measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        measureType (String)
        hmhId (Integer)
        MeasureDetails (Object)
        Expected Output:
        List of updated measure. (List) */

    public static List<HealthMeasureHistory> updateUserMeasure(int uId, String measuretype, HealthMeasureHistory healthMeasureHistoryDetails, int hmhId) throws Exception {
        responseStorage = serviceStorage.path(uId + "/" + measuretype + "/" + hmhId).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(healthMeasureHistoryDetails));
        responseStorage = serviceStorage.path(String.valueOf(uId) + "/"  + measuretype + "/" + String.valueOf(hmhId)).request().accept(MediaType.APPLICATION_JSON).get();
        List<HealthMeasureHistory> healthMeasureHistory = responseStorage.readEntity(List.class);
        return healthMeasureHistory;
    }

    /*  Request to delete measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        hmhId (Integer)
        Expected Output: Response Message. */

    public static void deleteUserMeasure(int uId, String measuretype, int hmhId) throws Exception {
        responseStorage = serviceStorage.path(uId + "/" + measuretype + "/" + hmhId).request().accept(MediaType.APPLICATION_JSON).delete();
    }
}
