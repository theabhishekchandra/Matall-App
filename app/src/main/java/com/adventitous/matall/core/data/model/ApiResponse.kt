package com.adventitous.matall.core.data.model

data class SimpleApiResponse(
    val status: String,
    val data: Any
)

data class UserResponse(
    val status: String,
    val data: UserData?
)

data class UserData(
    val user_id: Int,
    val name: String,
    val mobile_number: String,
    val email: String,
    val address: Any?,
    val user_img: Any?
)

data class SingleProductResponse(
    val status: String,
    val data: Product
)

data class AllProductResponse(
    val status: String,
    val data: AllData
)

data class AllData(
    val limit: Int,
    val page: Int,
    val total: Int,
    val data: List<Product>
)

data class Product(
    val product_id: Int?,
    val product_name: String,
    val product_category: String,
    val product_sub_category: String,
    val product_brand: String,
    val product_mrp_price: String,
    val product_sell_price: String,
    val product_img: String,
    val product_code: String,
    val product_description: String,
    val discount_percentage: Int,
    val discount_amount: String?,
    val sku: String,
    val gst_rate: Int,
    val available_quantity: String?
)

data class CartResponse(
    val status: String,
    val data: List<CartData>
)

data class CartData(
    val cart_id: Int,
    val product_id: Int,
    val product_img: String,
    val product_name: String,
    val product_sell_price: String,
    val quantity: Int,
    val total_price: String,
    val user_id: Int
)

data class CategoryResponse(
    val status: String,
    val data: List<CategoryData>
)

data class CategoryData(
    val category_id: Int,
    val category_img: String,
    val category_name: String
)

data class SubCategoryResponse(
    val status: String,
    val data: List<SubCategoryData>?
)

data class SubCategoryData(
    val sub_category_id: Int,
    val sub_category_name: String,
    val category_id: Int,
    val sub_category_img: String
)

data class OrderResponse(
    val status: String,
    val data: List<OrderData>
)

data class OrderData(
    val order_id: Int,
    val user_id: Int,
    val order_date: String,
    val total_amount: String,
    val status: String,
    val order_status: String,
    val payment_method: String,
    val payment_status: String,
    val shipping_address: String,
    val shipping_method: String,
    val shipping_cost: String,
    val quantity: String,
    val product_name: String,
    val product_img: String
)
