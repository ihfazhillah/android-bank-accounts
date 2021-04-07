package com.ihfazh.bankaccounts.ui.bank_account_create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.databinding.ActivityCreateAccountActivityBinding

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://stackoverflow.com/questions/58686104/why-does-my-navcontroller-cannot-find-an-id-that-i-already-have
        binding = ActivityCreateAccountActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.stepper.setupWithNavController(
            findNavController(R.id.nav_host_stepper)
        )
//        binding.stepper.goToNextStep()
    }
}