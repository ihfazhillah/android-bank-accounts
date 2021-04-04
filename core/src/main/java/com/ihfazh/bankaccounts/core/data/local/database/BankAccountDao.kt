package com.ihfazh.bankaccounts.data.local.database

import androidx.room.*
import com.ihfazh.bankaccounts.data.local.entity.AccountWithBank
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BankAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountEntity: BankAccountEntity): Completable

    @Query("select * from bank_account")
    fun getAll(): Flowable<List<AccountWithBank>>

    @Delete
    fun delete(bankAccount: BankAccountEntity): Completable
}