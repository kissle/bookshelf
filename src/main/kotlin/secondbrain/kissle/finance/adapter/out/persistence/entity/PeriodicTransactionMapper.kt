package secondbrain.kissle.finance.adapter.out.persistence.entity

import secondbrain.kissle.finance.application.domain.PeriodicTransaction
import java.time.Period

class PeriodicTransactionMapper {
    companion object {
        fun toEntity(periodicTransaction: PeriodicTransaction): PeriodicTransactionEntity {
            return PeriodicTransactionEntity.create(
                periodicTransaction.id,
                periodicTransaction.completed.map { TransactionMapper.toEntity(it) }.toMutableList(),
                periodicTransaction.period,
                periodicTransaction.currentAmount,
                periodicTransaction.lastTransactionDate
            )
        }

        fun toDomain(periodicTransactionEntity: PeriodicTransactionEntity): PeriodicTransaction {
            val periodicTransaction = PeriodicTransaction(periodicTransactionEntity.id)
            mapCompletedTransactions(periodicTransactionEntity, periodicTransaction)
            mapPeriod(periodicTransactionEntity, periodicTransaction)
            mapCurrentAmount(periodicTransactionEntity, periodicTransaction)
            mapLastTransactionDate(periodicTransactionEntity, periodicTransaction)
            return periodicTransaction
        }

        private fun mapCompletedTransactions(
            periodicTransactionEntity: PeriodicTransactionEntity,
            periodicTransaction: PeriodicTransaction
        ) {
            val transactions = periodicTransactionEntity.completed.map { TransactionMapper.toDomain(it) }
            periodicTransaction.addCompletedTransactions(transactions)
        }

        private fun mapPeriod(
            periodicTransactionEntity: PeriodicTransactionEntity,
            periodicTransaction: PeriodicTransaction
        ) {
            periodicTransaction.updatePeriod(Period.parse(periodicTransactionEntity.period))
        }

        private fun mapCurrentAmount(
            periodicTransactionEntity: PeriodicTransactionEntity,
            periodicTransaction: PeriodicTransaction
        ) {
            periodicTransaction.updateCurrentAmount(periodicTransactionEntity.currentAmount)
        }

        private fun mapLastTransactionDate(
            periodicTransactionEntity: PeriodicTransactionEntity,
            periodicTransaction: PeriodicTransaction
        ) {
            periodicTransaction.updateLastTransactionDate(periodicTransactionEntity.lastTransactionDate)
        }
    }
}