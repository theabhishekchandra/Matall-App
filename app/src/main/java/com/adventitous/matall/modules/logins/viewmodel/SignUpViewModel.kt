package com.adventitous.matall.modules.logins.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.SimpleApiResponse
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel  @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _signUpResponse = MutableLiveData<SimpleApiResponse>()
    val signUpResponse: MutableLiveData<SimpleApiResponse> get() = _signUpResponse


    fun signUp(name: String, phone: String, email: String, password: String) {
        viewModelScope.launch {
            val response = repository.signUpUser("signUpUser", name, phone, email, password)
            _signUpResponse.value = response
        }
    }
}
