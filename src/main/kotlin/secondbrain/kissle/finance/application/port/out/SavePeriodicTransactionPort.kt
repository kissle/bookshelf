package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.PeriodicTransaction

interface SavePeriodicTransactionPort {
    fun save(periodicTransaction: PeriodicTransaction): PeriodicTransaction
}
