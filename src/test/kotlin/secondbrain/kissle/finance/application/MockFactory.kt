package secondbrain.kissle.finance.application

import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.domain.Transaction
import java.time.LocalDate

class MockFactory {
    companion object {
        fun getOwner(id: Long) = Owner(id, "Owner$id")
        fun getAccount(id: Long) = Account("DE{id}", listOf(getOwner(id)))
        fun getTransaction(id: Long, date: LocalDate) = Transaction(
            id,
            getAccount(1L),
            getAccount(2L),
            200L,
            date,
            "Purpose"
        )
    }
}