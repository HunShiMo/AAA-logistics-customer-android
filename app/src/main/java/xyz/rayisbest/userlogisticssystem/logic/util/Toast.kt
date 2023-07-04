package xyz.rayisbest.userlogisticssystem.logic.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import xyz.rayisbest.userlogisticssystem.UserLogisticsSystemApplication

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(UserLogisticsSystemApplication.context, this, duration).show()
}

fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(UserLogisticsSystemApplication.context, this, duration).show()
}
