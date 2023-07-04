package xyz.rayisbest.userlogisticssystem.logic.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.ui.address.AddressEditOrAddActivity
import java.util.function.BiConsumer

class AddressEditOrAddContract: ActivityResultContract<Map<String, Any>, Address>() {

    companion object {
        const val TAG = "AddressEditOrAddContract"
    }

    override fun createIntent(context: Context, input: Map<String, Any>): Intent {
        val isEdit = input.isNotEmpty()
        if (isEdit) {
            // 编辑模式
            val intent = Intent(context, AddressEditOrAddActivity::class.java)
            intent.putExtra("sign", "edit")
            input.forEach { (t, u) ->
                if (u is Long) {
                    intent.putExtra(t, u)
                } else if (u is Int) {
                    intent.putExtra(t, u)
                } else if (u is String) {
                    intent.putExtra(t, u)
                }
            }
            return intent
        } else {
            // 添加模式
            val intent = Intent(context, AddressEditOrAddActivity::class.java)
            intent.putExtra("sign", "add")
            return intent
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Address {
        val address = Address()
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "返回值ok, intent => ${intent.toString()}")
            if (intent != null) {
                address.province = intent.getStringExtra("province")!!
                address.city = intent.getStringExtra("city")!!
                address.district = intent.getStringExtra("district")!!
                address.street = intent.getStringExtra("street")!!
                address.addressDetail = intent.getStringExtra("addressDetail")!!
                address.name = intent.getStringExtra("name")!!
                address.phoneNum = intent.getStringExtra("phoneNum")!!
                address.position = intent.getIntExtra("position", 0)
            } else {
                Log.d(TAG, "返回值不ok, intent => ${intent.toString()}")
            }
        }
        return address
    }
}