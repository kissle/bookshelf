package secondbrain.kissle.finance.application.port.`in`.web

import secondbrain.kissle.finance.application.port.`in`.web.request.TransactionCSVRowRequest

interface ImportTransactionFromCSVRowUseCase {
    fun import(transactionRequest: TransactionCSVRowRequest, requesterIBAN: String)
}