package xyz.rayisbest.userlogisticssystem.ui.order

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.rayisbest.userlogisticssystem.UserLogisticsSystemApplication
import xyz.rayisbest.userlogisticssystem.databinding.ActivityPalceOrderBinding
import xyz.rayisbest.userlogisticssystem.logic.adapter.MyFragmentStateAdapter
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResultWithoutData
import xyz.rayisbest.userlogisticssystem.logic.bean.Order
import xyz.rayisbest.userlogisticssystem.logic.service.OrderService
import xyz.rayisbest.userlogisticssystem.logic.service.ServiceCreator
import xyz.rayisbest.userlogisticssystem.logic.util.AddressSelectContract
import xyz.rayisbest.userlogisticssystem.logic.util.TabLayoutMediator
import xyz.rayisbest.userlogisticssystem.logic.util.showToast
import xyz.rayisbest.userlogisticssystem.logic.viewmodel.PlaceOrderViewModel
import xyz.rayisbest.userlogisticssystem.logic.viewmodel.factory.PlaceOrderViewModelFactory


class PlaceOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPalceOrderBinding

    private lateinit var senderTextView: TextView
    private lateinit var recipientTextView: TextView
    private lateinit var placeOrderButton: Button
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var senderAddressButton: Button
    private lateinit var receiverAddressButton: Button
    private lateinit var senderNamePhone: TextView
    private lateinit var receiverNamePhone: TextView
    private lateinit var viewModel: PlaceOrderViewModel
    private lateinit var orderService: OrderService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPalceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 初始化控件
        senderTextView = binding.senderTextView
        recipientTextView = binding.recipientTextView
        placeOrderButton = binding.placeOrderButton
        viewPager2 = binding.vp2
        tabLayout = binding.tabLayout
        senderAddressButton = binding.senderAddressButton
        receiverAddressButton = binding.receiverAddressButton
        senderNamePhone = binding.senderNamePhone
        receiverNamePhone = binding.receiverNamePhone
        orderService = ServiceCreator.create(OrderService::class.java)!!


        init()
    }

    private fun init() {
        Log.d(TAG, "init函数执行")
        val gson = Gson()
        val sharedPreferences = getSharedPreferences("place_order", Context.MODE_PRIVATE)
        var senderAddress = Address()
        var receiverAddress = Address()

        val senderAddressStr = sharedPreferences.getString("senderAddress", "")
        if (senderAddressStr !=null&&senderAddressStr!="") {
            senderAddress = gson.fromJson(senderAddressStr, Address::class.java)
        }
        val receiverAddressStr = sharedPreferences.getString("receiverAddress", "")
        if (receiverAddressStr !=null && receiverAddressStr!="") {
            receiverAddress = gson.fromJson(receiverAddressStr, Address::class.java)
        }

        viewModel = ViewModelProvider(this,
            PlaceOrderViewModelFactory(senderAddress, receiverAddress))[PlaceOrderViewModel::class.java]

        // 加载数据
        senderNamePhone.text = viewModel.senderAddress.name + "  " + viewModel.senderAddress.phoneNum
        receiverNamePhone.text = viewModel.receiverAddress.name + "  " + viewModel.receiverAddress.phoneNum

        placeOrderButton.setOnClickListener {
            Log.d(TAG, "当前的页面id ${viewPager2.currentItem}")
            if (viewPager2.currentItem == 0) {
                // 上门取件页面
                placeOrderWithPickUp()
            } else {
                // 网点自寄
                placeOrderWithSelfSend()
            }
            finish()
        }

        viewPager2.setAdapter(MyFragmentStateAdapter(supportFragmentManager, lifecycle))

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "上门取件"
                }
                else -> {
                    tab.text = "网点自寄"
                }
            }
        }.attach()

        senderAddressButton.setOnClickListener {
            // 启动地址簿活动，返回地址信息
            addressSelectorLauncher.launch("sender")
        }

        receiverAddressButton.setOnClickListener {
            addressSelectorLauncher.launch("receiver")
        }
    }

    private fun placeOrderWithPickUp() {
        val fragment = supportFragmentManager.findFragmentByTag("f0") as PickupOrderFragment
        val radioGroup = fragment.binding.paywayRadioGroup

        val goodsName = fragment.binding.goodsNameEditText.text.toString()
        val goodsDetail = fragment.binding.goodsDetailEditText.text.toString()
        var payWay = ""
        val pickTime = fragment.binding.dateTimePicker.text.toString()

        val checkedRadioButtonId = radioGroup.checkedRadioButtonId
        val rb = findViewById<RadioButton>(checkedRadioButtonId)
        payWay = rb.text.toString()

        Log.d(TAG, goodsName)
        Log.d(TAG, goodsDetail)
        Log.d(TAG, payWay)
        Log.d(TAG, pickTime)

        val order: Order = Order(
            userId = UserLogisticsSystemApplication.userId,
            sendAddressId = viewModel.senderAddress.addressId,
            receiveAddressId = viewModel.receiverAddress.addressId,
            goodsName = goodsName,
            goodsDetails = goodsDetail,
            orderStatus = "01",
            payWay = payWay,
            pickTime = pickTime,
        )
        orderService.addOrder(order).enqueue(object : Callback<AjaxResultWithoutData> {
            override fun onResponse(
                call: Call<AjaxResultWithoutData>,
                response: Response<AjaxResultWithoutData>
            ) {
                "订单已创建".showToast()
                Log.d(TAG, "订单信息 ： ${order.toString()}")
            }

            override fun onFailure(call: Call<AjaxResultWithoutData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun placeOrderWithSelfSend() {
        // 网点自寄
        val selfSendOrderFragment =
            supportFragmentManager.findFragmentByTag("f1") as SelfSendOrderFragment
        val goodsName = selfSendOrderFragment.binding.goodsNameEditText.text.toString()
        val goodsDetail = selfSendOrderFragment.binding.goodsDetailEditText.text.toString()
        val checkedRadioButtonId =
            selfSendOrderFragment.binding.selfSendOrderRadioGroup.checkedRadioButtonId
        val payWay = findViewById<RadioButton>(checkedRadioButtonId).text.toString()
        val branchInfo = selfSendOrderFragment.branchInfo

        val order: Order = Order(
            userId = UserLogisticsSystemApplication.userId,
            sendAddressId = viewModel.senderAddress.addressId,
            receiveAddressId = viewModel.receiverAddress.addressId,
            goodsName = goodsName,
            goodsDetails = goodsDetail,
            orderStatus = "01",
            payWay = payWay,
            branchId = branchInfo.branchId
        )

        orderService.addOrder(order).enqueue(object : Callback<AjaxResultWithoutData> {
            override fun onResponse(
                call: Call<AjaxResultWithoutData>,
                response: Response<AjaxResultWithoutData>
            ) {
                "创建订单成功".showToast()
            }

            override fun onFailure(call: Call<AjaxResultWithoutData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private val addressSelectorLauncher = registerForActivityResult(AddressSelectContract()) {
        val addressFor = it.first
        val address = it.second

        if ("sender" == addressFor) {
            viewModel.senderAddress = address.copy()
            senderNamePhone.text = address.name + "  " + address.phoneNum
        } else {
            viewModel.receiverAddress = address.copy()
            receiverNamePhone.text = address.name + "  " + address.phoneNum
        }
    }

    companion object {
        const val TAG = "PlaceOrder"
    }

    override fun onStop() {
        Log.d(TAG,"onStop方法执行")
        super.onStop()
        val gson = Gson()
        getSharedPreferences("place_order", Context.MODE_PRIVATE).edit {
            putString("senderAddress", gson.toJson(viewModel.senderAddress))
            putString("receiverAddress", gson.toJson(viewModel.receiverAddress))
        }
    }
}