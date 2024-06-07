package secondbrain.kissle.finance.application.port.out.peristence

import secondbrain.kissle.finance.application.domain.Account

interface SaveAccountPort {
    fun save(account: Account): Account
}