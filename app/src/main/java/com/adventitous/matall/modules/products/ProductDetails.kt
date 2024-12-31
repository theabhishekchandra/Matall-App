package com.adventitous.matall.modules.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adventitous.matall.R
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentProductDetailsBinding
import com.adventitous.matall.modules.cart.AddToCartViewModel
import com.adventitous.matall.modules.products.viewmodel.ProductDetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetails : Fragment(R.layout.fragment_product_details) {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var appSharedPreferences: AppSharedPref
    @Inject
    lateinit var navigation: NavigationInterface

    private val viewModel: ProductDetailsViewModel by viewModels()
    private val addToCartViewModel: AddToCartViewModel by viewModels()
    private var quantity = QuantityNo()

    private val productId by lazy {
        arguments?.getInt("productId", 0) ?: 0
    }

    private var userId: Int = 0 // Moved initialization

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Now safely access the appSharedPreferences after injection
        userId = appSharedPreferences.getUserId() ?: 0

        // Hide bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.GONE

        // Set up the toolbar
        binding.myCartToolbar.setNavigationIcon(R.drawable.arrow_back_24)
        binding.myCartToolbar.title = getString(R.string.product_details)
        binding.myCartToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        listener()
        observers()

        // Fetch product details
        viewModel.getProductDetails(productId)

        // Initialize quantity text view
        binding.quantity.text = quantity.count.toString()
    }

    private fun observers() {
        // Observe product details from the view model and populate the UI accordingly
        viewModel.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.productNames.text = it.product_name
                binding.productPrice.text = "Price: ${it.product_sell_price} Rs"
                binding.productDEC.text = it.product_description
                Picasso.get().load(it.product_img).into(binding.productIMG)
            }
        }
    }

    private fun listener() {
        // Set up click listeners
        binding.addItem.setOnClickListener {
            quantity.increment()
            binding.quantity.text = quantity.count.toString()
        }

        binding.removeItem.setOnClickListener {
            quantity.decrement()
            binding.quantity.text = quantity.count.toString()
        }

        binding.buyNow.setOnClickListener {
            // Implement buy now logic
            val bundle = Bundle().apply {
                putInt("productId", productId)
                putInt("quantity", quantity.count)
            }
            navigation.navigateWithBundle(R.id.action_productDetailsFragments_to_addressFragment, bundle)
        }

        binding.addToCart.setOnClickListener {
            userId.let { userID ->
                addToCartViewModel.addToCart(userID, productId, quantity.count)
                addToCartViewModel.addCartDetails.observe(viewLifecycleOwner) { response ->
                    val message = if (response.status == "success") {
                        "Successfully Added"
                    } else {
                        "Failed to Add to Cart"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private class QuantityNo {
            var count: Int = 1
                private set

            fun increment() {
                count++
            }

            fun decrement() {
                if (count > 1) {
                    count--
                } else {
                    count = 1
                }
            }
        }
    }
}