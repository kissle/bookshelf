package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.OwnerEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.OwnerMapper
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.out.SaveOwnerPort

@ApplicationScoped
class SaveOwnerAdapter: SaveOwnerPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun save(owner: Owner): Owner {
        val session = sessionFactory.openSession()
        val ownerEntity = OwnerMapper.toEntity(owner)
        session.save(ownerEntity)

        val retrievedEntity = session.load(OwnerEntity::class.java, ownerEntity.id)
            ?: throw Exception("Owner could not be retrieved")
        return OwnerMapper.toDomain(retrievedEntity)
    }
}
