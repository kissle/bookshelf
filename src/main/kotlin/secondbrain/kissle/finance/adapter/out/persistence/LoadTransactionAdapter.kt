package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.out.peristence.LoadTransactionPort

@ApplicationScoped
class LoadTransactionAdapter: LoadTransactionPort {
    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun load(id: Long): Transaction {
        val session = sessionFactory.openSession()

        return session.load(Transaction::class.java, id)
            ?: throw Exception("Transaction could not be retrieved")
    }

    override fun load(ids: List<Long>): List<Transaction> {
        val session = sessionFactory.openSession()

        return session.loadAll(Transaction::class.java, ids).toList()
    }
}