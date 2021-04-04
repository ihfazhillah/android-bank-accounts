package com.ihfazh.bankaccounts.ui.bankaccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.R
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

        fun toggleFavoriteIcon(state: Boolean) {
            if (state) {
                binding.btnLove.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.btnLove.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
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

            holder.binding.apply {
                btnShare.setOnClickListener { itemListener?.onShareClick(bank, it) }
                btnCopy.setOnClickListener { itemListener?.onCopyClick(bank, it) }
                btnMore.setOnClickListener { itemListener?.onMoreClick(bank, it) }
                btnLove.setOnClickListener { itemListener?.onFavoriteClick(bank, it) }
            }

            holder.bind(bank)
            holder.toggleFavoriteIcon(bank.favorite)
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