package com.ihfazh.bankaccounts.domain.utils

import android.util.Log
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import com.ihfazh.bankaccounts.data.local.entity.BankEntity
import com.ihfazh.bankaccounts.data.remote.responses.BanksItem
import com.ihfazh.bankaccounts.domain.data.Bank
import com.ihfazh.bankaccounts.domain.data.BankAccount

class BankDataMapper {
    companion object {
        private  val TAG = "BankDataMapper"
       fun mapEntitiesToDomain(entities: List<BankEntity>): List<Bank>{
           return entities.map {
               Bank(it.id, it.name, it.code, it.image)
           }
       }

        fun mapDomainsToEntities(data: List<BanksItem>): List<BankEntity> {
            return data.map{
                val id = it.name.split(" ").joinToString("-")
                BankEntity(id, it.name, it.code, it.image)
            }
        }

        fun mapDomainToEntity(bankAccount: BankAccount): BankAccountEntity {
            return BankAccountEntity(
                bank_id = bankAccount.bank.id,
                account_holder = bankAccount.account_holder,
                account_number = bankAccount.account_number
            )
        }
    }

}
