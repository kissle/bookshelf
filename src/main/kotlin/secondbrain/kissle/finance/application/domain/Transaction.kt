package secondbrain.kissle.finance.application.domain

import java.time.LocalDate

class Transaction(
    val id: Long?,
    val sourceAccount: Account,
    val targetAccount: Account,
    val amount: Long,
    val date: LocalDate,
    val purpose: String
)