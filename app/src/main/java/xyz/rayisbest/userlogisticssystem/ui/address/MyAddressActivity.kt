package xyz.rayisbest.userlogisticssystem.ui.address

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.ActivityMyAddressBinding
import xyz.rayisbest.userlogisticssystem.logic.adapter.AddressAdapter
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.util.AddressEditOrAddContract
import xyz.rayisbest.userlogisticssystem.logic.util.ItemClickSupport
import xyz.rayisbest.userlogisticssystem.logic.util.showToast

class MyAddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyAddressBinding
    private lateinit var recyclerViewAddress: RecyclerView
    private val addressList = ArrayList<Address>()
    private lateinit var addressAdapter: AddressAdapter
    private lateinit var addAddressButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAddressList()

        recyclerViewAddress = binding.recyclerViewAddress
        addAddressButton = binding.addAddressButton

        val layoutManager = LinearLayoutManager(this@MyAddressActivity)
        recyclerViewAddress.layoutManager = layoutManager
        addressAdapter = AddressAdapter(addressList)
        recyclerViewAddress.adapter = addressAdapter

        val itemClickSupport = ItemClickSupport.addTo(recyclerViewAddress)

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
                        addressList.removeAt(position)
                        addressAdapter.notifyItemRemoved(position)
                        addressAdapter.notifyItemChanged(0, addressList.size)
                        dialog.dismiss()
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
    }

    private val activityEditOrAddLauncher = registerForActivityResult(AddressEditOrAddContract()) {
        Log.d(TAG, "收到新增或编辑后的信息：${it.toString()}")

        //data class Address(var position: Int = 0, var addressId: Long=0, var userId: Long=0,
        //                    var province: String="", var city: String="", var district: String="",
        //                    var street: String="", var addressDetail:String="", var name: String="",var phoneNum:String="")
        if (it.position == -1) {
            addressList.add(it)
            addressAdapter.notifyItemInserted(addressList.size-1)
        } else {
            addressList[it.position].position = it.position
            addressList[it.position].province = it.province
            addressList[it.position].city = it.city
            addressList[it.position].district = it.district
            addressList[it.position].street = it.street
            addressList[it.position].addressDetail = it.addressDetail
            addressList[it.position].name = it.name
            addressList[it.position].phoneNum = it.phoneNum
            addressAdapter.notifyItemRangeChanged(it.position, 1)
        }


    }

    private fun initAddressList() {
        addressList.apply {
            add(Address(name = "1", province = "浙江省", city = "杭州市", district = "桐庐县",
                street = "春江路1323号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "2", province = "广东省", city = "揭阳市", district = "惠来县",
                street = "文化路637号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "3", province = "江苏省", city = "淮阴市", district = "涟水县",
                street = "幸福路139号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "4", province = "湖北省", city = "荆门市", district = "京山县",
                street = "祝秦路761号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "5", province = "福建省", city = "莆田市", district = "仙游县",
                street = "长兴北路168号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "6", province = "河北省", city = "唐山市", district = "唐海县",
                street = "基隆路161号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "7", province = "山西省", city = "长治市", district = "壶关县",
                street = "汉兴路163号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "8", province = "山西省", city = "晋城市", district = "应县",
                street = "宋家路488号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "9", province = "山东省", city = "济宁市", district = "鱼台县",
                street = "南京路46号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "10", province = "湖南省", city = "衡阳市", district = "祁东县",
                street = "翠湖北路378号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "11", province = "江苏省", city = "邯郸市", district = "鸡泽县",
                street = "中山路680号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
            add(Address(name = "12", province = "广东省", city = "揭阳市", district = "揭东县",
                street = "幸福路698号", addressDetail = "龙潭小区" , phoneNum = "18268096967"))
        }
    }


    companion object {
        const val TAG = "MyAddressActivity"
    }
}