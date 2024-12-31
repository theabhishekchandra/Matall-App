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
class ProductsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _products = MutableLiveData<List<Product>?>()
    val products: LiveData<List<Product>?> get() = _products

    private val _productsSub = MutableLiveData<List<Product>?>()
    val productsSub: LiveData<List<Product>?> get() = _productsSub

    fun loadProducts(page: Int) {
        viewModelScope.launch {
            val response = repository.getAllProducts("getAllProducts", page, 20)
            _products.postValue(response.data.data)
        }
    }

    fun loadMoreProducts(page: Int) {
        viewModelScope.launch {
            val response = repository.getAllProducts("getAllProducts", page, 20)
            val currentProducts = _products.value.orEmpty().toMutableList()
            currentProducts.addAll(response.data.data)
            _products.postValue(currentProducts)
        }
    }

    fun getProductsBySubCategory(subCategoryName: String,page: Int) {
        viewModelScope.launch {
            val response = repository.getProductsBySubCategory("getProductsBySubCategory", subCategoryName,page,20)
            _productsSub.postValue(response.data.data)
        }
    }

    fun loadMoreProductsBySubCategory(subCategoryName: String, page: Int) {
        viewModelScope.launch {
            val response = repository.getProductsBySubCategory("getProductsBySubCategory", subCategoryName, page, 20)
            val currentProducts = _productsSub.value.orEmpty().toMutableList()
            currentProducts.addAll(response.data.data)
            _productsSub.postValue(currentProducts)
        }
    }
}
