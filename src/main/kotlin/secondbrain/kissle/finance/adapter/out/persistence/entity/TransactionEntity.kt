package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDateTime

@NodeEntity("Transaction")
class TransactionEntity {
    @Relationship("TRANSFERRED", direction = Relationship.Direction.INCOMING)
    var sourceAccount = AccountEntity()
    @Relationship("RECEIVED", direction = Relationship.Direction.OUTGOING)
    val targetAccount = AccountEntity()
    val value: Long = 0
    val dateTime: LocalDateTime = LocalDateTime.now()
    val purpose: String = ""
}