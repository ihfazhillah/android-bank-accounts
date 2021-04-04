package com.ihfazh.bankaccounts.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "bank_account",
    foreignKeys = [
        ForeignKey(
            entity = BankEntity::class,
            parentColumns = ["id"],
            childColumns = ["bank_id"],
            onDelete = CASCADE
        )
    ]
)
class BankAccountEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "bank_id")
    val bank_id: String,

    @ColumnInfo(name = "account_holder")
    val account_holder: String,

    @ColumnInfo(name = "account_number")
    val account_number: String,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false

)