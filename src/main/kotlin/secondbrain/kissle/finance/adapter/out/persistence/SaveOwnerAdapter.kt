package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.OwnerEntity
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.out.SaveOwnerPort

@ApplicationScoped
class SaveOwnerAdapter: SaveOwnerPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun save(owner: Owner): Owner {
        val session = sessionFactory.openSession()
        val ownerEntity = OwnerEntity.create(owner.id, owner.name)
        session.save(ownerEntity)

        val retrievedOwnerEntity = session.load(OwnerEntity::class.java, ownerEntity.id)
            ?: throw Exception("Owner could not be retrieved")
        return Owner(retrievedOwnerEntity.id, retrievedOwnerEntity.name)
    }
}
