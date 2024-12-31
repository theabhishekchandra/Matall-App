package com.adventitous.matall.modules.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adventitous.matall.R
import com.adventitous.matall.core.data.model.Product
import com.adventitous.matall.core.genericadapter.GenericAdapter
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPrefInterface
import com.adventitous.matall.databinding.FragmentProductsBinding
import com.adventitous.matall.modules.products.viewmodel.ProductsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var binding: FragmentProductsBinding
    private lateinit var adapterData: GenericAdapter<Product>
    private val subCategoryName by lazy {
        arguments?.getString("subCategoryName", "") ?: ""
    }
    private var currentPage = 1
    private val pageSize = 20

    @Inject
    lateinit var appSharedPreferences: AppSharedPrefInterface
    @Inject
    lateinit var navigation: NavigationInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recview.layoutManager = GridLayoutManager(requireContext(), 2)

        if (subCategoryName.isEmpty()) {
            viewModel.loadProducts(currentPage)
            observeProducts(viewModel.products)
        } else {
            viewModel.getProductsBySubCategory(subCategoryName,currentPage)
            observeProducts(viewModel.productsSub)
        }

        binding.recview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0 && totalItemCount >= pageSize) {
                    currentPage++
                    if (subCategoryName.isEmpty()) {
                        viewModel.loadMoreProducts(currentPage)
                    } else {
                        viewModel.loadMoreProductsBySubCategory(subCategoryName, currentPage)
                    }
                }
            }
        })
    }

    private fun observeProducts(liveData: LiveData<List<Product>?>) {
        liveData.observe(viewLifecycleOwner) { productData ->
            if (productData.isNullOrEmpty()) {
                binding.emptyStateTextView.visibility = View.VISIBLE
                binding.recview.visibility = View.GONE
            } else {
                binding.emptyStateTextView.visibility = View.GONE
                binding.recview.visibility = View.VISIBLE
                adapterData = GenericAdapter(
                    productData,
                    R.layout.item_product,
                    { itemView, product ->
                        val productImage = itemView.findViewById<ImageView>(R.id.product_img_xml)
                        val productName = itemView.findViewById<TextView>(R.id.product_name_xml)
                        val productPrice = itemView.findViewById<TextView>(R.id.product_price_xml)

                        if (product.product_img.isEmpty()) {
                            productImage.setImageResource(R.drawable.img) // Placeholder image
                        } else {
                            Picasso.get().load(product.product_img).into(productImage)
                        }

                        productName.text = product.product_name
                        productPrice.text = "Price: ${product.product_sell_price} Rs"
                    },
                    {
                        val bundle = Bundle()
                        bundle.putInt("productId", it.product_id ?: 0)
                        findNavController().navigate(
                            R.id.action_productsFragment_to_productDetailsFragments,
                            bundle
                        )
                    }
                )
                binding.recview.adapter = adapterData
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.VISIBLE
    }
}
