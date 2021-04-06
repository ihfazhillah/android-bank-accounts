package com.ihfazh.bankaccounts.data

import androidx.paging.PagedList
import androidx.paging.toFlowable
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.repository.IBankRepository
import com.ihfazh.bankaccounts.core.domain.utils.BankDataMapper
import com.ihfazh.bankaccounts.data.local.LocalDataSource
import com.ihfazh.bankaccounts.data.remote.NetworkBoundResource
import com.ihfazh.bankaccounts.data.remote.RemoteDataSource
import com.ihfazh.bankaccounts.data.remote.network.ApiResponse
import com.ihfazh.bankaccounts.data.remote.responses.BanksItem
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BankRepository @Inject constructor(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
    ): IBankRepository {
    override fun getAllBanks(): Flowable<Resource<PagedList<Bank>>> =
        object : NetworkBoundResource<PagedList<Bank>, List<BanksItem>>() {
            override fun loadFromDB(): Flowable<PagedList<Bank>> =
                localDataSource.getAllBanks().map {
                    BankDataMapper.mapBankEntityToDomain(it)
                }.toFlowable(pageSize = 10)
//            localDataSource.getAllBanks().map{
//            BankDataMapper.mapEntitiesToDomain(it)
//        }.toFlowable(pageSize = 20)

            override fun shouldFetch(data: PagedList<Bank>?): Boolean = data.isNullOrEmpty()
            override fun createCall(): Flowable<ApiResponse<List<BanksItem>>> =
                remoteDataSource.getAllBanks()

            override fun saveCallResult(data: List<BanksItem>) {
                localDataSource.addAll(BankDataMapper.mapDomainsToEntities(data))
                    .subscribeOn(
                        Schedulers.computation()
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }

    }.asFlowable()

    override fun getBankById(id: String): Flowable<Bank> = localDataSource.getBankById(id).map{
        Bank(it.id, it.name, it.code, it.image)
    }
    override fun addBank(bank: Bank): Completable {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteBank(bank: Bank): Completable {
        TODO("Not yet implemented")
    }

    override fun addBankAccount(bank: BankAccount): Completable {
        return localDataSource.addBankAccount(BankDataMapper.mapDomainToEntity(bank))
    }

    override fun getAllBankAccounts(): Flowable<List<BankAccount>> =
        localDataSource.getAllBankAccounts().map {
            BankDataMapper.mapBankAccountEntitiesToDomain(it)
        }

    override fun deleteBankAccount(bankAccount: BankAccount): Completable {
        val bankAccountEntity = BankDataMapper.mapBankAccountToEntity(bankAccount)
        return localDataSource.deleteBankAccount(bankAccountEntity)
    }

    override fun getBankAccount(id: Int): Flowable<BankAccount> {
        return localDataSource.getBankAccount(id).map {
            BankDataMapper.mapBankAccountEntityToDomain(it)
        }
    }

    override fun updateBankAccount(bankAccount: BankAccount): Completable {
        return localDataSource.updateBankAccount(
                BankDataMapper.mapBankAccountToEntity(bankAccount)
        )
    }

    override fun toggleFavorite(bankAccount: BankAccount): Completable {
        val newState = !bankAccount.favorite
        bankAccount.favorite = newState
        return localDataSource.updateBankAccount(
                BankDataMapper.mapBankAccountToEntity(bankAccount)
        )
    }

    override fun getFavoritedBankAccount(): Flowable<List<BankAccount>> =
            localDataSource.getFavoritedBankAccounts().map {
                BankDataMapper.mapBankAccountEntitiesToDomain(it)
            }
}