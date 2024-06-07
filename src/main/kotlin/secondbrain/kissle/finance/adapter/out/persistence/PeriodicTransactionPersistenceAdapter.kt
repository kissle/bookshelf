package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.PeriodicTransactionEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.PeriodicTransactionMapper
import secondbrain.kissle.finance.application.domain.PeriodicTransaction
import secondbrain.kissle.finance.application.port.out.peristence.LoadPeriodicTransactionPort

@ApplicationScoped
class PeriodicTransactionPersistenceAdapter: LoadPeriodicTransactionPort {

    @Suppress("CdiInjectionPointsInspection")
    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun load(id: Long): PeriodicTransaction {
        val session = sessionFactory.openSession()

        val entity = session.load(PeriodicTransactionEntity::class.java, id)
        return PeriodicTransactionMapper.toDomain(entity)
    }
}