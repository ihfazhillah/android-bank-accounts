package com.ihfazh.bankaccounts.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ihfazh.bankaccounts.data.local.entity.BankEntity

@Database(entities = [BankEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun bankDao(): BankDao
}