package com.ihfazh.bankaccounts.ui.bankaccounts

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.ihfazh.bankaccounts.core.domain.data.BankAccount

class BankAccountDiffCallback(val banks: List<BankAccount>, private val banks1: List<BankAccount>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return banks.size
    }

    override fun getNewListSize(): Int {
        return banks1.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = banks[oldItemPosition]
        val new = banks1[newItemPosition]
        return old == new

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // ini akan dipanggil kalau yang ada true
        Log.d("bankaccountdiffcallback", "areContentsTheSame: called")
        val result = banks[oldItemPosition] == banks1[newItemPosition]
        Log.d("bankaccountdiffcallback", "areContentsTheSame result: " + result)
        return result
    }

}
