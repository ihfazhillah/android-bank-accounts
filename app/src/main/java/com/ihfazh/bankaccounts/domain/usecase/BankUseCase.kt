package com.ihfazh.bankaccounts.domain.usecase

import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.domain.data.Bank
import com.ihfazh.bankaccounts.domain.data.BankAccount
import io.reactivex.Completable
import io.reactivex.Flowable

interface BankUseCase {
    fun getAllBanks(): Flowable<Resource<List<Bank>>>
    fun getBankById(id: Number): Flowable<Bank>
    fun addBank(bank: Bank): Completable
    fun updateBank(bank: Bank): Completable
    fun deleteBank(bank: Bank): Completable

    fun addBankAccount(bankAccount: BankAccount): Completable
}