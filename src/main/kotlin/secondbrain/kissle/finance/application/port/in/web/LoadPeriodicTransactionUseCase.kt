package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.domain.PeriodicTransaction

interface LoadPeriodicTransactionUseCase {
    fun load(id: Long): PeriodicTransaction
}