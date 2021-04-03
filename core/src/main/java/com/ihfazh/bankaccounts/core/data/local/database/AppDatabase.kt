package com.ihfazh.bankaccounts.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import javax.inject.Singleton

@Database(entities = [BankEntity::class, BankAccountEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankDao(): BankDao
    abstract fun bankAccountDao(): BankAccountDao
}