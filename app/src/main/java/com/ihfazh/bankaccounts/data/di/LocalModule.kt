package com.ihfazh.bankaccounts.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.database.BankDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    val MIGRATION_1_2 = object : Migration(1, 2){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                create table bank_account (
                id INTEGER primary key autoincrement not null,
                bank_id TEXT not null,
                account_holder TEXT not null,
                account_number TEXT not null,
                foreign key (bank_id)
                    references bank(id)
                    on delete cascade
                    on update no action
                )
            """.trimIndent())
        }
    }

    @Provides
    fun provideBankDao(appDatabase: AppDatabase): BankDao  = appDatabase.bankDao()

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "bank-account.db"
        ).addMigrations(MIGRATION_1_2)
            .build()


}