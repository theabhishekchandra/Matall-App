package com.adventitous.matall.modules.logins.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.UserResponse
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel  @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _signInResponse = MutableLiveData<UserResponse>()
    val signInResponse: LiveData<UserResponse> get() = _signInResponse

    fun signIn(number: String, password: String) {
        viewModelScope.launch {
            val response = repository.loginUser("authenticateUser", number, password)
            _signInResponse.value = response
        }
    }
}