package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.out.peristence.SaveOwnerPort

@ApplicationScoped
class SaveOwnerAdapter: SaveOwnerPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun save(owner: Owner): Owner {
        val session = sessionFactory.openSession()
        session.save(owner)

        return session.load(Owner::class.java, owner.id)
            ?: throw Exception("Owner could not be retrieved")
    }
}
