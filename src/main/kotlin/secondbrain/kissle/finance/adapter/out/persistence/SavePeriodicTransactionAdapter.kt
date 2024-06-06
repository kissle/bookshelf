package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.PeriodicTransactionMapper
import secondbrain.kissle.finance.application.domain.PeriodicTransaction
import secondbrain.kissle.finance.application.port.out.SavePeriodicTransactionPort

@ApplicationScoped
class SavePeriodicTransactionAdapter: SavePeriodicTransactionPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun save(periodicTransaction: PeriodicTransaction): PeriodicTransaction {
        val session = sessionFactory.openSession()
        val periodicTransactionEntity = PeriodicTransactionMapper.toEntity(periodicTransaction)
        session.save(periodicTransactionEntity)

        val retrievedPeriodicTransactionEntity =
            session.load(
                PeriodicTransactionMapper.toEntity(periodicTransaction)::class.java,
                periodicTransactionEntity.id)
            ?: throw Exception("Periodic transaction could not be retrieved")
        return PeriodicTransactionMapper.toDomain(retrievedPeriodicTransactionEntity)
    }
}
