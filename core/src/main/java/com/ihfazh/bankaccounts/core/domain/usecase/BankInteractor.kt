package com.ihfazh.bankaccounts.core.domain.usecase

import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.repository.IBankRepository
import com.ihfazh.bankaccounts.data.Resource
import io.reactivex.Completable
import io.reactivex.Flowable

class BankInteractor (private val bankRepository: IBankRepository): BankUseCase {
    override fun getAllBanks(): Flowable<Resource<List<Bank>>> = bankRepository.getAllBanks()

    override fun getBankById(id: String): Flowable<Bank> = bankRepository.getBankById(id)

    override fun addBank(bank: Bank): Completable = bankRepository.addBank(bank)

    override fun updateBank(bank: Bank): Completable = bankRepository.updateBank(bank)

    override fun deleteBank(bank: Bank): Completable = bankRepository.deleteBank(bank)

    override fun addBankAccount(bankAccount: BankAccount): Completable =
        bankRepository.addBankAccount(bankAccount)

    override fun getAllBankAccounts(): Flowable<List<BankAccount>> =
        bankRepository.getAllBankAccounts()
}