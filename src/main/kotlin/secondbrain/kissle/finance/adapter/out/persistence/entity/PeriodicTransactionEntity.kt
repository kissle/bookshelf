package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import java.time.LocalDateTime
import java.time.Period

@NodeEntity("PeriodicTransaction")
class PeriodicTransactionEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Relationship(type = "COMPLETED_TRANSACTION")
    var completed: MutableList<TransactionEntity> = mutableListOf()

    var period = Period.of(0, 1, 0).toString()
    var currentAmount = 0L
    var lastTransactionDate: LocalDateTime? = null

    companion object {
        fun create(id: Long?, completed: MutableList<TransactionEntity>, period: Period, currentAmount: Long, lastTransactionDate: LocalDateTime?): PeriodicTransactionEntity {
            return PeriodicTransactionEntity().apply {
                this.id = id
                this.completed = completed
                this.period = period.toString()
                this.currentAmount = currentAmount
                this.lastTransactionDate = lastTransactionDate
            }
        }
    }
}