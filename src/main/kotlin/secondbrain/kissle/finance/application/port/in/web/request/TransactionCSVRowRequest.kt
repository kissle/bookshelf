package secondbrain.kissle.finance.application.port.`in`.web.request

data class TransactionCSVRowRequest(
    val owner: String,
    val iban: String,
    val amount: Double,
    val date: String,
    val purpose: String
)
