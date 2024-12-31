package com.adventitous.matall.modules.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.adventitous.matall.R
import com.adventitous.matall.core.data.model.CartData
import com.adventitous.matall.core.genericadapter.GenericAdapter
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentCartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: GenericAdapter<CartData>
    @Inject
    lateinit var navigation: NavigationInterface
    @Inject
    lateinit var appSharedPreferences: AppSharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = appSharedPreferences.getUserId() ?: 0

        binding.buyNow.setOnClickListener {
            navigation.navigateTo(R.id.action_cartFragment_to_addressFragement)
        }

        binding.cartRec.layoutManager = LinearLayoutManager(requireContext())
        userId.let {
            viewModel.getCartData(it)
        }
        viewModel.cartLiveData.observe(viewLifecycleOwner) { result ->
            if (result.isNullOrEmpty()) {
                binding.linLayout5.visibility = View.GONE
            } else {
                binding.linLayout5.visibility = View.VISIBLE
            }
            adapter = GenericAdapter(result, R.layout.item_cart, { view, item ->
                // Binding logic for each item in the cart
                val cartName = view.findViewById<TextView>(R.id.cart_product_name)
                val cartQuality = view.findViewById<TextView>(R.id.cart_product_quantity)
                val cartPrice = view.findViewById<TextView>(R.id.cart_product_price)
                val cartImage = view.findViewById<ImageView>(R.id.cart_product_img)
                val cartImageDL = view.findViewById<ImageView>(R.id.cart_delete)
                val cartItemRM = view.findViewById<AppCompatButton>(R.id.cart_remove_item)
                val cartItemAD = view.findViewById<AppCompatButton>(R.id.cart_add_item)

                // Set click listeners for item interactions
                cartItemAD.setOnClickListener {
                    val updatedQuantity = item.quantity + 1
                    cartQuality.text = updatedQuantity.toString()
                    updateQuantity(item.cart_id, updatedQuantity)
                }

                cartItemRM.setOnClickListener {
                    if (item.quantity > 0) {
                        val updatedQuantity = item.quantity - 1
                        cartQuality.text = updatedQuantity.toString()
                        updateQuantity(item.cart_id, updatedQuantity)
                    } else {
                        Toast.makeText(requireContext(), "Quantity cannot be less than zero", Toast.LENGTH_SHORT).show()
                    }
                }

                cartImageDL.setOnClickListener {
                    deleteCart(item.cart_id)
                }

                // Populate views with data from CartData object
                cartName.text = item.product_name
                cartQuality.text = item.quantity.toString()
                cartPrice.text = "Price: ${item.product_sell_price} Rs"
                Picasso.get().load(item.product_img).into(cartImage)

            }, null)
            binding.cartRec.adapter = adapter

            var totalPrice = 0.0
            result.forEach {
                totalPrice += it.total_price.toDouble()
            }
            binding.totalPriceCart.text = "Total Price: ₹ ${totalPrice.toInt()}"

        }
    }

    private fun updateQuantity(cartId: Int, quantity: Int) {
        viewModel.updateQuantity(cartId, quantity)
        viewModel.updateCartLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Update: $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteCart(cartId: Int) {
        viewModel.deleteCartItem(cartId)
        viewModel.deleteCartLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Delete: $it", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.VISIBLE
    }
}
