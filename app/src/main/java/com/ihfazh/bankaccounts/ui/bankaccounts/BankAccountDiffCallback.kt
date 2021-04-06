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
        return true

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // ini akan dipanggil kalau yang ada true
        Log.d("bankaccountdiffcallback", "areContentsTheSame: called")
        val result = banks[oldItemPosition].id == banks1[newItemPosition].id && banks[oldItemPosition].favorite == banks1[newItemPosition].favorite
        Log.d("bankaccountdiffcallback", "areContentsTheSame result: " + result)
        return result
    }

}
