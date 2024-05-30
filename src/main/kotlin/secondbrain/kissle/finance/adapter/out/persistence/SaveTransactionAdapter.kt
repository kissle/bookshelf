package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.AccountEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.OwnerEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.TransactionEntity
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.out.LoadAccountPort
import secondbrain.kissle.finance.application.port.out.SaveTransactionPort

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
        val transactionEntity = TransactionEntity.create(transaction.id, sourceAccount, targetAccount, transaction.amount, transaction.dateTime, transaction.purpose)
        session.save(transactionEntity)

        val retrievedTransactionEntity = session.load(TransactionEntity::class.java, transactionEntity.id)
            ?: throw Exception("Transaction could not be retrieved")
        val retrievedSourceAccount = getAccountFromEntity(retrievedTransactionEntity.sourceAccount)
        val retrievedTargetAccount = getAccountFromEntity(retrievedTransactionEntity.targetAccount)
        return Transaction(retrievedTransactionEntity.id, retrievedSourceAccount, retrievedTargetAccount, retrievedTransactionEntity.amount, retrievedTransactionEntity.dateTime, retrievedTransactionEntity.purpose)
    }

    private fun getAccountEntity(account: Account): AccountEntity {
        return loadAccountPort.load(account.iban).let { a -> AccountEntity.create(a.iban, a.owner.map { owner -> getOwnerEntity(owner) }) }
    }

    private fun getOwnerEntity(owner: Owner): OwnerEntity {
        return OwnerEntity.create(owner.id, owner.name)
    }

    private fun getAccountFromEntity(accountEntity: AccountEntity): Account {
        return Account(accountEntity.iban, accountEntity.owners.map { ownerEntity -> Owner(ownerEntity.id, ownerEntity.name) })
    }
}