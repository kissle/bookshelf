package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.LoadFutureTransactionsOfPeriodicTransactionUseCase
import secondbrain.kissle.finance.application.port.out.LoadPeriodicTransactionPort
import java.time.LocalDate

@ApplicationScoped
class LoadFutureTransactionsOfPeriodicTransactionService(
    @Inject private var loadPeriodicTransactionPort: LoadPeriodicTransactionPort
): LoadFutureTransactionsOfPeriodicTransactionUseCase {
    override fun get(id: Long, untilDate: LocalDate): List<Transaction> {
        val periodicTransaction = loadPeriodicTransactionPort.load(id)
        return periodicTransaction.getPlannedTransactions(untilDate)
    }
}