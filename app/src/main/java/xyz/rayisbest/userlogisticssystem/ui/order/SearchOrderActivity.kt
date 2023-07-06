package xyz.rayisbest.userlogisticssystem.ui.order

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.ActivitySearchOrderBinding
import xyz.rayisbest.userlogisticssystem.logic.adapter.PackageInfoAdapter
import xyz.rayisbest.userlogisticssystem.logic.bean.Waybill
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.bean.Order
import xyz.rayisbest.userlogisticssystem.logic.bean.PackageInfo
import xyz.rayisbest.userlogisticssystem.logic.util.ItemClickSupport
import xyz.rayisbest.userlogisticssystem.logic.util.showToast
import xyz.rayisbest.userlogisticssystem.ui.address.MyAddressActivity
import java.util.Date

class SearchOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchOrderBinding
    private val packageInfoList = ArrayList<PackageInfo>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var packageInfoAdapter: PackageInfoAdapter
    private lateinit var itemClickListener: ItemClickSupport

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.searchOrderRecyclerview
        itemClickListener = ItemClickSupport.addTo(recyclerView)

        initPackageInfoList()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        packageInfoAdapter = PackageInfoAdapter(packageInfoList)
        recyclerView.adapter = packageInfoAdapter

        initListener()
    }

    private fun initListener() {
        itemClickListener.addOnItemClickListener(object : ItemClickSupport.OnItemClickListener{
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                "点击了position = $position, waybillId = ${packageInfoList[position].waybill.waybillId}".showToast()
                val intent = Intent(this@SearchOrderActivity, ShowPackageInfoActivity::class.java)
                intent.putExtra("packageInfo", packageInfoList[position])
                startActivity(intent)
            }
        })
        itemClickListener.addOnChildClickListener(R.id.orderDelButton, object : ItemClickSupport.OnChildClickListener {
            override fun onChildClicked(recyclerView: RecyclerView, position: Int, v: View) {
                Log.d(TAG, "你点击了删除按钮：position = $position ${packageInfoList[position].waybill.waybillId}")
                val dialog = AlertDialog.Builder(this@SearchOrderActivity)
                    .setIcon(R.drawable.app_icon)
                    .setTitle("确定删除？")
                    .setMessage("确定删除该信息么？")
                    .setNegativeButton("取消", DialogInterface.OnClickListener { dialog, which ->
                        Log.d(MyAddressActivity.TAG, "取消删除：position = $position ${packageInfoList[position].waybill.waybillId}")
                        dialog.dismiss()
                    })
                    .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                        Log.d(MyAddressActivity.TAG, "删除地址：position = $position ${packageInfoList[position].waybill.waybillId}")
                        packageInfoList.removeAt(position)
                        packageInfoAdapter.notifyItemRemoved(position)
                        packageInfoAdapter.notifyItemChanged(0, packageInfoList.size)
                        dialog.dismiss()
                    })
                    .create()
                dialog.show()
            }
        })
    }

    private fun initPackageInfoList() {
        packageInfoList.apply {
            add(PackageInfo(
                senderAddress = Address(city = "烟台市", name = "向往", phoneNum = "13255667788"),
                receiverAddress = Address(city = "杭州市", name = "Ada", province = "浙江省",
                    district = "桐庐县", street = "富春路", addressDetail = "123号", phoneNum = "13255667788"),
                waybill = Waybill(waybillId = 1, waybillStatus = "正在派送"),
                orderInfo = Order(orderId = 1, createTime = Date())
            ))

            add(PackageInfo(
                senderAddress = Address(city = "烟台市", name = "向往", phoneNum = "13255667788"),
                receiverAddress = Address(city = "杭州市", name = "Ada", province = "浙江省",
                    district = "桐庐县", street = "富春路", addressDetail = "123号", phoneNum = "13255667788"),
                waybill = Waybill(waybillId = 1, waybillStatus = "正在派送"),
                orderInfo = Order(orderId = 2, createTime = Date())
            ))

            add(PackageInfo(
                senderAddress = Address(city = "烟台市", name = "向往", phoneNum = "13255667788"),
                receiverAddress = Address(city = "杭州市", name = "Ada", province = "浙江省",
                    district = "桐庐县", street = "富春路", addressDetail = "123号", phoneNum = "13255667788"),
                waybill = Waybill(waybillId = 1, waybillStatus = "正在派送"),
                orderInfo = Order(orderId = 3, createTime = Date())
            ))

            add(PackageInfo(
                senderAddress = Address(city = "烟台市", name = "向往", phoneNum = "13255667788"),
                receiverAddress = Address(city = "杭州市", name = "Ada", province = "浙江省",
                    district = "桐庐县", street = "富春路", addressDetail = "123号", phoneNum = "13255667788"),
                waybill = Waybill(waybillId = 1, waybillStatus = "正在派送"),
                orderInfo = Order(orderId = 4, createTime = Date())
            ))

            add(PackageInfo(
                senderAddress = Address(city = "烟台市", name = "向往", phoneNum = "13255667788"),
                receiverAddress = Address(city = "杭州市", name = "Ada", province = "浙江省",
                    district = "桐庐县", street = "富春路", addressDetail = "123号", phoneNum = "13255667788"),
                waybill = Waybill(waybillId = 1, waybillStatus = "正在派送"),
                orderInfo = Order(orderId = 5, createTime = Date())
            ))

            add(PackageInfo(
                senderAddress = Address(city = "烟台市", name = "向往", phoneNum = "13255667788"),
                receiverAddress = Address(city = "杭州市", name = "Ada", province = "浙江省",
                    district = "桐庐县", street = "富春路", addressDetail = "123号", phoneNum = "13255667788"),
                waybill = Waybill(waybillId = 1, waybillStatus = "正在派送"),
                orderInfo = Order(orderId = 6, createTime = Date())
            ))

        }
    }

    companion object {
        const val TAG = "SearchOrderActivity"
    }
}