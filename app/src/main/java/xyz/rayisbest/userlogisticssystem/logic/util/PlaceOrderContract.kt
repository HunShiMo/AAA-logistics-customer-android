package xyz.rayisbest.userlogisticssystem.logic.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import xyz.rayisbest.userlogisticssystem.logic.bean.Order
import xyz.rayisbest.userlogisticssystem.ui.order.PlaceOrderActivity

class PlaceOrderContract: ActivityResultContract<String, Order>() {

    companion object {
        const val TAG  = "PlaceOrderContract"
    }

    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, PlaceOrderActivity::class.java)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Order {
        val order = Order()
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "返回值ok, intent => ${intent.toString()}")
            if (intent != null) {
                order.goodsDetails = intent.getStringExtra("goodsDetails")!!
            }
        } else {
            Log.d(TAG, "返回值不ok, intent => ${intent.toString()}")
        }

        return order
    }
}