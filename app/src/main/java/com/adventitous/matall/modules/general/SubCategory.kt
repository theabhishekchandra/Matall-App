package com.adventitous.matall.modules.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.adventitous.matall.R
import com.adventitous.matall.core.data.model.SubCategoryData
import com.adventitous.matall.core.genericadapter.GenericAdapter
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentSubCategoryBinding
import com.adventitous.matall.modules.general.viewmodel.SubCategoryViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class SubCategory : Fragment() {
    private var _binding: FragmentSubCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SubCategoryViewModel by viewModels()
    private lateinit var adapter: GenericAdapter<SubCategoryData>
    private val categoryName by lazy {
        arguments?.getString("categoryName", "") ?: ""
    }

    @Inject
    lateinit var navigation: NavigationInterface
    @Inject
    lateinit var appSharedPreferences: AppSharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recviewSubcategory.layoutManager = GridLayoutManager(requireContext(), 3)
        viewModel.getSubCategory(categoryName)
        viewModel.subCategoryProduct.observe(viewLifecycleOwner) { category ->
            if (category != null) {
                adapter = GenericAdapter(category, R.layout.item_sub_category, { view, item ->

                    val categoryName = view.findViewById<TextView>(R.id.name_sub_cat)
                    val categoryItemNum = view.findViewById<TextView>(R.id.txt_item_cat)
                    val categoryImg = view.findViewById<ImageView>(R.id.img_sub_cat)

                    categoryName.text = item.sub_category_name
                    categoryItemNum.text = "Only ${Random.nextInt(22, 99)} Items Left"
                    Picasso.get().load(item.sub_category_img).into(categoryImg)
                }, { subCategory ->
                    // Handle product item click here
                    val bundle = Bundle().apply {
                        putInt("subCategoryId", subCategory.sub_category_id ?: 0)
                        putString("subCategoryName", subCategory.sub_category_name)
                    }
                    // Navigate to ProductsFragment with bundle and clear backstack
                    navigateToProductsFragment(bundle)
                })
                binding.recviewSubcategory.adapter = adapter
            }
        }
    }

    private fun navigateToProductsFragment(bundle: Bundle) {
        val navController = navigation.getNavController()
        navController.popBackStack(R.id.subCategory, true)
        navController.navigate(R.id.productsFragment, bundle)
    }

    companion object {
        fun newInstance(categoryName: String): SubCategory {
            val fragment = SubCategory()
            val args = Bundle()
            args.putString("categoryName", categoryName)
            fragment.arguments = args
            return fragment
        }
    }
}
