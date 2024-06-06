package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.finance.application.SaveAccountService
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.`in`.web.CreatePeriodicTransactionUseCase
import secondbrain.kissle.finance.application.port.`in`.web.request.CreatePeriodicTransactionFromCompletedTransactionsCommand

@Path("/account")
class AccountController(
    @Inject
    private var saveAccountService: SaveAccountService,
    @Inject
    private var createPeriodicTransactionUseCase: CreatePeriodicTransactionUseCase
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun save(account: Account): Account {
        return saveAccountService.save(account)
    }

    @POST
    @Path("/{iban}/command")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun executeCommand(@PathParam("iban") iban: String, command: CreatePeriodicTransactionFromCompletedTransactionsCommand) {
        createPeriodicTransactionUseCase.execute(iban, command)
    }
}