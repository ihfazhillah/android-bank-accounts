package com.ihfazh.bankaccounts.core.domain.usecase

import androidx.paging.PagedList
import com.ihfazh.bankaccounts.core.data.Resource
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.repository.IBankRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class BankInteractor (private val bankRepository: IBankRepository): BankUseCase {
    override fun getAllBanks(): Flowable<Resource<PagedList<Bank>>> = bankRepository.getAllBanks()
    override fun getFavoritedbanks(): Flowable<PagedList<Bank>> = bankRepository.getFavoritedbanks()

    override fun getBankById(id: String): Flowable<Bank> = bankRepository.getBankById(id)

    override fun addBank(bank: Bank): Completable = bankRepository.addBank(bank)

    override fun updateBank(bank: Bank): Completable = bankRepository.updateBank(bank)

    override fun deleteBank(bank: Bank): Completable = bankRepository.deleteBank(bank)

    override fun addBankAccount(bankAccount: BankAccount): Completable =
        bankRepository.addBankAccount(bankAccount)

    override fun getAllBankAccounts(): Flowable<List<BankAccount>> =
        bankRepository.getAllBankAccounts()

    override fun deleteBankAccount(bankAccount: BankAccount): Completable =
            bankRepository.deleteBankAccount(bankAccount)

    override fun getBankAccount(id: Int): Flowable<BankAccount> = bankRepository.getBankAccount(id)
    override fun updateBankAccount(bankAccount: BankAccount): Completable =
            bankRepository.updateBankAccount(bankAccount)

    override fun toggleFavorite(bankAccount: BankAccount): Completable =
            bankRepository.toggleFavorite(bankAccount)

    override fun getFavoritedBankAccounts(): Flowable<List<BankAccount>> = bankRepository.getFavoritedBankAccount()
    override fun toggleBankFavorite(bankAccount: Bank): Completable = bankRepository.toggleBankFavorite(bankAccount)
    override fun searchBank(search: String): Flowable<Resource<PagedList<Bank>>> = bankRepository.searchBank(search)
}