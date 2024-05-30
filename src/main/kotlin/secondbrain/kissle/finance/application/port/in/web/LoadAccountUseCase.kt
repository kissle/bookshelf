package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.domain.Account

interface LoadAccountUseCase {
    fun load(iban: String): Account
}