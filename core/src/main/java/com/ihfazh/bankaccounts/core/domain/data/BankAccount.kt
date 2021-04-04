package com.ihfazh.bankaccounts.core.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BankAccount(
    var id: Int? = null,
    val bank: Bank,
    val account_holder: String,
    val account_number: String,
    var favorite: Boolean = false
) : Parcelable {
}
