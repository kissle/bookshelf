package secondbrain.kissle.finance.application.domain

import java.time.LocalDate
import java.time.Period

class PeriodicTransaction(
    val id: Long?
) {
    val completed: MutableList<Transaction> = mutableListOf()

    var period: Period = Period.of(0, 1, 0)
    private set
    var currentAmount: Long = 0
    private set
    var lastTransactionDate: LocalDate? = null
    private set
    /**
     * Returns a list of planned transactions until the specified date.
     * The transactions are based on the last completed transaction and the period.
     */
    fun getPlannedTransactions(untilDate: LocalDate): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        var nextDate = getLastDateTime().plus(period)
        while (isBeforeOrEqualsUntilDate(nextDate, untilDate) && isBeforeOrEqualLastDate(nextDate)) {
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

    private fun isBeforeOrEqualsUntilDate(nextDate: LocalDate, untilDate: LocalDate): Boolean {
        return nextDate.isBefore(untilDate) || nextDate.isEqual(untilDate)
    }

    private fun isBeforeOrEqualLastDate(nextDate: LocalDate): Boolean {
        if (lastTransactionDate == null) return true
        return nextDate.isBefore(lastTransactionDate) || nextDate.isEqual(lastTransactionDate)
    }

    private fun getLastDateTime(): LocalDate {
        return if (completed.isEmpty()) LocalDate.now() else completed.last().date
    }

    fun addCompletedTransactions(transactions: List<Transaction>) {
        transactions.forEach {
            if (completed.contains(it)) return
            if (completed.isEmpty() || it.date.isAfter(completed.last().date)) {
                completed.add(it)
            } else {
                val index = completed.indexOfFirst { completedTransaction ->
                    it.date.isBefore(completedTransaction.date) }
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

    fun updateLastTransactionDate(date: LocalDate?) {
        this.lastTransactionDate = date
    }
}