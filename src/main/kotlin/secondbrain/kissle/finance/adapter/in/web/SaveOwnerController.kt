package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import secondbrain.kissle.finance.application.SaveOwnerService
import secondbrain.kissle.finance.application.domain.Owner

@Path("/owner")
class SaveOwnerController(
    @Inject
    private var saveOwnerService: SaveOwnerService
) {

    @POST
    fun save(owner: String): Owner {
        return saveOwnerService.save(owner)
    }
}