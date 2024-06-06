package secondbrain.kissle.finance.application.port.`in`.web.request

import com.fasterxml.jackson.annotation.JsonCreator
import secondbrain.kissle.finance.application.domain.PeriodicTransaction

data class CreatePeriodicTransactionFromCompletedTransactionsCommand @JsonCreator constructor(
    val transactionIds: List<Long>
) {
    var result: PeriodicTransaction? = null
}