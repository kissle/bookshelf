package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.domain.Transaction
import java.time.LocalDate

interface LoadFutureTransactionsOfPeriodicTransactionUseCase {
    fun get(id: Long, untilDate: LocalDate): List<Transaction>
}