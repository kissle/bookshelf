package secondbrain.kissle.finance.application.domain

import java.time.LocalDateTime
import java.time.Period

class PeriodicTransaction(
    val id: Long?
) {
    val completed: MutableList<Transaction> = mutableListOf()

    var period: Period = Period.of(0, 1, 0)
    private set
    var currentAmount: Long = 0
    private set
    var lastTransactionDate: LocalDateTime? = null
    private set
    /**
     * Returns a list of planned transactions until the specified date.
     * The transactions are based on the last completed transaction and the period.
     */
    fun getPlannedTransactions(untilDate: LocalDateTime): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        var nextDate = getLastDateTime().plus(period)
        while (nextDate.isBefore(untilDate) && isBeforeOrEqualLastDate(nextDate)) {
            transactions.add(
                Transaction(
                    null,
                    completed.last().sourceAccount,
                    completed.last().targetAccount,
                    currentAmount,
                    nextDate,
                    "Planned Transaction: ${completed.last().purpose}"
                ))
            nextDate = nextDate.plus(period)
        }
        return transactions
    }

    private fun isBeforeOrEqualLastDate(nextDate: LocalDateTime): Boolean {
        if (lastTransactionDate == null) return true
        return nextDate.isBefore(lastTransactionDate) || nextDate.isEqual(lastTransactionDate)
    }

    private fun getLastDateTime(): LocalDateTime {
        return if (completed.isEmpty()) LocalDateTime.now() else completed.last().dateTime
    }

    fun addCompletedTransactions(transactions: List<Transaction>) {
        transactions.forEach {
            if (completed.contains(it)) return
            if (completed.isEmpty() || it.dateTime.isAfter(completed.last().dateTime)) {
                completed.add(it)
            } else {
                val index = completed.indexOfFirst { completedTransaction ->
                    it.dateTime.isBefore(completedTransaction.dateTime) }
                completed.add(index, it)
            }
        }
    }

    fun updatePeriod(period: Period) {
        this.period = period
    }

    fun updateCurrentAmount(amount: Long) {
        this.currentAmount = amount
    }

    fun updateLastTransactionDate(date: LocalDateTime?) {
        this.lastTransactionDate = date
    }
}