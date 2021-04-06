package com.ihfazh.bankaccounts.ui.utils

import android.view.View
import com.ihfazh.bankaccounts.core.domain.data.BankAccount

interface IBankAccountItemListener {
    fun onMoreClick(bankAccount: BankAccount, btn: View)
    fun onShareClick(bankAccount: BankAccount, btn: View)
    fun onCopyClick(bankAccount: BankAccount, btn: View)
    fun onFavoriteClick(bankAccount: BankAccount, btn: View, position: Int)
}