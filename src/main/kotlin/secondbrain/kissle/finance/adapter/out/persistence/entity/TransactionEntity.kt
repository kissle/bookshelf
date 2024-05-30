package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDateTime

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
    var dateTime: LocalDateTime = LocalDateTime.now()
    var purpose: String = ""

    companion object {
        fun create(id: Long?, sourceAccount: AccountEntity, targetAccount: AccountEntity, value: Long, dateTime: LocalDateTime, purpose: String): TransactionEntity {
            return TransactionEntity().apply {
                this.id = id
                this.sourceAccount = sourceAccount
                this.targetAccount = targetAccount
                this.amount = value
                this.dateTime = dateTime
                this.purpose = purpose
            }
        }
    }
}