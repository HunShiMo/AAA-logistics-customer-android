package xyz.rayisbest.userlogisticssystem

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Looper

class UserLogisticsSystemApplication: Application() {

    companion object {
        // 因为这里获取的不是Activity或service的Context，而是Application中的Context，全局只有一份实例，
        // 并且在整个应用程序的生命周期内都不会回收，因此是不存在内存泄露风险的。直接抑制警告，无视它
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        // 登录token
        lateinit var token: String
        // 刷新token
        lateinit var refreshToken: String

        var userId: Long = 0
        var userName: String = ""
        var phoneNum: String = ""
        var accPassId: Long = 0
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        // token初始化
        token = ""
        refreshToken = ""
    }

}