package com.ihfazh.bankaccounts.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class BankEntity (
    @PrimaryKey var id: String,
    @ColumnInfo val name: String?,
    @ColumnInfo val code: String?,
    @ColumnInfo val image: String?
)