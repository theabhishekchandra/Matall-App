package com.adventitous.matall.modules.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adventitous.matall.R
import com.adventitous.matall.core.data.model.OrderData
import com.adventitous.matall.core.genericadapter.GenericAdapter
import com.adventitous.matall.core.shareprefrence.AppSharedPref
import com.adventitous.matall.databinding.FragmentOrdersBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    @Inject
    lateinit var appSharedPreferences: AppSharedPref

    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var adapter: GenericAdapter<OrderData>
    var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = appSharedPreferences.getUserId() ?: 0

        binding.ordersRecview.layoutManager = LinearLayoutManager(requireContext())
        userId.let {
            viewModel.getOrders(it)
        }
        viewModel.orders.observe(viewLifecycleOwner) { orderList ->
            adapter = GenericAdapter(orderList, R.layout.item_orders,{ itemView, order ->
                val orderImage = itemView.findViewById<ImageView>(R.id.order_category_img)
                val orderName = itemView.findViewById<TextView>(R.id.order_category_name)
                val orderDeliveryDate = itemView.findViewById<TextView>(R.id.order_DeliveryDate)
                val orderStatus = itemView.findViewById<TextView>(R.id.order_status)

                // Assuming these fields are available in OrderData
                Picasso.get().load(order.product_img).into(orderImage)
                orderName.text = order.product_name
                orderDeliveryDate.text = order.order_date
                orderStatus.text = order.order_status

            },{ order ->
                val bundle = Bundle().apply {
                    putInt("orderId", order.order_id)
                    putInt("userId", order.user_id)
                    putString("orderDate", order.order_date)
                    putString("totalAmount", order.total_amount)
                    putString("status", order.status)
                    putString("orderStatus", order.order_status)
                    putString("paymentMethod", order.payment_method)
                    putString("paymentStatus", order.payment_status)
                    putString("shippingAddress", order.shipping_address)
                    putString("shippingMethod", order.shipping_method)
                    putString("shippingCost", order.shipping_cost)
                    putString("quantity", order.quantity)
                    putString("productName", order.product_name)
                    putString("productImg", order.product_img)
                }
                findNavController().navigate(
                    R.id.orderDetails,
                    bundle
                )
            })
            binding.ordersRecview.adapter = adapter
        }
    }
    override fun onResume() {
        super.onResume()
        // Show bottom navigation bar
        val bottomNavView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavView.visibility = View.VISIBLE
    }
}
