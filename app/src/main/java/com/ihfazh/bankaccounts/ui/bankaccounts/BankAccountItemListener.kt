package com.ihfazh.bankaccounts.ui.bankaccounts

import android.view.View
import com.ihfazh.bankaccounts.core.domain.data.BankAccount

interface BankAccountItemListener {
    fun onMoreClick(bankAccount: BankAccount, btn: View)
    fun onShareClick(bankAccount: BankAccount, btn: View)
    fun onCopyClick(bankAccount: BankAccount, btn: View)
    fun onFavoriteClick(bankAccount: BankAccount, btn: View)
}