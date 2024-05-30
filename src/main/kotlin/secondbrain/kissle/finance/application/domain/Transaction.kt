package secondbrain.kissle.finance.application.domain

import java.time.LocalDateTime

class Transaction(
    val id: Long?,
    val sourceAccount: Account,
    val targetAccount: Account,
    val amount: Long,
    val dateTime: LocalDateTime,
    val purpose: String
)