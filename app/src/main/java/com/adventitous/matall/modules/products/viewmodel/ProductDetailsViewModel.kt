package com.adventitous.matall.modules.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.Product
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product


    fun getProductDetails(productId: Int) {
        viewModelScope.launch {
            val response = repository.getProductDetails("getProductDetails", productId)
            _product.postValue(response.data)
        }
    }


}