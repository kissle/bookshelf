package secondbrain.kissle.finance.application

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import secondbrain.kissle.finance.application.port.`in`.web.request.CreatePeriodicTransactionFromCompletedTransactionsCommand
import secondbrain.kissle.finance.application.port.out.LoadTransactionPort
import java.time.LocalDate

@QuarkusTest
class CommandExecutionServiceTest {

    @Inject
    lateinit var commandExecutionService: CommandExecutionService

    @InjectMock
    lateinit var loadTransactionPort: LoadTransactionPort

    val transaction1 = MockFactory.getTransaction(1L, LocalDate.now().minusMonths(2L))
    val transaction2 = MockFactory.getTransaction(2L,  LocalDate.now().minusMonths(1L))
    val transaction3 = MockFactory.getTransaction(3L,  LocalDate.now())

    @Test
    fun `should have completed transactions in chronological order`() {
        // given
        val command = CreatePeriodicTransactionFromCompletedTransactionsCommand(listOf(1L, 2L))

        Mockito.`when`(loadTransactionPort.load(listOf(1L, 2L))).thenReturn(listOf(transaction2, transaction1))

        commandExecutionService.execute(transaction1.sourceAccount.iban, command)

        assertNotNull(command.result)
        command.result?.completed?.first()?.date?.let {
            assertTrue(it.isBefore(command.result!!.completed.last().date))
        }
    }

    @Test
    fun `period is identified as one month`() {
        // given
        val command = CreatePeriodicTransactionFromCompletedTransactionsCommand(listOf(1L, 2L, 3L))

        Mockito.`when`(loadTransactionPort.load(listOf(1L, 2L, 3L)))
            .thenReturn(listOf(transaction1, transaction2,transaction3))

        commandExecutionService.execute(transaction1.sourceAccount.iban, command)

        assertNotNull(command.result)
        assertTrue(command.result!!.period.months == 1)
    }

    @Test
    fun `amount is identified as 200`() {
        // given
        val command = CreatePeriodicTransactionFromCompletedTransactionsCommand(listOf(1L, 2L, 3L))

        Mockito.`when`(loadTransactionPort.load(listOf(1L, 2L, 3L)))
            .thenReturn(listOf(transaction1, transaction2,transaction3))

        commandExecutionService.execute(transaction1.sourceAccount.iban, command)

        assertNotNull(command.result)
        assertTrue(command.result!!.currentAmount == 200L)
    }
}