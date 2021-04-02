package com.ihfazh.bankaccounts.domain.usecase

import com.ihfazh.bankaccounts.data.Resource
import com.ihfazh.bankaccounts.domain.data.Bank
import com.ihfazh.bankaccounts.domain.repository.IBankRepository
import io.reactivex.Completable
import io.reactivex.Flowable

class BankInteractor (private val bankRepository: IBankRepository): BankUseCase {
    override fun getAllBanks(): Flowable<Resource<List<Bank>>> = bankRepository.getAllBanks()

    override fun getBankById(id: Number): Flowable<Bank> = bankRepository.getBankById(id)

    override fun addBank(bank: Bank): Completable = bankRepository.addBank(bank)

    override fun updateBank(bank: Bank): Completable = bankRepository.updateBank(bank)

    override fun deleteBank(bank: Bank): Completable = bankRepository.deleteBank(bank)
}