package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.PeriodicTransaction

interface LoadPeriodicTransactionPort {
    fun load(id: Long): PeriodicTransaction
}