package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.out.peristence.SaveAccountPort

@ApplicationScoped
class SaveAccountAdapter: SaveAccountPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun save(account: Account): Account {
        val session = sessionFactory.openSession()
        session.save(account)

        return session.load(Account::class.java, account.iban)
            ?: throw Exception("Account could not be retrieved")
    }
}