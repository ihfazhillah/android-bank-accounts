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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
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
            """.trimIndent()
            )
        }
    }

    private val migration2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
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
            """.trimIndent()
            )
        }
    }

    private val migration3To4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                    alter table bank_account
                    add column favorite INTEGER NOT NULL default 0;
                """.trimIndent()
            )
        }
    }

    private val migration4To5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                        alter table bank
                        add column favorite INTEGER NOT NULL default 0;
                    """.trimIndent()
            )
        }
    }

    @Provides
    fun provideBankDao(appDatabase: AppDatabase): BankDao = appDatabase.bankDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase = SQLiteDatabase.getBytes("ihfazhillah".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "bank-account.db"
        ).addMigrations(MIGRATION_1_2, migration2_3, migration3To4, migration4To5)
                .openHelperFactory(factory)
                .build()
    }


}