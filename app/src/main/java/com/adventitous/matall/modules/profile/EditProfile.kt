package com.adventitous.matall.modules.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.adventitous.matall.R
import com.adventitous.matall.databinding.FragmentEditProfileBinding
import com.adventitous.matall.modules.logins.viewmodel.SignInViewModel
import com.adventitous.matall.modules.profile.viewmodel.EditProfileViewModel


class EditProfile : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

}