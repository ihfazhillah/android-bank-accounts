package com.ihfazh.bankaccounts.ui.banks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.databinding.BankItemBinding
import com.squareup.picasso.Picasso

class BankRecyclerViewAdapter :
    PagedListAdapter<Bank, BankRecyclerViewAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : ItemCallback<Bank>() {
            override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean =
                oldItem == newItem
        }
    }


    class ViewHolder(val binding: BankItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bank: Bank?) {
            binding.tvName.text = bank?.name
            binding.tvCode.text = bank?.code
            Picasso.get()
                .load(bank?.image)
                .resize(100, 50)
                .into(binding.imgLogo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BankItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}