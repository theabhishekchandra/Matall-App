package com.adventitous.matall.modules.general.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.SimpleApiResponse
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _placeOrders = MutableLiveData<SimpleApiResponse>()
    val placeOrders: MutableLiveData<SimpleApiResponse> get() = _placeOrders

    private val _orderAllProductsInCart = MutableLiveData<SimpleApiResponse>()
    val orderAllProductsInCart: MutableLiveData<SimpleApiResponse> get() = _orderAllProductsInCart

    fun placeOrder(userId: Int, productId: Int, quantity: Int, name: String, phone: String, address: String) {
        viewModelScope.launch {
            try {
                val response = repository.placeOrder("placeOrder", userId, productId, quantity, name, phone, address)
                _placeOrders.postValue(response)
            } catch (e: HttpException) {
                _placeOrders.postValue(SimpleApiResponse(status = "error", data = "HTTP ${e.code()}: ${e.message()}"))
            } catch (e: Exception) {
                _placeOrders.postValue(SimpleApiResponse(status = "error", data = e.message ?: "An error occurred"))
            }
        }
    }

    fun orderAllProductsInCart(userId: Int, name: String, phone: String, address: String) {
        viewModelScope.launch {
            try {
                val response = repository.orderAllProductsInCart("orderAllProductsInCart", userId, name, phone, address)
                _orderAllProductsInCart.postValue(response)
            } catch (e: HttpException) {
                _orderAllProductsInCart.postValue(SimpleApiResponse(status = "error", data = "HTTP ${e.code()}: ${e.message()}"))
            } catch (e: Exception) {
                _orderAllProductsInCart.postValue(SimpleApiResponse(status = "error", data = e.message ?: "An error occurred"))
            }
        }
    }
}
