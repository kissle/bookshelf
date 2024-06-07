package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.TransactionEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.TransactionMapper
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.out.peristence.LoadTransactionPort

@ApplicationScoped
class LoadTransactionAdapter: LoadTransactionPort {
    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun load(id: Long): Transaction {
        val session = sessionFactory.openSession()

        val retrievedTransaction = session.load(TransactionEntity::class.java, id)
            ?: throw Exception("Transaction could not be retrieved")
        return TransactionMapper.toDomain(retrievedTransaction)
    }

    override fun load(ids: List<Long>): List<Transaction> {
        val session = sessionFactory.openSession()

        return session.loadAll(TransactionEntity::class.java, ids).toList().map { TransactionMapper.toDomain(it) }
    }
}