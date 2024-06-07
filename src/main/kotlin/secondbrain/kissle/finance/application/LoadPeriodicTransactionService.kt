package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.PeriodicTransaction
import secondbrain.kissle.finance.application.port.`in`.web.LoadPeriodicTransactionUseCase
import secondbrain.kissle.finance.application.port.out.peristence.LoadPeriodicTransactionPort

@ApplicationScoped
class LoadPeriodicTransactionService(
    @Inject private var loadPeriodicTransactionPort: LoadPeriodicTransactionPort
): LoadPeriodicTransactionUseCase {
    override fun load(id: Long): PeriodicTransaction {
        return loadPeriodicTransactionPort.load(id)
    }
}