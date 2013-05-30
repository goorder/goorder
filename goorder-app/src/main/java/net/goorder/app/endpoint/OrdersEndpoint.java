package net.goorder.app.endpoint;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import net.goorder.app.presentation.OrderingGroupRepo;
import net.goorder.app.presentation.OrderingGroupView;

/**
 *
 * @author witoldsz
 */
@Path("orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class OrdersEndpoint {

    @Inject
    private OrderingGroupRepo orderingGroupRepo;

    @Path("/group/{groupId}")
    @GET
    public OrderingGroupView group(@PathParam("groupId") String groupId) {
        return orderingGroupRepo.orderingGroup(groupId);
    }

}
