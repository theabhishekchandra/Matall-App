package com.adventitous.matall.modules.logins.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.repository.Repository
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class FirebaseViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var phoneNumber: String

    private val _authenticationState = MutableLiveData<Boolean>()
    val authenticationState: LiveData<Boolean> = _authenticationState

    private val _otpSendState = MutableLiveData<Boolean>()
    val otpSendState: LiveData<Boolean> = _otpSendState

    private val _mobileNumber = MutableLiveData<String>()
    val mobileNumber: LiveData<String> = _mobileNumber

    private val _toastMessage: MutableLiveData<String?> = MutableLiveData()
    val toastMessage: MutableLiveData<String?> = _toastMessage

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> = _loader

    fun setMobileNumber(number: String) {
        _mobileNumber.value = number
    }


    fun signOut() {
        auth.signOut()
        _authenticationState.value = false
        _otpSendState.value = false
        _loader.postValue(false)
    }

    fun resendVerificationCode(activity: Activity) {
        _loader.postValue(true)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(getPhoneAuthCallbacks())
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneNumber(phoneNumber: String, activity: Activity) {
        viewModelScope.launch {
            startPhoneNumberVerification(phoneNumber, activity)
        }
    }
    fun getMobileNumber(): String {
        return auth.currentUser?.phoneNumber.toString()
    }

    fun verifyPhoneNumberWithCode(code: String) {
        if (!::storedVerificationId.isInitialized) {
            // Handle case where verification id is not yet initialized
            _toastMessage.postValue("Verification ID not initialized. Please retry.")
            return
        }

        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    fun cancelAuthentication() {
        if (auth.currentUser != null) {
            auth.signOut()
        }
        _authenticationState.value = false
        _otpSendState.value = false
        _loader.postValue(false)
    }

    private fun startPhoneNumberVerification(phoneNumber: String, activity: Activity) {
        setMobileNumber(phoneNumber)
        this.phoneNumber = "+91$phoneNumber"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(this.phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(getPhoneAuthCallbacks())
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun getPhoneAuthCallbacks(): PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                handleVerificationFailure(e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                storedVerificationId = verificationId
                resendToken = token
                _otpSendState.postValue(true)
                _loader.postValue(false)
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _authenticationState.postValue(true)
                _loader.postValue(false)
                _toastMessage.postValue(null)
            } else {
                handleSignInFailure(task.exception)
            }
        }
    }

    private fun handleVerificationFailure(exception: FirebaseException) {
        _authenticationState.postValue(false)
        _otpSendState.postValue(false)
        _toastMessage.postValue("Failed to send OTP. Please try again.")
        when (exception) {
            is FirebaseAuthInvalidCredentialsException -> {
                Log.d(TAG, "Invalid request: $exception")
            }
            is FirebaseTooManyRequestsException -> {
                Log.d(TAG, "SMS quota for the project has been exceeded.")
            }
            is FirebaseAuthMissingActivityForRecaptchaException -> {
                Log.d(TAG, "reCAPTCHA verification attempted with null Activity.")
            }
        }
    }

    private fun handleSignInFailure(exception: Exception?) {
        _toastMessage.postValue("Failed to login. Please try again.")
        if (exception is FirebaseAuthInvalidCredentialsException) {
            _toastMessage.postValue("Wrong OTP entered")
            Log.d(TAG, "Wrong OTP entered")
        }
        _otpSendState.postValue(false)
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    private companion object {
        private const val TAG = "FirebaseAuthViewModel"
    }
}
