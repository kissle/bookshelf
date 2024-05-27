package secondbrain.kissle.finance.adapter.out.persistence

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.neo4j.ogm.session.SessionFactory
import secondbrain.kissle.finance.adapter.out.persistence.entity.AccountEntity
import secondbrain.kissle.finance.adapter.out.persistence.entity.OwnerEntity
import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.port.`in`.web.SaveOwnerUseCase
import secondbrain.kissle.finance.application.port.out.SaveAccountPort

@ApplicationScoped
class SaveAccountAdapter: SaveAccountPort {

    @Inject
    private lateinit var sessionFactory: SessionFactory

    @Inject lateinit var saveOwnerUseCase: SaveOwnerUseCase

    override fun save(account: Account): Account {
        val session = sessionFactory.openSession()

        val ownerEntities = getOrCreateOwner(account.owner)
        val accountEntity = AccountEntity.create(account.iban, ownerEntities)
        session.save(accountEntity)

        val retrievedAccountEntity = session.load(AccountEntity::class.java, accountEntity.iban)
            ?: throw Exception("Account could not be retrieved")
        val owners = retrievedAccountEntity.owners.map { ownerEntity -> Owner(ownerEntity.id, ownerEntity.name) }
        return Account(retrievedAccountEntity.iban, owners)
    }

    private fun getOrCreateOwner(owners: List<Owner>): List<OwnerEntity> {
        return owners.map { owner -> getOrCreateOwner(owner) }
    }

    private fun getOrCreateOwner(owner: Owner): OwnerEntity {
        return if (owner.id != null) {
                OwnerEntity.create(owner.id, owner.name)
            } else {
                val savedOwner = saveOwnerUseCase.save(owner.name)
                OwnerEntity.create(savedOwner.id, savedOwner.name)
            }
    }
}