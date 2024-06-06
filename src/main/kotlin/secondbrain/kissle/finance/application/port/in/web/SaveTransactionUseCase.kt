package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.domain.Transaction
import secondbrain.kissle.finance.application.port.`in`.web.request.TransactionRequest

interface SaveTransactionUseCase {
    fun save(transaction: TransactionRequest): Transaction
}