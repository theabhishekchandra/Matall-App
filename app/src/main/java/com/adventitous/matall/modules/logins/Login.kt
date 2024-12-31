package com.adventitous.matall.modules.logins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.adventitous.matall.R
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentLoginBinding
import com.adventitous.matall.modules.logins.viewmodel.FirebaseViewModel
import com.adventitous.matall.modules.logins.viewmodel.LoginViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Login : Fragment() {
    private lateinit var binding :FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val firebaseViewModel: FirebaseViewModel by viewModels()
    @Inject
    lateinit var appSharedPreferences: AppSharedPref
    @Inject
    lateinit var navigation: NavigationInterface


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.GONE

        binding.loginButton.setOnClickListener {
            val mobileNumber = binding.mobileNumber.text.toString()
            if(mobileNumber.length == 10){
                firebaseViewModel.setMobileNumber(mobileNumber)
                firebaseViewModel.verifyPhoneNumber(mobileNumber,requireActivity())
//                appSharedPreferences.setUserMobileNumber(mobileNumber)
            }else{
                binding.mobileNumber.error = "Please enter a valid 10 digit number"
            }
        }
        firebaseViewModel.otpSendState.observe(viewLifecycleOwner){
            if(it){
                navigation.navigateTo(R.id.action_login_to_OTPManage)
//                navigation.navigateTo(R.id.action_login_to_OTPManage)
            }
        }

    }

    companion object {

    }
    override fun onResume() {
        super.onResume()
        // Show bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.GONE
    }
}