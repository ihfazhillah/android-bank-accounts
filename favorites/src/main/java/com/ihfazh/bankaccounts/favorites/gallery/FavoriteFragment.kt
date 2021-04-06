package com.ihfazh.bankaccounts.favorites.gallery

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.core.domain.data.BankAccount
import com.ihfazh.bankaccounts.di.FavoriteModuleDependencies
import com.ihfazh.bankaccounts.favorites.databinding.FragmentGalleryBinding
import com.ihfazh.bankaccounts.ui.bankaccounts.BankAccountRecyclerViewAdapter
import com.ihfazh.bankaccounts.ui.bankaccounts.SlideshowFragmentDirections
import com.ihfazh.bankaccounts.ui.utils.IBankAccountItemListener
import dagger.hilt.android.EntryPointAccessors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteFragment : Fragment(), IBankAccountItemListener {

    private lateinit var binding: FragmentGalleryBinding

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels {
        factory
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
                .context(context)
                .appDependencies(
                        EntryPointAccessors.fromApplication(
                                context.applicationContext,
                                FavoriteModuleDependencies::class.java
                        )
                )
                .build()
                .inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        val root = binding.root
        val rvAdapter = BankAccountRecyclerViewAdapter().apply {
            itemListener = this@FavoriteFragment
        }

        viewModel.favoritedBankAccounts.observe(viewLifecycleOwner) {
            rvAdapter.setBanks(it)
        }
        binding.rvBankAccountItems.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
        }
        return root
    }

    override fun onMoreClick(bank: BankAccount, btn: View) {
        val popupMenu = PopupMenu(context, btn)
        popupMenu.menuInflater.inflate(R.menu.account_detail_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            setPopupMenuListener(it, bank)
        }
        popupMenu.show()
    }

    private fun setPopupMenuListener(menuItem: MenuItem, bankAccount: BankAccount): Boolean {
        when (menuItem.itemId) {
            R.id.action_edit -> {
                val action =
                        SlideshowFragmentDirections.actionNavSlideshowToBankAccountCreateFragment(
                                bankAccount
                        )
                findNavController().navigate(action)
            }
            R.id.action_delete -> {
                val builder = AlertDialog.Builder(context)
                        .setMessage("Anda yakin akan menghapus akun: ${bankAccount.account_holder} ?")
                        .setPositiveButton("Yes") { _, _ ->
                            val disposable = viewModel.deleteBankAccount(bankAccount)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe()
                            compositeDisposable.add(disposable)

                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.cancel()
                        }
                val dialog = builder.create()
                dialog.show()
            }
        }
        return true
    }

    override fun onCopyClick(bankAccount: BankAccount, btn: View) {
        val popupMenu = PopupMenu(context, btn)
        popupMenu.menuInflater.inflate(R.menu.account_detail_share_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            val text = """
                    ${bankAccount.bank.name} (${bankAccount.bank.code}),
                    
                    ${bankAccount.account_number}
                    atas nama ${bankAccount.account_holder}
                """.trimIndent()
            val clipBoard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            when (it.itemId) {
                R.id.action_copy_all -> {
                    clipBoard.setPrimaryClip(ClipData.newPlainText("bank account", text))
                    showToast("Bank Account Copied")
                }
                R.id.action_copy_number -> {
                    clipBoard.setPrimaryClip(
                            ClipData.newPlainText(
                                    "bank number",
                                    bankAccount.account_number
                            )
                    )
                    showToast("Bank Number Copied")
                }
            }

            true
        }
        popupMenu.show()

    }

    override fun onShareClick(bankAccount: BankAccount, btn: View) {
        val text = """
                    ${bankAccount.bank.name} (${bankAccount.bank.code}),
                    
                    ${bankAccount.account_number}
                    atas nama ${bankAccount.account_holder}
                """.trimIndent()
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Kontak Bank")
        startActivity(shareIntent)
    }

    override fun onFavoriteClick(bankAccount: BankAccount, btn: View, position: Int) {
        val disposable = viewModel.toggleFavorite(bankAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
        compositeDisposable.add(disposable)
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
}