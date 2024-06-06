package secondbrain.kissle.finance.adapter.out.persistence.entity

import secondbrain.kissle.finance.application.domain.Transaction

class TransactionMapper {
    companion object {
        fun toEntity(transaction: Transaction): TransactionEntity {
            return TransactionEntity.create(
                transaction.id,
                AccountMapper.toEntity(transaction.sourceAccount),
                AccountMapper.toEntity(transaction.targetAccount),
                transaction.amount,
                transaction.date,
                transaction.purpose
            )
        }

        fun toDomain(transactionEntity: TransactionEntity): Transaction {
            return Transaction(
                transactionEntity.id,
                AccountMapper.toDomain(transactionEntity.sourceAccount),
                AccountMapper.toDomain(transactionEntity.targetAccount),
                transactionEntity.amount,
                transactionEntity.date,
                transactionEntity.purpose
            )
        }
    }
}
