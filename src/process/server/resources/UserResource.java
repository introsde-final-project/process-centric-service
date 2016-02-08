package process.server.resources;

import process.server.endpoint.ProcessImplementation;
import process.server.model.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Created by bishruti on 2/3/16.
 */

@Stateless // Used only if the the application is deployed in a Java EE container
@LocalBean // Used only if the the application is deployed in a Java EE container
@Path("/user")
public class UserResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

   /*  Request to add a new user in the list.
        Expected Input: User (Object)
        Expected Output: Newly created User with the details associated to that user. (Object) */

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(User user) throws Exception {
        System.out.println("Creating new user...");
        User newUser = ProcessImplementation.createUser(user);
        if (user != null) {
            System.out.println("Getting the info of the new user..." );
            return Response.ok(newUser).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    /*  Request to edit a user in the list.
        Expected Input: uId (Integer) and User (Object)
        Expected Output: Edited User with the details associated to that user. (Object) */

    @Path("{uId}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("uId") int uId, User user) throws Exception {
        System.out.println("Updating user...");
        User updatedUser = ProcessImplementation.updateUser(uId, user);
        if (updatedUser != null) {
            System.out.println("Getting the info of the new use." );
            return Response.ok(updatedUser).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    /*  Request to delete a user from the list.
        Expected Input: uId (Integer)
        Expected Output: Response Message. */

    @Path("{uId}")
    @DELETE
    public void deleteUser(@PathParam("uId") int uId) throws Exception {
        System.out.println("Deleting user...");
        ProcessImplementation.deleteUser(uId);
    }

    /* Navigates to HealthMeasureHistoryResource if both uId and measuretype is obtained */
    @Path("{uId}/{measuretype}")
    public HealthMeasureHistoryResource getUserHistory(@PathParam("uId") int uId, @PathParam("measuretype") String measuretype) {
        return new HealthMeasureHistoryResource(uriInfo, request, uId, measuretype);
    }
}
