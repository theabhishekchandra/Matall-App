package com.adventitous.matall.modules.logins

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.adventitous.matall.R
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentOTPManageBinding
import com.adventitous.matall.modules.logins.viewmodel.FirebaseViewModel
import com.adventitous.matall.modules.logins.viewmodel.OTPManageViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class OTPManage : Fragment() {

    private lateinit var binding: FragmentOTPManageBinding
    private val viewModel: OTPManageViewModel by viewModels()
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    @Inject
    lateinit var appSharedPreferences: AppSharedPref

    private lateinit var navigation: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPManageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation = findNavController()

        binding.btnVerifyOpt.setOnClickListener {
            val code = binding.otp.text.toString()
            if (code.length == 6) {
                navigateToHomeFragment()
                firebaseViewModel.verifyPhoneNumberWithCode(code)
            }
        }

        firebaseViewModel.authenticationState.observe(viewLifecycleOwner) {
            if (it) {
                navigateToHomeFragment()
            } else {
                // Show error message
            }
        }
    }

    private fun navigateToHomeFragment() {
        navigation.popBackStack(R.id.OTPManage, true)
        navigation.navigate(R.id.homeFragment)
    }

    override fun onResume() {
        super.onResume()
        // Show bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.GONE
    }
}