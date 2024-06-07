package secondbrain.kissle.finance.application.port.out.peristence

import secondbrain.kissle.finance.application.domain.PeriodicTransaction

interface LoadPeriodicTransactionPort {
    fun load(id: Long): PeriodicTransaction
}