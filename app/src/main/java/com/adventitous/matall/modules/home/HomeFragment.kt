package com.adventitous.matall.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adventitous.matall.R
import com.adventitous.matall.core.data.model.CategoryData
import com.adventitous.matall.core.data.model.Product
import com.adventitous.matall.core.genericadapter.GenericAdapter
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentHomeBinding
import com.adventitous.matall.modules.profile.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GenericAdapter<Product>

    @Inject
    lateinit var navigation: NavigationInterface
    @Inject
    lateinit var appSharedPreferences: AppSharedPref
    lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        observer()

        binding.homeCategoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularRec.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recommendedRecycle.layoutManager = GridLayoutManager(requireContext(), 2)


    }

    private fun observer() {


        //Products
        viewModel.products.observe(viewLifecycleOwner) { result ->
            adapter = GenericAdapter(result, R.layout.item_popular, { view, item ->
                // Binding logic
                val productName = view.findViewById<TextView>(R.id.product_name_xml)
                val productPrice = view.findViewById<TextView>(R.id.product_price_xml)
                val productImage = view.findViewById<ImageView>(R.id.product_img_xml)

                Picasso.get().load(item.product_img).into(productImage)
                productName.text = item.product_name
                productPrice.text = "Price: ${item.product_sell_price} Rs"
            }, { product ->
                // Handle product item click here
                val bundle = Bundle()
                bundle.putInt("productId", product.product_id ?: 0)
                navigation.getNavController().navigate(R.id.action_homeFragment_to_productDetailsFragments, bundle)
            })
            binding.popularRec.adapter = adapter
        }

        // Recommended products
        viewModel.productsForRec.observe(viewLifecycleOwner) { result ->
            adapter = GenericAdapter(result, R.layout.item_popular, { view, item ->
                // Binding logic
                val productName = view.findViewById<TextView>(R.id.product_name_xml)
                val productPrice = view.findViewById<TextView>(R.id.product_price_xml)
                val productImage = view.findViewById<ImageView>(R.id.product_img_xml)

                productName.text = item.product_name
                productPrice.text = "Price: ${item.product_sell_price} Rs"
                Picasso.get().load(item.product_img).into(productImage)
            }, { product ->
                // Handle product item click here
                val bundle = Bundle()
                bundle.putInt("productId", product.product_id ?: 0)
                navigation.getNavController().navigate(R.id.action_homeFragment_to_productDetailsFragments, bundle)
            })
            binding.recommendedRecycle.adapter = adapter
        }

        // Categories
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val categoryAdapter = GenericAdapter(categories, R.layout.item_category, { view, item ->
                val categoryImage = view.findViewById<ImageView>(R.id.home_cat_item_img)
                val categoryName = view.findViewById<TextView>(R.id.home_cat_item_name)

                Picasso.get().load(item.category_img).into(categoryImage)
                categoryName.text = item.category_name
            }, { category ->
                // Handle category item click here
                val bundle = Bundle()
                bundle.putString("categoryName", category.category_name ?: "")
                navigation.getNavController().navigate(R.id.action_homeFragment_to_subCategory, bundle)
            })
            binding.homeCategoryRecyclerView.adapter = categoryAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        // Show bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.VISIBLE
    }
}
