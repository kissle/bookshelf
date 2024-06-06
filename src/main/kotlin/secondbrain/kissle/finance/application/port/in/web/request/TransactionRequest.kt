package secondbrain.kissle.finance.application.port.`in`.web.request

import java.time.LocalDateTime

data class TransactionRequest(
    val sourceAccount: String,
    val targetAccount: String,
    val amount: Long,
    val purpose: String,
    val dateTime: LocalDateTime?
)