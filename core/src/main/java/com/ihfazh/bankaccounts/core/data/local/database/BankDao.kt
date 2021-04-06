package com.ihfazh.bankaccounts.data.local.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BankDao {
    @Query("select * from bank")
    fun getAll(): DataSource.Factory<Int, BankEntity>

    @Query("select * from bank where id = :id")
    fun getById(id: String): Flowable<BankEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<BankEntity>): Completable
}