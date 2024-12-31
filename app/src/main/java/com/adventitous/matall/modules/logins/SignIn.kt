package com.adventitous.matall.modules.logins

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adventitous.matall.R
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentSignInBinding
import com.adventitous.matall.modules.logins.viewmodel.SignInViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignIn : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var appSharedPreferences: AppSharedPref
    @Inject
    lateinit var navigation: NavigationInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.btnSignIn.setOnClickListener {
            callApis()
        }
    }

    private fun isValid(): Boolean {
        return binding.loginphone.text.isNotBlank() && binding.loginPassword.text.isNotBlank()

    }

    private fun callApis() {
        if (isValid()){
            viewModel.signIn(binding.loginphone.text.toString(),binding.loginPassword.text.toString())  // This line will call signIn method in SignInViewModel and pass phone and password

        }
    }

    private fun observer() {
        viewModel.signInResponse.observe(viewLifecycleOwner){
            if (it.status == "success" && it.data != null){
                Log.d("NoData", "${it.data.mobile_number}")
                appSharedPreferences.setUserMobileNumber(it.data.mobile_number)
                appSharedPreferences.setUserId(it.data.user_id)
                appSharedPreferences.setUserName(it.data.name)
                appSharedPreferences.setEmail(it.data.email)
                appSharedPreferences.setUserLoggedIn(true)
                findNavController().navigate(R.id.homeFragment)

            }else{
                Toast.makeText(requireContext(),"Login Failed" , Toast.LENGTH_SHORT).show()
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