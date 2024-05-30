package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.port.`in`.web.requests.TransactionCSVRowRequest

interface ImportTransactionFromCSVRowUseCase {
    fun import(transactionRequest: TransactionCSVRowRequest, requesterIBAN: String)
}