package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDate

@NodeEntity("Transaction")
class TransactionEntity {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Relationship("SENT", direction = Relationship.Direction.INCOMING)
    var sourceAccount = AccountEntity()
    @Relationship("RECEIVED", direction = Relationship.Direction.INCOMING)
    var targetAccount = AccountEntity()
    var amount: Long = 0
    var date: LocalDate = LocalDate.now()
    var purpose: String = ""

    companion object {
        fun create(id: Long?, sourceAccount: AccountEntity, targetAccount: AccountEntity, value: Long, date: LocalDate, purpose: String): TransactionEntity {
            return TransactionEntity().apply {
                this.id = id
                this.sourceAccount = sourceAccount
                this.targetAccount = targetAccount
                this.amount = value
                this.date = date
                this.purpose = purpose
            }
        }
    }
}