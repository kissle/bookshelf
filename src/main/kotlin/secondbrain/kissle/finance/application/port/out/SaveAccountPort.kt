package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.Account

interface SaveAccountPort {
    fun save(account: Account): Account
}