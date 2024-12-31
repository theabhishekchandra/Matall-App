package com.adventitous.matall.modules.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
}