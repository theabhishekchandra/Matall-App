package com.adventitous.matall.modules.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.OrderData
import com.adventitous.matall.core.data.model.SimpleApiResponse
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _cancelOrder = MutableLiveData<SimpleApiResponse>()
    val cancelOrder: LiveData<SimpleApiResponse> get() = _cancelOrder

    fun cancelOrder(orderId: Int,userId: Int) {
        viewModelScope.launch {
            val response = repository.cancelOrder("cancelOrder", orderId, userId)
            _cancelOrder.value = response
        }
    }

}