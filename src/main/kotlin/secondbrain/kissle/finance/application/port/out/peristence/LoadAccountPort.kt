package secondbrain.kissle.finance.application.port.out.peristence

import secondbrain.kissle.finance.application.domain.Account

interface LoadAccountPort {
    fun load(iban: String): Account
}