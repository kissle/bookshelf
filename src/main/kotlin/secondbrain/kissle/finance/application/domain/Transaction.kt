package secondbrain.kissle.finance.application.domain

import java.time.LocalDateTime

class Transaction(
    val sourceAccount: Account,
    val targetAccount: Account,
    val value: Long,
    val dateTime: LocalDateTime,
    val purpose: String
)