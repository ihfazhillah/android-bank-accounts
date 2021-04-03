package com.ihfazh.bankaccounts.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AccountWithBank(
    @Relation(
        parentColumn = "id",
        entityColumn = "bank_id"
    ) val bank: BankEntity,
    @Embedded val accountEntity: BankAccountEntity
)
