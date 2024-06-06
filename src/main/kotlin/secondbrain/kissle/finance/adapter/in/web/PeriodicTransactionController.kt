package secondbrain.kissle.finance.adapter.`in`.web

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import secondbrain.kissle.finance.application.domain.PeriodicTransaction
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.LoadFutureTransactionsOfPeriodicTransactionUseCase
import secondbrain.kissle.finance.application.port.`in`.web.LoadPeriodicTransactionUseCase
import java.time.LocalDate

@Path("/periodic-transaction")
class PeriodicTransactionController(
    @Inject private var loadPeriodicTransactionUseCase: LoadPeriodicTransactionUseCase,
    @Inject private var loadFutureTransactionsOfPeriodicTransactionUseCase: LoadFutureTransactionsOfPeriodicTransactionUseCase
) {

    @GET
    @Path("/{id}")
    fun get(id: Long): PeriodicTransaction {
        return loadPeriodicTransactionUseCase.load(id)
    }

    @GET
    @Path("/{id}/future-transactions/{untilDate}")
    fun getFutureTransactions(@PathParam("id") id: Long, @PathParam("untilDate") untilDate: String): List<Transaction> {
        val date = LocalDate.parse(untilDate)
        return loadFutureTransactionsOfPeriodicTransactionUseCase.get(id, date)
    }
}