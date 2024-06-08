package secondbrain.kissle.finance.adapter.out.persistence.entity

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship
import secondbrain.kissle.finance.application.domain.Transaction
import java.time.LocalDate
import java.time.Period

@NodeEntity("PeriodicTransaction")
class PeriodicTransactionEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    @Relationship(type = "COMPLETED_TRANSACTION")
    var completed: MutableList<Transaction> = mutableListOf()

    var period = Period.of(0, 1, 0).toString()
    var currentAmount = 0L
    var lastTransactionDate: LocalDate? = null

    companion object {
        fun create(id: Long?, completed: MutableList<Transaction>, period: Period, currentAmount: Long, lastTransactionDate: LocalDate?): PeriodicTransactionEntity {
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