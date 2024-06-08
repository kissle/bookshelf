package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import secondbrain.kissle.finance.application.port.`in`.web.request.TransactionRequest
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.ImportTransactionFromCSVRowUseCase
import secondbrain.kissle.finance.application.port.`in`.web.SaveTransactionUseCase
import secondbrain.kissle.finance.application.port.`in`.web.request.TransactionCSVRowRequest

@Path("/transaction")
class TransactionController(
    @Inject
    private var saveTransactionUseCase: SaveTransactionUseCase,
    @Inject
    private var importTransactionFromCSVRowUseCase: ImportTransactionFromCSVRowUseCase
) {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun save(transaction: TransactionRequest): Transaction {
        return saveTransactionUseCase.save(transaction)
    }

    @POST
    @Path("/{requesterIBAN}/import")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun import(transaction: TransactionCSVRowRequest, @PathParam("requesterIBAN") requesterIBAN: String) {
        return importTransactionFromCSVRowUseCase.import(transaction, requesterIBAN)
    }
}