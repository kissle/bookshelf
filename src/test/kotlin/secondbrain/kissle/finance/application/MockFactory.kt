package secondbrain.kissle.finance.application

import secondbrain.kissle.finance.application.domain.Account
import secondbrain.kissle.finance.application.domain.Owner
import secondbrain.kissle.finance.application.domain.Transaction
import java.time.LocalDate

class MockFactory {
    companion object {
        private fun getOwner(id: Long) = Owner.create(id, "Owner$id")
        private fun getAccount(id: Long) = Account.create("DE${id}", listOf(getOwner(id)))
        fun getTransaction(id: Long, date: LocalDate) = Transaction.create(
            id,
            getAccount(1L),
            getAccount(2L),
            200L,
            date,
            "Purpose"
        )
    }
}