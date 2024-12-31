package com.adventitous.matall.modules.general

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.adventitous.matall.R
import com.adventitous.matall.core.nav.NavigationInterface
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentAddressBinding
import com.adventitous.matall.modules.general.viewmodel.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class Address : Fragment() {
    private lateinit var binding: FragmentAddressBinding
    private val productId by lazy {
        arguments?.getInt("productId", 0) ?: 0
    }
    private val quantity by lazy {
        arguments?.getInt("quantity", 0) ?: 0
    }

    @Inject
    lateinit var appSharedPreferences: AppSharedPref
    @Inject
    lateinit var navigation: NavigationInterface
    private val viewModel: AddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addressCheckoutBtn.setOnClickListener {
            if (productId > 0) {
                placeOrder(productId, quantity)
            } else {
                placeAllOrder()
            }
        }
    }

    private fun placeOrder(productId: Int, quantity: Int) {
        if (validation()) {
            val userId: Int = appSharedPreferences.getUserId().toInt()
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val address = "${binding.editTextAddress.text}, ${binding.editTextAddress1.text}, ${binding.editTextAddress2.text}, ${binding.editTextAddress3.text}"

            viewModel.placeOrder(userId, productId, quantity, name, phone, address)
            viewModel.placeOrders.observe(viewLifecycleOwner) { response ->
                if (response.status == "success") {
                    navigation.navigateTo(R.id.action_addressFragement_to_orderPlaced)
                } else {
                    Toast.makeText(requireContext(), response.data.toString(), Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please Fill All Details Correctly", Toast.LENGTH_LONG).show()
        }
    }

    private fun placeAllOrder() {
        if (validation()) {
            val userId: Int = appSharedPreferences.getUserId().toInt()
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val address = "${binding.editTextAddress.text}, ${binding.editTextAddress1.text}, ${binding.editTextAddress2.text}, Pin Code: ${binding.editTextAddress3.text}"

            viewModel.orderAllProductsInCart(userId, name, phone, address)
            viewModel.orderAllProductsInCart.observe(viewLifecycleOwner) { response ->
                if (response.status == "success") {
                    navigation.navigateTo(R.id.action_addressFragement_to_orderPlaced)
                } else {
                    Toast.makeText(requireContext(), response.data.toString(), Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please Fill All Details Correctly", Toast.LENGTH_LONG).show()
        }
    }

    private fun validation(): Boolean {
        return binding.editTextName.text.toString().isNotEmpty() &&
                binding.editTextPhone.text.toString().length == 10 &&
                binding.editTextAddress.text.toString().isNotEmpty() &&
                binding.editTextAddress1.text.toString().isNotEmpty() &&
                binding.editTextAddress2.text.toString().isNotEmpty() &&
                binding.editTextAddress3.text.toString().length == 6
    }
}