package com.adventitous.matall.modules.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.CartData
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _cartLiveData = MutableLiveData<List<CartData>>()
    val cartLiveData: LiveData<List<CartData>> get() = _cartLiveData

    private val _deleteCartLiveData = MutableLiveData<String>()
    val deleteCartLiveData: LiveData<String> get() = _deleteCartLiveData

    private val _updateCartLiveData = MutableLiveData<String>()
    val updateCartLiveData: LiveData<String> get() = _updateCartLiveData


    fun getCartData(userId: Int) {
        viewModelScope.launch {
            val response = repository.getCartItems("getCartItems", userId = userId)
            _cartLiveData.postValue(response.data)
        }
    }
    fun deleteCartItem(cartId: Int) {
        viewModelScope.launch {
            val response = repository.deleteCartItem("deleteCartItem", cartId = cartId)
            _deleteCartLiveData.postValue(response.status)
        }

    }
    fun updateQuantity(cartId: Int, quantity: Int) {
        viewModelScope.launch {
            val response = repository.updateCartItemQuantity("updateCartItemQuantity",cartId,quantity)
            _updateCartLiveData.postValue(response.status)
        }
    }

}