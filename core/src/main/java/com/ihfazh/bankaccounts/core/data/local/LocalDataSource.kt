package com.ihfazh.bankaccounts.data.local

import com.ihfazh.bankaccounts.data.local.database.AppDatabase
import com.ihfazh.bankaccounts.data.local.entity.AccountWithBank
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val database: AppDatabase) {
    fun getAllBanks() = database.bankDao().getAll()
    fun getFavoritedBanks() = database.bankDao().getFavorites()
    fun addAll(data: List<BankEntity>): Completable = database.bankDao().addAll(data)
    fun addBankAccount(bankAccount: BankAccountEntity): Completable =
            database.bankAccountDao().insert(bankAccount)

    fun getBankById(id: String): Flowable<BankEntity> = database.bankDao().getById(id)
    fun getAllBankAccounts(): Flowable<List<AccountWithBank>> = database.bankAccountDao().getAll()
    fun deleteBankAccount(bankAccount: BankAccountEntity): Completable =
            database.bankAccountDao().delete(bankAccount)

    fun getBankAccount(id: Int): Flowable<AccountWithBank> = database.bankAccountDao().get(id)
    fun updateBankAccount(bankAccount: BankAccountEntity): Completable =
            database.bankAccountDao().update(bankAccount)

    fun getFavoritedBankAccounts(): Flowable<List<AccountWithBank>> = database.bankAccountDao().listFavorite()
    fun updateBank(mapBankToEntity: BankEntity): Completable = database.bankDao().update(mapBankToEntity)
    fun searchBank(search: String) = database.bankDao().searchBank("%${search.toUpperCase(Locale.ROOT)}%")
}