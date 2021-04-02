package com.ihfazh.bankaccounts.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.databinding.BankItemBinding
import com.ihfazh.bankaccounts.domain.data.Bank
import com.ihfazh.bankaccounts.ui.utils.BankDiffCallback
import com.squareup.picasso.Picasso

class BankRecyclerViewAdapter: RecyclerView.Adapter<BankRecyclerViewAdapter.ViewHolder>() {
    private val banks= mutableListOf<Bank>()

    class ViewHolder(val binding: BankItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bank: Bank) {
            binding.tvName.text = bank.name
            binding.tvCode.text = bank.code
            Picasso.get()
                .load(bank.image)
                .resize(100, 50)
                .into(binding.imgLogo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BankItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(banks[position])
    }

    fun setBanks(banks: List<Bank>){
        val diffCallback = BankDiffCallback(this.banks, banks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.banks.clear()
        this.banks.addAll(banks)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = banks.size
}