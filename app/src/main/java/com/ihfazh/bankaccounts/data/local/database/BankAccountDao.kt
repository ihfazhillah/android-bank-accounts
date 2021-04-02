package com.ihfazh.bankaccounts.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import io.reactivex.Completable

@Dao
interface BankAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accountEntity: BankAccountEntity): Completable
}