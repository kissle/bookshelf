package secondbrain.kissle.finance.application.domain

class Account(
    val iban: String,
    val owners: List<Owner>
)