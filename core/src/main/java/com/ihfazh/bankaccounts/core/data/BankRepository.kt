package com.ihfazh.bankaccounts.data

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
    override fun getAllBanks(): Flowable<Resource<List<Bank>>> = object: NetworkBoundResource<List<Bank>, List<BanksItem>>(){
        override fun loadFromDB(): Flowable<List<Bank>> = localDataSource.getAllBanks().map{
            BankDataMapper.mapEntitiesToDomain(it)
        }

        override fun shouldFetch(data: List<Bank>?): Boolean = data.isNullOrEmpty()
        override fun createCall(): Flowable<ApiResponse<List<BanksItem>>> = remoteDataSource.getAllBanks()

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

}