package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.AccountEntity
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.out.LoadAccountPort

@ApplicationScoped
class LoadAccountAdapter: LoadAccountPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    override fun load(iban: String): Account {
        val session = sessionFactory.openSession()
        val entity =  session.load(AccountEntity::class.java, iban)
            ?: throw Exception("Account could not be retrieved")
        return Account(entity.iban, entity.owners.map { ownerEntity -> Owner(ownerEntity.id, ownerEntity.name) })
    }
}