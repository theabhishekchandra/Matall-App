package com.adventitous.matall.core.data.repository

import com.adventitous.matall.core.data.model.*
import com.adventitous.matall.core.data.remote.ApiService

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun signUpUser(action: String, name: String, phone: String, email: String, password: String): SimpleApiResponse {
        return apiService.signUpUser(action, name, phone, email, password)
    }

    suspend fun updateUserProfile(action: String, userId: Int, name: String, phone: String, email: String, address: String): SimpleApiResponse {
        return apiService.updateUserProfile(action, userId, name, phone, email, address)
    }

    suspend fun loginUser(action: String, email: String, password: String): UserResponse {
        return apiService.authenticateUser(action, email, password)
    }

    suspend fun cancelOrder(action: String, orderId: Int, userId: Int): SimpleApiResponse {
        return apiService.cancelOrder(action, orderId, userId)
    }

    suspend fun updateCartItemQuantity(action: String, cartId: Int, quantity: Int): SimpleApiResponse {
        return apiService.updateCartItemQuantity(action, cartId, quantity)
    }

    suspend fun getProductsBySubCategory(action: String, subCategory: String, page:Int,limit: Int): AllProductResponse {
        return apiService.getProductsBySubCategory(action, subCategory)
    }

    suspend fun placeOrder(action: String, userId: Int, productId: Int, quantity: Int, name: String, phone: String, shippingAddress: String): SimpleApiResponse {
        return apiService.placeOrder(action, userId, productId, quantity, name, phone, shippingAddress)
    }

    suspend fun getAllProducts(action: String, page: Int, limit: Int): AllProductResponse {
        return apiService.getAllProducts(action, page, limit)
    }

    suspend fun getProductDetails(action: String, productId: Int): SingleProductResponse {
        return apiService.getProductDetails(action, productId)
    }

    suspend fun addToCart(action: String, userId: Int, productId: Int, quantity: Int): SimpleApiResponse {
        return apiService.addToCart(action, userId, productId, quantity)
    }

    suspend fun getCartItems(action: String, userId: Int): CartResponse {
        return apiService.getCartItems(action, userId)
    }

    suspend fun getOrders(action: String, userId: Int): OrderResponse {
        return apiService.getOrders(action, userId)
    }

    suspend fun getAllCategories(action: String): CategoryResponse {
        return apiService.getAllCategories(action)
    }

    suspend fun getAllSubCategoryByCategoryName(action: String, category: String): SubCategoryResponse {
        return apiService.getAllSubCategoryByCategoryName(action, category)
    }

    suspend fun getUserDetails(action: String, email: String): UserResponse {
        return apiService.getUserDetails(action, email)
    }

    suspend fun orderAllProductsInCart(action: String, userId: Int, name: String, phone: String, shippingAddress: String): SimpleApiResponse {
        return apiService.orderAllProductsInCart(action, userId, name, phone, shippingAddress)
    }

    suspend fun deleteCartItem(action: String, cartId: Int): SimpleApiResponse {
        return apiService.deleteCartItem(action, cartId)
    }
}
