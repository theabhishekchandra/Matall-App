package com.adventitous.matall.modules.logins.viewmodel

import androidx.lifecycle.ViewModel
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OTPManageViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

}