package com.adventitous.matall.modules.logins

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adventitous.matall.R
import com.adventitous.matall.databinding.FragmentSignUpBinding
import com.adventitous.matall.modules.logins.viewmodel.SignUpViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUp : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            if (isValidation()) {
                viewModel.signUp(binding.regName.text.toString(), binding.regPhone.text.toString(), binding.regEmail.text.toString(), binding.regPassword.text.toString())
            }
        }
        binding.signLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.signIn)
        }

        viewModel.signUpResponse.observe(viewLifecycleOwner){
            if(it.status == "success"){
                //Redirect to Home Fragment
                findNavController().navigate(R.id.homeFragment)

            }else{
                //Show error message
                Toast.makeText(requireContext(),"Failed to SignUp", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun isValidation() : Boolean{
        return  binding.regName.text.isNotEmpty()&&
                binding.regEmail.text.isNotEmpty() &&
                binding.regPassword.text.isNotEmpty()&&
                binding.regPhone.text.isNotEmpty() &&
                binding.regPassword.text == binding.regConformPass.text
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