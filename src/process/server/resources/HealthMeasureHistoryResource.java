package process.server.resources;

import process.server.endpoint.BusinessLogicImplementation;
import process.server.endpoint.ProcessImplementation;
import process.server.model.HealthMeasureHistory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by bishruti on 2/5/16.
 */
public class HealthMeasureHistoryResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int uId;
    String measuretype;

    public HealthMeasureHistoryResource(UriInfo uriInfo, Request request, int uId, String measuretype) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.uId = uId;
        this.measuretype = measuretype;
    }

    /* Request to obtain all measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
                       measureType (String)
       Expected Output: List of details of measure types. (List) */

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserHistory() throws Exception {
        List<HealthMeasureHistory> userHistoryList = BusinessLogicImplementation.getUserHistory(uId, measuretype);
        if (userHistoryList != null) {
            System.out.println("Generating list of Health Measure History...");
            return Response.ok(userHistoryList).build();
        }
        else {
            return Response.status(404).build();
        }
    }

     /*  Request to obtain measure details about a particular measure of a user in the list.
        Expected Input: uId (Integer)
                        measureType (String)
                        hmhId (Integer)
        Expected Output: Details of a particular measure. (List) */

    @Path("/{hmhId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserMeasure(@PathParam("hmhId") int hmhId) throws Exception {
        List<HealthMeasureHistory> userHistory = BusinessLogicImplementation.getUserMeasure(uId, measuretype, hmhId);
        if (userHistory != null) {
            System.out.println("Getting details of Health Measure History");
            return Response.ok(userHistory).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    /* Request to create measure details about a measure of a user in the list.
       Expected Input: uId (Integer)
       measureType (String)
       MeasureDetails (Object)
       Expected Output:
       List of newly created measure. (List) */

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createMeasure(HealthMeasureHistory healthMeasureHistory) throws Exception {
        System.out.println("Creating new healthMeasureHistory...");
        List<HealthMeasureHistory> userHistory = ProcessImplementation.createUserMeasure(uId, measuretype, healthMeasureHistory);
        if (userHistory != null) {
            System.out.println("Getting details of Health Measure History");
            return Response.ok(userHistory).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    /*  Request to update measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        measureType (String)
        hmhId (Integer)
        MeasureDetails (Object)
        Expected Output:
        List of updated measure. (List) */

    @Path("/{hmhId}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateMeasure(HealthMeasureHistory healthMeasureHistory, @PathParam("hmhId") int hmhId) throws Exception {
        System.out.println("Updating healthMeasureHistory...");
        List<HealthMeasureHistory> userHistory = ProcessImplementation.updateUserMeasure(uId, measuretype, healthMeasureHistory, hmhId);
        if (userHistory != null) {
            System.out.println("Getting details of Health Measure History");
            return Response.ok(userHistory).build();
        }
        else {
            return Response.status(404).build();
        }
    }

    /*  Request to delete measure details about a measure of a user in the list.
        Expected Input: uId (Integer)
        hmhId (Integer)
        Expected Output: Response Message. */

    @Path("/{hmhId}")
    @DELETE
    public void deleteMeasure(@PathParam("hmhId") int hmhId) throws Exception {
        System.out.println("Deleting healthMeasureHistory...");
        ProcessImplementation.deleteUserMeasure(uId, measuretype, hmhId);
    }

}
