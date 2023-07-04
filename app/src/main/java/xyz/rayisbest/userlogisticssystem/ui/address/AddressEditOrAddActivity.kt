package xyz.rayisbest.userlogisticssystem.ui.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citylist.Toast.ToastUtils
import com.lljjcoder.style.citypickerview.CityPickerView
import xyz.rayisbest.userlogisticssystem.databinding.ActivityAddressEditOrAddBinding
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.util.showToast


class AddressEditOrAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressEditOrAddBinding
    private val myCityPicker = CityPickerView()
    private lateinit var provincePicker: Button
    private lateinit var editAddTitle: TextView
    private lateinit var nameEditAddText: EditText
    private lateinit var phoneEditEditText: EditText
    private lateinit var streetEditText: EditText
    private lateinit var detailEditAddText: EditText
    private lateinit var editAddConfirmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddressEditOrAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        provincePicker = binding.provincePicker
        editAddTitle = binding.editAddTitle
        nameEditAddText = binding.nameEditAddText
        phoneEditEditText = binding.phoneEditAddText
        streetEditText = binding.streetEditAddText
        detailEditAddText = binding.detailEditAddText
        editAddConfirmButton = binding.editAddConfirmButton

        val sign = intent.getStringExtra("sign")
        val address = Address()

        if (sign == "edit") {
            editAddTitle.text = "修改地址"

            // 编辑模式，加载数据，可以整个viewModel
            address.province = intent.getStringExtra("province")!!
            address.city = intent.getStringExtra("city")!!
            address.district = intent.getStringExtra("district")!!
            address.street = intent.getStringExtra("street")!!
            address.addressDetail = intent.getStringExtra("addressDetail")!!
            address.name = intent.getStringExtra("name")!!
            address.phoneNum = intent.getStringExtra("phoneNum")!!
            address.position = intent.getIntExtra("position", 0)

            nameEditAddText.setText(address.name)
            phoneEditEditText.setText(address.phoneNum)
            provincePicker.text = address.province + address.city + address.district
            streetEditText.setText(address.street)
            detailEditAddText.setText(address.addressDetail)
        }

        myCityPicker.init(this@AddressEditOrAddActivity)
        val cityConfig = CityConfig.Builder().build()
        myCityPicker.setConfig(cityConfig)
        // 监听选择点击事件及返回结果
        myCityPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(
                province: ProvinceBean?,
                city: CityBean?,
                district: DistrictBean?
            ) {
                // 省份province
                // 城市city
                // 地区district
                Log.d(TAG, "province => ${province.toString()}")
                Log.d(TAG, "city => ${city.toString()}")
                Log.d(TAG, "district => ${district.toString()}")
                address.province = province.toString()
                address.city = city.toString()
                address.district = district.toString()


                provincePicker.text = province.toString() + city.toString() + district.toString()
            }

            override fun onCancel() {
                "已取消".showToast()
            }
        })
        provincePicker.setOnClickListener {
            // 显示
            myCityPicker.showCityPicker()
        }
        editAddConfirmButton.setOnClickListener {
            val dataIntent = Intent()
            dataIntent.apply {
                putExtra("province", address.province)
                putExtra("city", address.city)
                putExtra("district", address.district)
                putExtra("street", streetEditText.text.toString())
                putExtra("addressDetail", detailEditAddText.text.toString())
                putExtra("name", nameEditAddText.text.toString())
                putExtra("phoneNum", phoneEditEditText.text.toString())
                putExtra("position", address.position)
            }
            setResult(Activity.RESULT_OK, dataIntent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this@AddressEditOrAddActivity, object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val dataIntent = Intent()
                dataIntent.apply {
                    putExtra("province", address.province)
                    putExtra("city", address.city)
                    putExtra("district", address.district)
                    putExtra("street", streetEditText.text.toString())
                    putExtra("addressDetail", detailEditAddText.text.toString())
                    putExtra("name", nameEditAddText.text.toString())
                    putExtra("phoneNum", phoneEditEditText.text.toString())
                    putExtra("position", address.position)
                }
                setResult(Activity.RESULT_OK, dataIntent)
                finish()
            }
        })
    }

    companion object {
        const val TAG = "AddressEditOrAddActivity"
    }
}