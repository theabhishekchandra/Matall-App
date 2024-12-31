package com.adventitous.matall.modules.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.CategoryData
import com.adventitous.matall.core.data.model.Product
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _categories = MutableLiveData<List<CategoryData>>()
    val categories: LiveData<List<CategoryData>> get() = _categories

    private val _productsForRec = MutableLiveData<List<Product>>()
    val productsForRec: LiveData<List<Product>> get() = _productsForRec

//    private val _userId = MutableLiveData<Int>()
//    val userId: LiveData<Int> get() = _userId


    init {
        getProduct()
        getProductForRec()
        getAllProductsCategory()
    }

//    fun getUserId (number: String) {
//        viewModelScope.launch {
//            val response = repository.getUserDetails("getUserDetails",number )
//            _userId.value = response.data?.user_id
//        }
//    }




    private fun getProduct(){
        viewModelScope.launch {
            val response = repository.getAllProducts("getAllProducts", page = Random.nextInt(1,10), 20)
            _products.postValue(response.data.data)
        }
    }
    private fun getProductForRec(){
        viewModelScope.launch {
            val response = repository.getAllProducts("getAllProducts", page = Random.nextInt(1,10), 20)
            _productsForRec.postValue(response.data.data)
        }
    }

    private fun getAllProductsCategory(){
        viewModelScope.launch {
            val response = repository.getAllCategories("getAllCategories")
            _categories.postValue(response.data)
        }
    }
}