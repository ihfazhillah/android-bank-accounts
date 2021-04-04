package com.ihfazh.bankaccounts.ui.slideshow

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.databinding.BankAccountItemBinding
import com.squareup.picasso.Picasso

class BankAccountRecyclerViewAdapter(val context: Context) :
        RecyclerView.Adapter<BankAccountRecyclerViewAdapter.ViewHolder>() {
    private val banks = mutableListOf<BankAccount>()

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
        val view = ViewHolder(binding)
        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(banks[position])
        holder.binding.btnMore.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.binding.btnMore)
            popupMenu.menuInflater.inflate(R.menu.account_detail_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                setPopupMenuListener(it, banks[position])
            }
            popupMenu.show()
        }
    }

    private fun setPopupMenuListener(menuItem: MenuItem, bankAccount: BankAccount): Boolean {
        val clipBoard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val text = """
                    ${bankAccount.bank.name} (${bankAccount.bank.code}),
                    
                    ${bankAccount.account_number}
                    atas nama ${bankAccount.account_holder}
                """.trimIndent()
        when (menuItem.itemId) {
            R.id.action_edit -> {
            }
            R.id.action_copy_all -> {
                clipBoard.setPrimaryClip(ClipData.newPlainText("bank account", text))
                showToast("Bank Account Copied")
            }
            R.id.action_copy_number -> {
                clipBoard.setPrimaryClip(ClipData.newPlainText("bank number", bankAccount.account_number))
                showToast("Bank Number Copied")
            }
            R.id.action_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, "Share Kontak Bank")
                context.startActivity(shareIntent)
            }
            R.id.action_delete -> {
            }
        }
        return true
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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