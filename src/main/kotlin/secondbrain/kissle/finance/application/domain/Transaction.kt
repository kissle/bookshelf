package secondbrain.kissle.finance.application.domain

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDate

@NodeEntity
class Transaction private constructor(
    @Id @GeneratedValue
    val id: Long?,

    @Relationship("FROM", direction = Relationship.Direction.INCOMING)
    val sourceAccount: Account,
    @Relationship("TO", direction = Relationship.Direction.INCOMING)
    val targetAccount: Account,
    val amount: Long,
    val date: LocalDate,
    val purpose: String
) {
    companion object {
        fun create(id: Long?, sourceAccount: Account, targetAccount: Account, amount: Long, date: LocalDate, purpose: String): Transaction {
            require(sourceAccount !== targetAccount)
            require(purpose.isNotBlank())
            return Transaction(id, sourceAccount, targetAccount, amount, date, purpose)
        }
    }

    protected constructor() : this(
        null,
        Account.create("DE0815", listOf(Owner.create(null, "DUMMY_SOURCE_ACCOUNT_OWNER"))),
        Account.create("DE0816", listOf(Owner.create(9,"DUMMY_TARGET_ACCOUNT_OWNER"))),
        0L,
        LocalDate.MIN,
        "DUMMY_PURPOSE"
    )
}