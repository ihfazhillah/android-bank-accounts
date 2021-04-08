package com.ihfazh.bankaccounts.ui.banks

import com.ihfazh.bankaccounts.core.domain.data.Bank

interface OnBankItemClick {
    fun onClick(bank: Bank)
}