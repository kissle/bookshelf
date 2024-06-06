package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.port.`in`.web.request.CreatePeriodicTransactionFromCompletedTransactionsCommand

interface CreatePeriodicTransactionUseCase {
    fun execute(iban: String, command: CreatePeriodicTransactionFromCompletedTransactionsCommand)
}