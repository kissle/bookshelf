package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.Transaction

interface LoadTransactionPort {
    fun load(id: Long): Transaction
    fun load(ids: List<Long>): List<Transaction>
}