package com.ihfazh.bankaccounts.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.database.BankDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    val migration2_3 = object: Migration(2, 3){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
                begin transaction;
                create temporary table _bank(
                    id TEXT,
                    name TEXT,
                    code TEXT,
                    image TEXT
                )
                insert into _bank select id, name, code, image from bank;
                drop table bank;
                create table bank (
                    id TEXT primary key not null,
                    name TEXT not null,
                    code TEXT,
                    image TEXT
                )
                insert into bank(id, name, code, image)
                select id, name, code, image
                from _bank;
                drop table _bank;
                commit;
            """.trimIndent())
        }
    }

    @Provides
    fun provideBankDao(appDatabase: AppDatabase): BankDao  = appDatabase.bankDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "bank-account.db"
            ).addMigrations(MIGRATION_1_2, migration2_3)
                    .build()


}