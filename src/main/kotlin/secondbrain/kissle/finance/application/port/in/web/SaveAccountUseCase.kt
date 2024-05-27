package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.domain.Account

interface SaveAccountUseCase {
    fun save(account: Account): Account
}