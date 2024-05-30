package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.AccountEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.AccountMapper
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.port.out.SaveAccountPort

@ApplicationScoped
class SaveAccountAdapter: SaveAccountPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory


    override fun save(account: Account): Account {
        val session = sessionFactory.openSession()
        val accountEntity = AccountMapper.toEntity(account)
        session.save(accountEntity)

        val retrievedAccountEntity = session.load(AccountEntity::class.java, accountEntity.iban)
            ?: throw Exception("Account could not be retrieved")
        return AccountMapper.toDomain(retrievedAccountEntity)
    }
}