package com.ihfazh.bankaccounts.core.domain.utils

import com.ihfazh.bankaccounts.core.data.remote.responses.BanksItem
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.data.local.entity.AccountWithBank
import com.ihfazh.bankaccounts.data.local.entity.BankAccountEntity
import com.ihfazh.bankaccounts.data.local.entity.BankEntity

class BankDataMapper {
    companion object {

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
                account_number = bankAccount.account_number,
                favorite = bankAccount.favorite
            )
        }

        fun mapBankAccountEntitiesToDomain(entities: List<AccountWithBank>): List<BankAccount> {
            return entities.map {
                BankAccount(
                        it.accountEntity.id,
                        Bank(it.bank.id, it.bank.name, it.bank.code, it.bank.image, it.bank.favorite),
                        it.accountEntity.account_holder,
                        it.accountEntity.account_number,
                        it.accountEntity.favorite
                )
            }

        }

        fun mapBankAccountToEntity(bankAccount: BankAccount): BankAccountEntity {
            return BankAccountEntity(
                bankAccount.id!!,
                bankAccount.bank.id,
                bankAccount.account_holder,
                bankAccount.account_number,
                bankAccount.favorite
            )

        }

        fun mapBankAccountEntityToDomain(entity: AccountWithBank): BankAccount {
            val bank = Bank(
                    entity.bank.id,
                    entity.bank.name,
                    entity.bank.code,
                    entity.bank.image,
                    entity.bank.favorite
            )
            return BankAccount(
                    entity.accountEntity.id,
                    bank = bank,
                    account_holder = entity.accountEntity.account_holder,
                    account_number = entity.accountEntity.account_number,
                    favorite = entity.accountEntity.favorite
            )

        }

        fun mapBankEntityToDomain(it: BankEntity) = Bank(it.id, it.name, it.code, it.image, it.favorite)
        fun mapBankToEntity(bankAccount: Bank): BankEntity =
                BankEntity(bankAccount.id, bankAccount.name, bankAccount.code, bankAccount.image, bankAccount.favorite)

    }

}
