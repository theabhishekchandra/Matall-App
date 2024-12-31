package com.adventitous.matall.modules.general.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adventitous.matall.core.data.model.SubCategoryData
import com.adventitous.matall.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubCategoryViewModel @Inject constructor( private val repository: Repository) : ViewModel() {

    private val _subCategoryProduct = MutableLiveData<List<SubCategoryData>?>()
    val subCategoryProduct: MutableLiveData<List<SubCategoryData>?> get() = _subCategoryProduct

    fun getSubCategory(categoryName: String) {
        viewModelScope.launch {
            val response = repository.getAllSubCategoryByCategoryName("getAllSubCategoryByCategoryName",categoryName)
            _subCategoryProduct.postValue(response.data)
        }
    }
}