package com.adventitous.matall.modules.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adventitous.matall.R
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentOrderDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetails : Fragment() {

    @Inject
    lateinit var appSharedPreferences: AppSharedPref

    private lateinit var binding: FragmentOrderDetailsBinding
    private val viewModel: OrderDetailsViewModel by viewModels()

    private var orderId: Int = 0
    private var userId: Int = 0
    private lateinit var orderStatus : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            orderId = it.getInt("orderId")
            userId = it.getInt("userId")
            val orderDate = it.getString("orderDate")
            val totalAmount = it.getString("totalAmount")
            val status = it.getString("status")
            val orderStatus = it.getString("orderStatus")
            val paymentMethod = it.getString("paymentMethod")
            val paymentStatus = it.getString("paymentStatus")
            val shippingAddress = it.getString("shippingAddress")
            val shippingMethod = it.getString("shippingMethod")
            val shippingCost = it.getString("shippingCost")
            val quantity = it.getString("quantity")
            val productName = it.getString("productName")
            val productImg = it.getString("productImg")

            // Set the received data to UI components
            binding.orderName.text = productName
            binding.orderPrice.text = totalAmount
            binding.orderDate.text = orderDate
            binding.orderDeliveryDate.text = orderDate
            binding.orderDeliveryAddress.text = shippingAddress
            statusColorTextView(orderStatus.toString())
//            binding.orderDeliveryStatus.text = orderStatus
            Picasso.get().load(productImg).into(binding.orderImg)
        }

        binding.orderCancelBtn.setOnClickListener {
            // Cancel the order
            viewModel.cancelOrder(orderId, userId)
        }
        viewModel.cancelOrder.observe(viewLifecycleOwner){
            if(it.status == "success"){
                Toast.makeText(requireContext(),"Order cancelled", Toast.LENGTH_LONG).show()
            }else{
                // Show error message
                Toast.makeText(requireContext(),"Order cancelled Failed", Toast.LENGTH_LONG).show()

            }
        }
    }
    private fun statusColorTextView(status: String){
        when (status) {
            "delivered" -> {
                val delivery = status.capitalize(Locale.ROOT)
                binding.orderDeliveryStatus.text = delivery
                binding.orderDeliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorDelivered))
            }
            "placed" -> {
                val placed = status.capitalize(Locale.ROOT)
                binding.orderDeliveryStatus.text = placed
                binding.orderDeliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPlaced))
            }
            "shipped" -> {
                val shipped = status.capitalize(Locale.ROOT)
                binding.orderDeliveryStatus.text = shipped
                binding.orderDeliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorShipped))
            }
            "cancelled" -> {
                val cancelled = status.capitalize(Locale.ROOT)
                binding.orderDeliveryStatus.text = cancelled
                binding.orderDeliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorCancelled))
            }
            "processing" -> {
                val processing = status.capitalize(Locale.ROOT)
                binding.orderDeliveryStatus.text = processing
                binding.orderDeliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorProcessing))
            }
            else -> {
                binding.orderDeliveryStatus.text = "Unknown"
                binding.orderDeliveryStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.black)) // Default color
            }
        }

    }
}