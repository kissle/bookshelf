package secondbrain.kissle.finance.application.port.out

import secondbrain.kissle.finance.application.domain.Account

interface LoadAccountPort {
    fun load(iban: String): Account
}