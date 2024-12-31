package com.adventitous.matall.modules.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.SimpleApiResponse
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToCartViewModel  @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _addCartDetails = MutableLiveData<SimpleApiResponse>()
    val addCartDetails: MutableLiveData<SimpleApiResponse> get() = _addCartDetails


    fun addToCart(userId: Int, productId: Int, quality: Int){
        viewModelScope.launch {
            val response = repository.addToCart("addToCart",userId,productId, quality)
            _addCartDetails.postValue(response)

        }
    }

}