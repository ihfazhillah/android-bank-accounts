package com.ihfazh.bankaccounts.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
}