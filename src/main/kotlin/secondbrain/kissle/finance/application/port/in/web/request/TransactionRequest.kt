package secondbrain.kissle.finance.application.port.`in`.web.request

import java.time.LocalDate

data class TransactionRequest(
    val sourceAccount: String,
    val targetAccount: String,
    val amount: Long,
    val purpose: String,
    val date: LocalDate?
)