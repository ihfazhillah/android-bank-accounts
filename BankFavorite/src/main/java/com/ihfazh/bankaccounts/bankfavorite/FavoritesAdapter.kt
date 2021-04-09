package com.ihfazh.bankaccounts.bankfavorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.core.domain.data.Bank
import com.ihfazh.bankaccounts.databinding.BankItemBinding
import com.squareup.picasso.Picasso

class FavoritesAdapter(val onItemClick: OnFavoriteItemClick) : PagedListAdapter<Bank, FavoritesAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Bank>() {
            override fun areItemsTheSame(oldItem: Bank, newItem: Bank): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Bank, newItem: Bank): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: BankItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bank: Bank) {
            binding.tvCode.text = bank.code
            binding.tvName.text = bank.name

            Picasso.get()
                    .load(bank.image)
                    .resize(100, 50)
                    .into(binding.imgLogo)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BankItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bank = getItem(position)
        if (bank != null) {
            holder.bind(bank)
            holder.itemView.setOnClickListener {
                onItemClick.onCLick(bank)
            }
        }
    }
}