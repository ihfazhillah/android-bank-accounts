package com.ihfazh.bankaccounts.core.domain.data

data class BankAccount(
    var id: Int? = null,
    val bank: Bank,
    val account_holder: String,
    val account_number: String
)
