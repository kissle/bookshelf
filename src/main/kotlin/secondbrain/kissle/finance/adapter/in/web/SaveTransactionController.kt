package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.finance.application.port.`in`.web.requests.TransactionRequest
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.SaveTransactionUseCase

@Path("/transaction")
class SaveTransactionController(
    @Inject
    private var saveTransactionUseCase: SaveTransactionUseCase
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun save(transaction: TransactionRequest): Transaction {
        return saveTransactionUseCase.save(transaction)
    }
}