package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.SaveTransactionUseCase
import secondbrain.kissle.finance.application.port.`in`.web.requests.TransactionRequest
import secondbrain.kissle.finance.application.port.out.LoadAccountPort
import secondbrain.kissle.finance.application.port.out.SaveTransactionPort
import java.time.LocalDateTime

@ApplicationScoped
class SaveTransactionService(
    @Inject
    private var saveTransactionPort: SaveTransactionPort,
    @Inject
    private var loadAccountPort: LoadAccountPort
): SaveTransactionUseCase {

    override fun save(transaction: TransactionRequest): Transaction {
        val sourceAccount = getAccount(transaction.sourceAccount)
        val targetAccount = getAccount(transaction.targetAccount)

        return saveTransactionPort.save(Transaction(
            null,
            sourceAccount,
            targetAccount,
            transaction.amount * 100,
            transaction.dateTime ?: LocalDateTime.now(),
            transaction.purpose
        ))
    }

    private fun getAccount(iban: String): Account {
        return loadAccountPort.load(iban)
    }
}