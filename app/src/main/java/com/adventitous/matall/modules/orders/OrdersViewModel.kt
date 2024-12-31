package com.adventitous.matall.modules.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.OrderData
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _orders = MutableLiveData<List<OrderData>>()
    val orders: LiveData<List<OrderData>> get() = _orders


    fun getOrders(userId: Int) {
        viewModelScope.launch {
            val response = repository.getOrders("getOrders",userId).data
            _orders.postValue(response)
        }
    }

}