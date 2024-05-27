package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.finance.application.SaveAccountService
import secondbrain.kissle.finance.application.domain.Account

@Path("/account")
class SaveAccountController(
    @Inject
    private var saveAccountService: SaveAccountService
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun save(account: Account): Account {
        return saveAccountService.save(account)
    }
}