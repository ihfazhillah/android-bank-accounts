package com.ihfazh.bankaccounts.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ihfazh.bankaccounts.R
import com.ihfazh.bankaccounts.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private lateinit var binding: FragmentSlideshowBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
                ViewModelProvider(this).get(SlideshowViewModel::class.java)

        binding = FragmentSlideshowBinding.inflate(layoutInflater)

        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_nav_slideshow_to_bankAccountCreateFragment)
        }



        return binding.root
    }
}