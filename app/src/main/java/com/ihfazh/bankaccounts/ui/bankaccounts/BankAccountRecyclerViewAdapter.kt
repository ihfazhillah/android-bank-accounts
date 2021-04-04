package com.ihfazh.bankaccounts.ui.bankaccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.databinding.BankAccountItemBinding
import com.squareup.picasso.Picasso

class BankAccountRecyclerViewAdapter :
    RecyclerView.Adapter<BankAccountRecyclerViewAdapter.ViewHolder>() {

    private val banks = mutableListOf<BankAccount>()
    var itemListener: BankAccountItemListener? = null

    class ViewHolder(val binding: BankAccountItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: BankAccount) {
            binding.tvNumber.text = account.account_number
            binding.tvHolder.text = account.account_holder
            Picasso.get()
                    .load(account.bank.image)
                    .resize(100, 50)
                .into(binding.bankLogo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
                BankAccountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = banks.getOrNull(position)
        if (bank != null) {
            holder.bind(banks[position])

            holder.binding.apply {
                btnShare.setOnClickListener { itemListener?.onShareClick(bank, it) }
                btnCopy.setOnClickListener { itemListener?.onCopyClick(bank, it) }
                btnMore.setOnClickListener { itemListener?.onMoreClick(bank, it) }
            }

        }
    }

    fun setBanks(banks: List<BankAccount>) {
        val diffCallback = BankAccountDiffCallback(this.banks, banks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.banks.clear()
        this.banks.addAll(banks)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = banks.size

}