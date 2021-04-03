package com.ihfazh.bankaccounts.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithBank(
    @Relation(
        parentColumn = "bank_id",
        entityColumn = "id"
    ) val bank: BankEntity,
    @Embedded val accountEntity: BankAccountEntity
)
