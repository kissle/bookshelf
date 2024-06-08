package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.*
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.out.peristence.LoadAccountPort
import secondbrain.kissle.finance.application.port.out.peristence.SaveTransactionPort

@ApplicationScoped
class SaveTransactionAdapter: SaveTransactionPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    @Inject
    private lateinit var loadAccountPort: LoadAccountPort

    override fun save(transaction: Transaction): Transaction {
        val session = sessionFactory.openSession()
        val sourceAccount = getAccountEntity(transaction.sourceAccount)
        val targetAccount = getAccountEntity(transaction.targetAccount)
        val transactionEntity = TransactionEntity.create(transaction.id, sourceAccount, targetAccount, transaction.amount, transaction.date, transaction.purpose)
        session.save(transactionEntity)

        val retrievedTransactionEntity = session.load(TransactionEntity::class.java, transactionEntity.id)
            ?: throw Exception("Transaction could not be retrieved")
        val retrievedSourceAccount = retrievedTransactionEntity.sourceAccount
        val retrievedTargetAccount = retrievedTransactionEntity.targetAccount
        return Transaction(retrievedTransactionEntity.id, retrievedSourceAccount, retrievedTargetAccount, retrievedTransactionEntity.amount, retrievedTransactionEntity.date, retrievedTransactionEntity.purpose)
    }

    private fun getAccountEntity(account: Account): Account {
        return loadAccountPort.load(account.iban)
    }
}