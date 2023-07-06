package xyz.rayisbest.userlogisticssystem.logic.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.ui.address.MyAddressActivity

class AddressSelectContract: ActivityResultContract<String, Pair<String, Address>>() {

    companion object {
        const val TAG = "AddressSelectContract"
    }

    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, MyAddressActivity::class.java)
        intent.putExtra("getAddress", input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<String, Address> {
        var address = Address()
        var addressFor = "sender"
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "返回值ok, intent => ${intent.toString()}")
            if (intent != null) {
                address = intent.getSerializableExtra("address") as Address
                addressFor = intent.getStringExtra("addressFor")!!
            }
        } else {
            Log.d(TAG, "返回值不ok, intent => ${intent.toString()}")
        }
        return Pair<String, Address>(addressFor, address)
    }
}