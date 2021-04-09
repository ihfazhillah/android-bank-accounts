package com.ihfazh.bankaccounts.core.domain.usecase

import androidx.paging.PagedList
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.data.Resource
import io.reactivex.Completable
import io.reactivex.Flowable

interface BankUseCase {
    fun getAllBanks(): Flowable<Resource<PagedList<Bank>>>
    fun getFavoritedbanks(): Flowable<PagedList<Bank>>
    fun getBankById(id: String): Flowable<Bank>
    fun addBank(bank: Bank): Completable
    fun updateBank(bank: Bank): Completable
    fun deleteBank(bank: Bank): Completable

    fun addBankAccount(bankAccount: BankAccount): Completable
    fun getAllBankAccounts(): Flowable<List<BankAccount>>
    fun deleteBankAccount(bankAccount: BankAccount): Completable
    fun getBankAccount(id: Int): Flowable<BankAccount>
    fun updateBankAccount(bankAccount: BankAccount): Completable
    fun toggleFavorite(bankAccount: BankAccount): Completable
    fun getFavoritedBankAccounts(): Flowable<List<BankAccount>>
    fun toggleBankFavorite(bankAccount: Bank): Completable
}