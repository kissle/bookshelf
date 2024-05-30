package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.finance.application.SaveOwnerService
import secondbrain.kissle.finance.application.domain.Owner

@Path("/owner")
class SaveOwnerController(
    @Inject
    private var saveOwnerService: SaveOwnerService
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun save(owner: Owner): Owner {
        return saveOwnerService.save(owner)
    }
}