package com.ihfazh.bankaccounts.ui.banks

import androidx.recyclerview.widget.DiffUtil
import com.ihfazh.bankaccounts.core.domain.data.Bank

class BankDiffCallback(val oldList: List<Bank>, val newList: List<Bank>): DiffUtil.Callback(){

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id.equals(newList[newItemPosition].id)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}