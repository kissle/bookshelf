package secondbrain.kissle.finance.application.port.out.peristence

import secondbrain.kissle.finance.application.domain.PeriodicTransaction

interface SavePeriodicTransactionPort {
    fun save(periodicTransaction: PeriodicTransaction): PeriodicTransaction
}
