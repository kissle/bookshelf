package secondbrain.kissle.finance.adapter.out.persistence.entity

import secondbrain.kissle.finance.application.domain.Account

class AccountMapper {
    companion object {
        fun toEntity(account: Account): AccountEntity {
            val owners = account.owners.map { OwnerMapper.toEntity(it) }
            return AccountEntity.create(account.iban, owners)
        }

        fun toDomain(accountEntity: AccountEntity): Account {
            val owners = accountEntity.owners.map { OwnerMapper.toDomain(it) }
            return Account(accountEntity.iban, owners)
        }
    }
}