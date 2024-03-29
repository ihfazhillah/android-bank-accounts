package com.ihfazh.bankaccounts.data.local.database

import androidx.paging.DataSource
import androidx.room.*
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface BankDao {
    @Query("select * from bank order by favorite desc, name")
    fun getAll(): DataSource.Factory<Int, BankEntity>

    @Query("select * from bank where id = :id")
    fun getById(id: String): Flowable<BankEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAll(data: List<BankEntity>): Completable

    @Update
    fun update(mapBankToEntity: BankEntity): Completable

    @Query("select * from bank where favorite = 1")
    fun getFavorites(): DataSource.Factory<Int, BankEntity>

    @Query("select * from bank where UPPER(name) like :search")
    fun searchBank(search: String): DataSource.Factory<Int, BankEntity>
}