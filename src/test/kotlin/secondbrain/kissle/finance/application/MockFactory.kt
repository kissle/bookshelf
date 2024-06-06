package secondbrain.kissle.finance.application

import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.domain.Transaction
import java.time.LocalDateTime

class MockFactory {
    companion object {
        fun getOwner(id: Long) = Owner(id, "Owner$id")
        fun getAccount(id: Long) = Account("DE{id}", listOf(getOwner(id)))
        fun getTransaction(id: Long, dateTime: LocalDateTime) = Transaction(
            id,
            getAccount(1L),
            getAccount(2L),
            200L,
            dateTime,
            "Purpose"
        )
    }
}