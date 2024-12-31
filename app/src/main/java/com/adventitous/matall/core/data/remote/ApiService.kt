package com.adventitous.matall.core.data.remote

import com.adventitous.matall.core.data.model.*
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("api.php")
    suspend fun signUpUser(
        @Field("action") action: String,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SimpleApiResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun updateUserProfile(
        @Field("action") action: String,
        @Field("user_id") userId: Int,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("address") address: String
    ): SimpleApiResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun requestPasswordReset(
        @Field("action") action: String,
        @Field("email") email: String
    ): SimpleApiResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun authenticateUser(
        @Field("action") action: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): UserResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun cancelOrder(
        @Field("action") action: String,
        @Field("order_id") orderId: Int,
        @Field("user_id") userId: Int
    ): SimpleApiResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun updateCartItemQuantity(
        @Field("action") action: String,
        @Field("cart_id") cartId: Int,
        @Field("quantity") quantity: Int
    ): SimpleApiResponse

    @GET("api.php")
    suspend fun getProductsBySubCategory(
        @Query("action") action: String,
        @Query("sub_category") subCategory: String
    ): AllProductResponse

    @GET("api.php")
    suspend fun placeOrder(
        @Query("action") action: String,
        @Query("user_id") userId: Int,
        @Query("product_id") productId: Int,
        @Query("quantity") quantity: Int,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("shipping_address") shippingAddress: String
    ): SimpleApiResponse

    @GET("api.php")
    suspend fun getAllProducts(
        @Query("action") action: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): AllProductResponse

    @GET("api.php")
    suspend fun getProductDetails(
        @Query("action") action: String,
        @Query("product_id") productId: Int
    ): SingleProductResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun addToCart(
        @Field("action") action: String,
        @Field("user_id") userId: Int,
        @Field("product_id") productId: Int,
        @Field("quantity") quantity: Int
    ): SimpleApiResponse

    @GET("api.php")
    suspend fun getCartItems(
        @Query("action") action: String,
        @Query("user_id") userId: Int
    ): CartResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun deleteCartItem(
        @Field("action") action: String,
        @Field("cart_id") cardId: Int
    ): SimpleApiResponse

    @GET("api.php")
    suspend fun getOrders(
        @Query("action") action: String,
        @Query("user_id") userId: Int
    ): OrderResponse

    @GET("api.php")
    suspend fun getAllCategories(
        @Query("action") action: String
    ): CategoryResponse

    @GET("api.php")
    suspend fun getAllSubCategoryByCategoryName(
        @Query("action") action: String,
        @Query("category") category: String
    ): SubCategoryResponse

    @GET("api.php")
    suspend fun getUserDetails(
        @Query("action") action: String,
        @Query("mobile_number") mobile_number: String
    ): UserResponse

    @FormUrlEncoded
    @POST("api.php")
    suspend fun orderAllProductsInCart(
        @Field("action") action: String,
        @Field("user_id") userId: Int,
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("shipping_address") shippingAddress: String
    ): SimpleApiResponse
}
