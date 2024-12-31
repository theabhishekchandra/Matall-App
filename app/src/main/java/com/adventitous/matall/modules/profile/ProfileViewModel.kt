package com.adventitous.matall.modules.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.adventitous.matall.core.data.model.SimpleApiResponse
import com.adventitous.matall.core.data.model.UserResponse
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun signUpUser(action: String, name: String, phone: String, email: String, password: String) = liveData(
        Dispatchers.IO) {
        try {
            val response = repository.signUpUser(action, name, phone, email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure<SimpleApiResponse>(e))
        }
    }

    fun getUserDetails(number: String) = liveData(Dispatchers.IO) {
        try {
            val response = repository.getUserDetails("getUserDetails", number)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure<UserResponse>(e))
        }
    }
}