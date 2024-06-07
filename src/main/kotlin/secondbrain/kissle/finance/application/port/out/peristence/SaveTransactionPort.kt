package secondbrain.kissle.finance.application.port.out.peristence

import secondbrain.kissle.finance.application.domain.Transaction

interface SaveTransactionPort {
    fun save(transaction: Transaction): Transaction
}