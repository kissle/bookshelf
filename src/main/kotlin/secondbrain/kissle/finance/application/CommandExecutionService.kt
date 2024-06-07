package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.PeriodicTransaction
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.CreatePeriodicTransactionUseCase
import secondbrain.kissle.finance.application.port.`in`.web.request.CreatePeriodicTransactionFromCompletedTransactionsCommand
import secondbrain.kissle.finance.application.port.out.peristence.LoadTransactionPort
import secondbrain.kissle.finance.application.port.out.peristence.SavePeriodicTransactionPort
import java.time.Period

@ApplicationScoped
class CommandExecutionService(
    @Inject private var loadTransactionPort: LoadTransactionPort,
    @Inject private var savePeriodicTransactionPort: SavePeriodicTransactionPort
): CreatePeriodicTransactionUseCase {

    override fun execute(iban: String, command: CreatePeriodicTransactionFromCompletedTransactionsCommand) {
        fromCompletedTransactions(command)
        command.result?.let { savePeriodicTransactionPort.save(it) }
    }

    fun fromCompletedTransactions(command: CreatePeriodicTransactionFromCompletedTransactionsCommand) {
        val transactions = loadTransactionPort.load(command.transactionIds)
        if (transactions.isEmpty()) {
            val first = loadTransactionPort.load(command.transactionIds.first())
            println(first.purpose)
        }
        val periodicTransaction = PeriodicTransaction(null)
        periodicTransaction.addCompletedTransactions(transactions)
        periodicTransaction.updatePeriod(getPeriodFromTransactions(transactions))
        periodicTransaction.updateCurrentAmount(transactions.last().amount)
        command.result = periodicTransaction
    }

    private fun getPeriodFromTransactions(transactions: List<Transaction>): Period {
        val sortedTransactions = transactions.sortedBy { it.date }
        val totalTransactions = sortedTransactions.size
        val transaction1 = sortedTransactions[totalTransactions - 2]
        val transaction2 = sortedTransactions[totalTransactions - 1]
        return Period.between(transaction1.date, transaction2.date)
    }
}