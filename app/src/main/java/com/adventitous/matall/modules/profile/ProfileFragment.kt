package com.adventitous.matall.modules.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.adventitous.matall.R
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentProfileBinding
import com.adventitous.matall.modules.logins.viewmodel.FirebaseViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val firebaseViewModel: FirebaseViewModel by viewModels()

    @Inject
    lateinit var appSharedPreferences: AppSharedPref
    @Inject
    lateinit var navigation: NavigationInterface

    // Moved initialization to onViewCreated
    private lateinit var userNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize userNumber here
        userNumber = appSharedPreferences.getUserMobileNumber()

        accessData()
        initialization()
    }

    private fun initialization() {
        binding.profileLogoutBtn.setOnClickListener {
            appSharedPreferences.resetUserInfo()
            navigation.navigateTo(R.id.signIn)
//            firebaseViewModel.signOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        // Show bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.VISIBLE
    }

    private fun accessData() {
        Log.d("NoData", "${appSharedPreferences.getUserMobileNumber()}")
        userNumber.let {
            viewModel.getUserDetails(appSharedPreferences.getUserMobileNumber()).observe(viewLifecycleOwner) { result ->
                result.onSuccess { userResponse ->
                    userResponse.data?.let { userData ->
                        binding.profileUsername.text = userData.name
                        binding.profileUserPhone.text = userData.mobile_number
                        binding.profileUserEmail.text = userData.email
                        binding.profileUserAddress.text =
                            userData.address?.toString() ?: "No Address"
                    }
                }.onFailure {
                    // Handle the failure case, e.g., show an error message
                    binding.profileUsername.text = "Failed to load user details"
                }
            }
        }
    }

    private fun recyclerView() {
        // Implement your RecyclerView setup here
    }
}