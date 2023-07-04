package xyz.rayisbest.userlogisticssystem.logic.util

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import xyz.rayisbest.userlogisticssystem.UserLogisticsSystemApplication

object ToastUtils {
    private val mainHandler = Handler(Looper.getMainLooper())

    fun showToast(message: String) {
        mainHandler.post {
            Toast.makeText(UserLogisticsSystemApplication.context, message, Toast.LENGTH_SHORT).show()
        }
    }
}