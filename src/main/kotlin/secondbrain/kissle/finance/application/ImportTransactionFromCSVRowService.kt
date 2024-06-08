package secondbrain.kissle.finance.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.ImportTransactionFromCSVRowUseCase
import secondbrain.kissle.finance.application.port.`in`.web.request.TransactionCSVRowRequest
import secondbrain.kissle.finance.application.port.out.peristence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ApplicationScoped
class ImportTransactionFromCSVRowService(
    @Inject private var loadOwnerPort: LoadOwnerPort,
    @Inject private var loadAccountPort: LoadAccountPort,
    @Inject private var saveOwnerPort: SaveOwnerPort,
    @Inject private var saveAccountPort: SaveAccountPort,
    @Inject private var saveTransactionPort: SaveTransactionPort
): ImportTransactionFromCSVRowUseCase {

    override fun import(transactionRequest: TransactionCSVRowRequest, requesterIBAN: String) {
        val transactionAccount = getOrCreateAccount(transactionRequest.iban, transactionRequest.owner)
        val requesterAccount = getAccount(requesterIBAN)
        val date: LocalDate = LocalDate.parse(transactionRequest.date, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        if (transactionRequest.amount >= 0) {
            val transaction = Transaction(
                null,
                transactionAccount,
                requesterAccount,
                transformAmount(transactionRequest.amount),
                date,
                transactionRequest.purpose
            )
            saveTransaction(transaction)
        } else {
            val transaction = Transaction(
                null,
                requesterAccount,
                transactionAccount,
                transformAmount(transactionRequest.amount),
                date,
                transactionRequest.purpose
            )
            saveTransaction(transaction)
        }
    }

    private fun getAccount(requesterIBAN: String): Account {
        return loadAccountPort.load(requesterIBAN)
    }

    private fun getOrCreateAccount(iban: String, ownerName: String): Account {
        try {
            return getAccount(iban)
        } catch (e: Exception) {
            val owner = getOrCreateOwner(ownerName)
            val account = Account(iban, listOf(owner))
            return saveAccountPort.save(account)
        }
    }

    private fun getOrCreateOwner(owner: String): Owner {
        return try {
            loadOwnerPort.findByName(owner)
        } catch (e: Exception) {
            saveOwnerPort.save(Owner.create(null, owner))
        }
    }

    private fun transformAmount(amount: Double): Long {
        return (amount * 100).toLong()
    }

    private fun saveTransaction(transaction: Transaction) {
        saveTransactionPort.save(transaction)
    }
}