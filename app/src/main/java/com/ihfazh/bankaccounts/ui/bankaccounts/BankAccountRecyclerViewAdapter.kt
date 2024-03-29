package com.ihfazh.bankaccounts.ui.bankaccounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.core.domain.utils.BankFormatter
import com.ihfazh.bankaccounts.databinding.BankAccountItemBinding
import com.ihfazh.bankaccounts.ui.utils.IBankAccountItemListener
import com.squareup.picasso.Picasso

class BankAccountRecyclerViewAdapter :
    RecyclerView.Adapter<BankAccountRecyclerViewAdapter.ViewHolder>() {

    var itemListener: IBankAccountItemListener? = null

    class ViewHolder(val binding: BankAccountItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(account: BankAccount) {
            binding.tvNumber.text = BankFormatter.formatAccountNumber(account.account_number)
            binding.tvHolder.text = account.account_holder
            Picasso.get()
                .load(account.bank.image)
                .resize(100, 50)
                .into(binding.bankLogo)
            toggleFavoriteIcon(account.favorite)
        }

        private fun toggleFavoriteIcon(state: Boolean) {
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
        val bank = asyncCallback.currentList.getOrNull(position)
        if (bank != null) {

            holder.binding.apply {
                btnShare.setOnClickListener { itemListener?.onShareClick(bank, it) }
                btnCopy.setOnClickListener { itemListener?.onCopyClick(bank, it) }
                btnMore.setOnClickListener { itemListener?.onMoreClick(bank, it) }
                btnLove.setOnClickListener { itemListener?.onFavoriteClick(bank, it, position) }
            }

            holder.bind(bank)
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<BankAccount>() {
        override fun areItemsTheSame(oldItem: BankAccount, newItem: BankAccount): Boolean =
                oldItem.id == newItem.id

        override fun areContentsTheSame(old: BankAccount, new: BankAccount): Boolean {
            return old == new
        }
    }

    private val asyncCallback = AsyncListDiffer(this, diffCallback)

    fun setBanks(banks: List<BankAccount>) {
        asyncCallback.submitList(banks)
    }


    override fun getItemCount(): Int = asyncCallback.currentList.size

}