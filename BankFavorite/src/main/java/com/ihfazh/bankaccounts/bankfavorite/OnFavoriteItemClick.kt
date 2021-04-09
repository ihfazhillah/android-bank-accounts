package com.ihfazh.bankaccounts.bankfavorite

import com.ihfazh.bankaccounts.core.domain.data.Bank

interface OnFavoriteItemClick {
    fun onCLick(item: Bank)
}
