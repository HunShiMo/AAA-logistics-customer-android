package xyz.rayisbest.userlogisticssystem.ui.address

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.ActivityMyAddressBinding
import xyz.rayisbest.userlogisticssystem.logic.adapter.AddressAdapter
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResult
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResultWithoutData
import xyz.rayisbest.userlogisticssystem.logic.service.AddressService
import xyz.rayisbest.userlogisticssystem.logic.service.ServiceCreator
import xyz.rayisbest.userlogisticssystem.logic.util.AddressEditOrAddContract
import xyz.rayisbest.userlogisticssystem.logic.util.ItemClickSupport

class MyAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyAddressBinding
    private lateinit var recyclerViewAddress: RecyclerView
    private val addressList = ArrayList<Address>()
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var addAddressButton: Button
    private lateinit var addressBackButton: ImageView
    private lateinit var itemClickSupport: ItemClickSupport
    private var addressFor: String = "sender"
    private lateinit var addressService: AddressService
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addressService = ServiceCreator.create(AddressService::class.java)!!
        recyclerViewAddress = binding.recyclerViewAddress
        addAddressButton = binding.addAddressButton
        addressBackButton = binding.addressBackButton
        progressBar = binding.progressBarInMyAddress

        val layoutManager = LinearLayoutManager(this@MyAddressActivity)
        recyclerViewAddress.layoutManager = layoutManager
        addressAdapter = AddressAdapter(addressList)
        recyclerViewAddress.adapter = addressAdapter
        itemClickSupport = ItemClickSupport.addTo(recyclerViewAddress)

        val getAddressOrNot = intent.getStringExtra("getAddress")
        if (getAddressOrNot == null) {
            // 什么也不做，正常执行
        } else {
            // 有活动希望获取到一个地址信息，添加额外监视器
            addressFor = getAddressOrNot
            addGetAddressListener()
        }

        initAddressList()

        initListener()
    }

    private fun initListener() {
        // 单个删除按钮操作
        itemClickSupport.addOnChildClickListener(R.id.addressDelButton, object : ItemClickSupport.OnChildClickListener {
            override fun onChildClicked(recyclerView: RecyclerView, position: Int, v: View) {
                // "你点击了删除按钮：position = $position ${addressList[position].name} 问好".showToast()
                Log.d(TAG, "你点击了删除按钮：position = $position ${addressList[position].name} 问好")
                val dialog = AlertDialog.Builder(this@MyAddressActivity)
                    .setIcon(R.drawable.app_icon)
                    .setTitle("确定删除？")
                    .setMessage("确定删除该地址么？")
                    .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which ->
                        Log.d(TAG, "取消删除：position = $position ${addressList[position].name}")
                        dialog.dismiss()
                    })
                    .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                        Log.d(TAG, "删除地址：position = $position ${addressList[position].name}")
                        val tempAddress = addressList[position]
                        addressList.removeAt(position)
                        addressAdapter.notifyItemRemoved(position)
                        addressAdapter.notifyItemChanged(0, addressList.size)
                        dialog.dismiss()
                        addressService.removeAddress(tempAddress.addressId).enqueue(object :Callback<AjaxResultWithoutData> {
                            override fun onResponse(
                                call: Call<AjaxResultWithoutData>,
                                response: Response<AjaxResultWithoutData>
                            ) {
                                // 删除成功
                                Log.d(TAG, "请求网络删除成功")
                            }

                            override fun onFailure(
                                call: Call<AjaxResultWithoutData>,
                                t: Throwable
                            ) {
                                // 删除失败
                                Log.d(TAG, "请求网络删除失败")
                                t.printStackTrace()
                                // 数据回滚
                                initAddressList()
                            }

                        })
                    })
                    .create()
                dialog.show()
            }
        })

        // 单个编辑按钮操作
        itemClickSupport.addOnChildClickListener(R.id.addressEditButton, object: ItemClickSupport.OnChildClickListener {
            override fun onChildClicked(recyclerView: RecyclerView, position: Int, v: View) {
                // 启动编辑地址的活动，希望这个活动带有更新后的地址信息
                val map = HashMap<String, Any>()
                val address = addressList[position]
                map["province"] = address.province
                map["city"] = address.city
                map["district"] = address.district
                map["street"] = address.street
                map["addressDetail"] = address.addressDetail
                map["name"] = address.name
                map["phoneNum"] = address.phoneNum
                map["position"] = position
                address.position = position

                activityEditOrAddLauncher.launch(map)
            }
        })

        addAddressButton.setOnClickListener {
            val map = HashMap<String, Any>()
            activityEditOrAddLauncher.launch(map)
        }

        addressBackButton.setOnClickListener {
            finish()
        }
    }

    private fun addGetAddressListener() {
        var address: Address = Address()

        itemClickSupport.addOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                address = addressList[position]
                val dataIntent = Intent()
                dataIntent.putExtra("address", address)
                dataIntent.putExtra("addressFor", addressFor)
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            }
        })

        onBackPressedDispatcher.addCallback(this@MyAddressActivity, object :OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 其实这里返回的就是个空内容的address
                val dataIntent = Intent()
                dataIntent.putExtra("address", address)
                dataIntent.putExtra("addressFor", addressFor)
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            }
        })
    }

    private val activityEditOrAddLauncher = registerForActivityResult(AddressEditOrAddContract()) {
        Log.d(TAG, "收到新增或编辑后的信息：${it.toString()}")
        if (it.position == -1) {
            // 新增
            addressList.add(it)
            addressAdapter.notifyItemInserted(addressList.size-1)

            // 发送网络请求修改
            addressService.addAddress(it).enqueue(object :Callback<AjaxResultWithoutData> {
                override fun onResponse(
                    call: Call<AjaxResultWithoutData>,
                    response: Response<AjaxResultWithoutData>
                ) {
                    // 添加成功
                    Log.d(TAG, "地址数据添加成功 ${response.body().toString()}")
                }

                override fun onFailure(call: Call<AjaxResultWithoutData>, t: Throwable) {
                    // 添加失败了，数据回滚
                    t.printStackTrace()
                    Log.d(TAG, "数据添加失败 ${call.toString()}")
                    initAddressList()
                }

            })
        } else if (it.position == -2) {
            // 没有新增，没有更改，跳过
        } else {
            // 修改
            addressList[it.position].position = it.position
            addressList[it.position].province = it.province
            addressList[it.position].city = it.city
            addressList[it.position].district = it.district
            addressList[it.position].street = it.street
            addressList[it.position].addressDetail = it.addressDetail
            addressList[it.position].name = it.name
            addressList[it.position].phoneNum = it.phoneNum
            addressList[it.position].cityId = it.cityId
            addressList[it.position].districtId = it.districtId
            addressList[it.position].provinceId = it.provinceId
            addressAdapter.notifyItemRangeChanged(it.position, 1)
            Log.d(TAG, addressList[it.position].toString())

            // 发送网络请求修改
            addressService.updateAddress(it).enqueue(object :Callback<AjaxResultWithoutData> {
                override fun onResponse(
                    call: Call<AjaxResultWithoutData>,
                    response: Response<AjaxResultWithoutData>
                ) {
                    // 修改成功
                    Log.d(TAG, "地址数据修改成功 ${response.body().toString()}")
                }

                override fun onFailure(call: Call<AjaxResultWithoutData>, t: Throwable) {
                    // 修改失败了，数据回滚
                    t.printStackTrace()
                    Log.d(TAG, "数据修改失败 ${call.toString()}")
                    initAddressList()
                }

            })
        }
    }

    private fun initAddressList() {
        progressBar.visibility = View.VISIBLE
        val preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val userId = preferences.getLong("userId", 0)
        addressService.getAddressByUserId(userId).enqueue(object : Callback<AjaxResult<List<Address>>> {
            override fun onResponse(
                call: Call<AjaxResult<List<Address>>>,
                response: Response<AjaxResult<List<Address>>>
            ) {
                progressBar.visibility = View.GONE
                val data = response.body()
                Log.d(TAG, "请求address list =>  $data")
                if (data != null && data.code == 200L) {
                    val listData = data.data
                    addressList.addAll(listData)
                    addressAdapter.notifyItemRangeInserted(0, listData.size)
                }
            }

            override fun onFailure(call: Call<AjaxResult<List<Address>>>, t: Throwable) {
                progressBar.visibility = View.GONE
                t.printStackTrace()
            }
        })
    }

    companion object {
        const val TAG = "MyAddressActivity"
    }
}