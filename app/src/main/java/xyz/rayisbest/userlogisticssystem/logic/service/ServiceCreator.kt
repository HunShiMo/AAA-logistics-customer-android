package xyz.rayisbest.userlogisticssystem.logic.service

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.rayisbest.userlogisticssystem.UserLogisticsSystemApplication
import xyz.rayisbest.userlogisticssystem.logic.exception.NoNetworkException
import xyz.rayisbest.userlogisticssystem.logic.util.NetworkUtils
import xyz.rayisbest.userlogisticssystem.logic.util.ToastUtils
import java.io.IOException
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

object ServiceCreator {

    // 后台接口统一请求路径
    private const val BASE_URL = "http://192.168.137.1:88/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.SECONDS)
        .addInterceptor(object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val networkAvailable =
                NetworkUtils.isNetworkAvailable(UserLogisticsSystemApplication.context)
            if (networkAvailable) {
                val builder = chain.request().newBuilder()
                builder.addHeader("accessToken", UserLogisticsSystemApplication.token)
                val request = builder.build()
                return chain.proceed(request)
            } else {
                Log.d("ServiceCreator", "网络状态 networkAvailable => $networkAvailable")
                chain.call().cancel()
                throw NoNetworkException("无网络连接，请求终止")
            }
        }
    }).build()

    private lateinit var retrofit: Retrofit

    init {
        try {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        } catch (e : RuntimeException) {
            Log.e("ServiceCreator", "message => ${e.message}")
            e.printStackTrace()
        }
    }

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}