package xyz.rayisbest.userlogisticssystem.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import xyz.rayisbest.userlogisticssystem.R
import xyz.rayisbest.userlogisticssystem.databinding.ActivityShowPackageInfoActityBinding
import xyz.rayisbest.userlogisticssystem.logic.bean.PackageInfo

class ShowPackageInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPackageInfoActityBinding
    private lateinit var senderName: TextView
    private lateinit var senderPhone: TextView
    private lateinit var receiverName: TextView
    private lateinit var receiverPhone: TextView
    private lateinit var receiverAddress: TextView
    private lateinit var orderId: TextView
    private lateinit var createTime: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val packageInfo = intent.getSerializableExtra("packageInfo") as PackageInfo

        binding = ActivityShowPackageInfoActityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        senderName = binding.senderNamePackageInfo
        senderPhone = binding.senderPhonePackageInfo
        receiverName = binding.receiverNamePackageInfo
        receiverPhone = binding.receiverPhonePackageInfo
        receiverAddress = binding.receiverAddressPackageInfo
        orderId = binding.orderIdPackageInfo
        createTime = binding.createTimePackageInfo

        senderName.text = packageInfo.senderAddress.name
        senderPhone.text = packageInfo.senderAddress.phoneNum
        receiverName.text = packageInfo.receiverAddress.name
        receiverPhone.text = packageInfo.receiverAddress.phoneNum
        receiverAddress.text =
            "${packageInfo.receiverAddress.province}${packageInfo.receiverAddress.city}" +
                    "${packageInfo.receiverAddress.district}${packageInfo.receiverAddress.street}" +
                    "${packageInfo.receiverAddress.addressDetail}"
        orderId.text = packageInfo.orderInfo.orderId.toString()
        createTime.text = packageInfo.orderInfo.createTime.toString()
    }
}