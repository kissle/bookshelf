package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.out.peristence.LoadAccountPort

@ApplicationScoped
class LoadAccountAdapter: LoadAccountPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun load(iban: String): Account {
        val session = sessionFactory.openSession()
        return session.load(Account::class.java, iban)
            ?: throw Exception("Account could not be retrieved")
    }
}