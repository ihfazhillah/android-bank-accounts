package com.ihfazh.bankaccounts.ui.slideshow

import androidx.recyclerview.widget.DiffUtil
import com.ihfazh.bankaccounts.core.domain.data.BankAccount

class BankAccountDiffCallback(val banks: List<BankAccount>, val banks1: List<BankAccount>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return this.banks.size
    }

    override fun getNewListSize(): Int {
        return this.banks1.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = this.banks[oldItemPosition]
        val new = this.banks1[newItemPosition]
        return old.id == new.id

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return this.banks[oldItemPosition] == this.banks1[newItemPosition]
    }

}
