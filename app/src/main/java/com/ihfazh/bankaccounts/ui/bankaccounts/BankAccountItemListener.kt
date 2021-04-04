package com.ihfazh.bankaccounts.ui.bankaccounts

import android.view.View
import com.ihfazh.bankaccounts.core.domain.data.BankAccount

interface BankAccountItemListener {
    fun onMoreClick(bank: BankAccount, btn: View)
    fun onShareClick(bank: BankAccount, btn: View)
    fun onCopyClick(bank: BankAccount, btn: View)
}