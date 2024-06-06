package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.out.LoadAccountPort

@Path("/account")
class LoadAccountController (
    @Inject
    private var loadAccountPort: LoadAccountPort
){

     @GET
     @Path("/{iban}")
     fun loadAccount(@PathParam("iban") iban: String): Account {
         return loadAccountPort.load(iban)
     }
}