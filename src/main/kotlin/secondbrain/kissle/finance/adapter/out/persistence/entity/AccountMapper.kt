package secondbrain.kissle.finance.adapter.out.persistence.entity

import secondbrain.kissle.finance.application.domain.Account

class AccountMapper {
    companion object {
        fun toEntity(account: Account): AccountEntity {
            return AccountEntity.create(account.iban, account.owners)
        }

        fun toDomain(accountEntity: AccountEntity): Account {
            return Account(accountEntity.iban, accountEntity.owners)
        }
    }
}