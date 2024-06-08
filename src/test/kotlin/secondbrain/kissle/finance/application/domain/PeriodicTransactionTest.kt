package secondbrain.kissle.finance.application.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Period

class PeriodicTransactionTest {

    private val owner1 = Owner.create(1, "Owner1")
    private val owner2 = Owner.create(2, "Owner2")
    private val account1 = Account("DE1234567890", listOf(owner1))
    private val account2 = Account("DE0987654321", listOf(owner2))


    @Test
    fun `addCompletedTransactions adds transactions in correct order`() {
        val transaction1 = Transaction(1L, account1, account2, 100L, LocalDate.now().minusDays(1), "Purpose1")
        val transaction2 = Transaction(2L, account1, account2, 200L, LocalDate.now(), "Purpose2")
        val periodicTransaction = PeriodicTransaction(1L)

        periodicTransaction.addCompletedTransactions(listOf(transaction2, transaction1))

        assertEquals(2, periodicTransaction.completed.size)
        assertEquals(transaction1, periodicTransaction.completed[0])
        assertEquals(transaction2, periodicTransaction.completed[1])
    }

    @Test
    fun `addCompletedTransactions does not add duplicate transactions`() {
        val transaction = Transaction(1L, account1, account2, 100L, LocalDate.now(), "Purpose1")
        val periodicTransaction = PeriodicTransaction(1L)

        periodicTransaction.addCompletedTransactions(listOf(transaction, transaction))

        assertEquals(1, periodicTransaction.completed.size)
        assertEquals(transaction, periodicTransaction.completed[0])
    }

    @Test
    fun `getPlannedTransactions returns correct transactions`() {
        val transaction = Transaction(1L, account1, account2, 100L, LocalDate.now().minusDays(1), "Purpose1")
        val periodicTransaction = PeriodicTransaction(1L)
        periodicTransaction.addCompletedTransactions(listOf(transaction))
        periodicTransaction.updatePeriod(Period.ofDays(1))
        periodicTransaction.updateCurrentAmount(200L)

        val plannedTransactions = periodicTransaction.getPlannedTransactions(LocalDate.now().plusDays(1))

        assertEquals(2, plannedTransactions.size)
        assertEquals("Planned Transaction: Purpose1", plannedTransactions[0].purpose)
        assertEquals(200L, plannedTransactions[0].amount)
    }

    @Test
    fun `getPlannedTransactions returns only transactions until lastTransactionDate`() {
        val transaction = Transaction(1L, account1, account2, 100L, LocalDate.now().minusDays(1), "Purpose1")
        val periodicTransaction = PeriodicTransaction(1L)
        periodicTransaction.addCompletedTransactions(listOf(transaction))
        periodicTransaction.updatePeriod(Period.ofDays(1))
        periodicTransaction.updateCurrentAmount(200L)
        periodicTransaction.updateLastTransactionDate(LocalDate.now().plusDays(2))

        val plannedTransactions = periodicTransaction.getPlannedTransactions(LocalDate.now().plusDays(10))

        assertEquals(3, plannedTransactions.size)
    }

    @Test
    fun `getPlannedTransactions returns empty list when no completed transactions`() {
        val periodicTransaction = PeriodicTransaction(1L)

        val plannedTransactions = periodicTransaction.getPlannedTransactions(LocalDate.now().plusDays(1))

        assertTrue(plannedTransactions.isEmpty())
    }
}