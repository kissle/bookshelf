package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.out.peristence.LoadOwnerPort

@ApplicationScoped
class LoadOwnerAdapter(
    @Inject private var sessionFactory: SessionFactory
): LoadOwnerPort {

    override fun findByName(name: String): Owner {
        val session = sessionFactory.openSession()
        val query = "MATCH (o:Owner) WHERE o.name = {name} RETURN o"
        val result = session.query(Owner::class.java, query, mapOf("name" to name))
        return result.firstOrNull() ?: throw Exception("Owner not found")
    }
}
