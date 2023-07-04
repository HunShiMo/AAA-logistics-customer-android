package xyz.rayisbest.userlogisticssystem

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.rayisbest.userlogisticssystem.databinding.ActivityUserLoginBinding
import xyz.rayisbest.userlogisticssystem.logic.exception.NoNetworkException
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResult
import xyz.rayisbest.userlogisticssystem.logic.bean.RefreshTokenBody
import xyz.rayisbest.userlogisticssystem.logic.bean.User
import xyz.rayisbest.userlogisticssystem.logic.bean.RefreshTokenResponse
import xyz.rayisbest.userlogisticssystem.logic.bean.UserLoginResponse
import xyz.rayisbest.userlogisticssystem.logic.service.ServiceCreator
import xyz.rayisbest.userlogisticssystem.logic.service.UserService
import xyz.rayisbest.userlogisticssystem.logic.util.showToast

/**
 * 登录界面
 */
class UserLoginActivity : AppCompatActivity() {

    private lateinit var userLoginBinding: ActivityUserLoginBinding

    // 账号输入
    private lateinit var accountEditText: EditText

    // 密码输入
    private lateinit var passwordEditText: EditText

    // 登录按钮
    private lateinit var loginButton: Button

    // 用户服务器
    private lateinit var userService: UserService

    companion object {
        const val TAG = "UserLoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userLoginBinding = ActivityUserLoginBinding.inflate(layoutInflater)
        accountEditText = userLoginBinding.accountEditText
        passwordEditText = userLoginBinding.passwordEditText
        loginButton = userLoginBinding.loginButton
        setContentView(userLoginBinding.root)

        userService = ServiceCreator.create(UserService::class.java)!!

        // 自动登录
        autoLogin()

        /* 添加监听事件 */
        loginButton.setOnClickListener {
            // 设置不可点击
            // loginButton.isClickable = false
            // loginButton.isEnabled = false
            // loginButton.setBackgroundColor(Color.GRAY)

            val account = accountEditText.text.toString()
            val password = passwordEditText.text.toString()

            userLogin(account, password)
        }
    }

    private fun unFreezeLoginButton() {
        // 设置可以点击
        // loginButton.isClickable = true
        // loginButton.isEnabled = true
        // loginButton.setBackgroundColor(Color.BLUE)
    }

    private fun autoLogin() {
        val preferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        val refreshToken = preferences.getString("refreshToken", "")
        val accessToken = preferences.getString("token", "")
        if ("" != refreshToken && "" != accessToken) {
            val refreshTokenBody = RefreshTokenBody(accessToken!!, refreshToken!!)
            userService.refreshToken(refreshTokenBody).enqueue(object : Callback<AjaxResult<RefreshTokenResponse>> {
                override fun onResponse(
                    call: Call<AjaxResult<RefreshTokenResponse>>,
                    response: Response<AjaxResult<RefreshTokenResponse>>
                ) {
                    val data = response.body()
                    if (data != null && data.code == 200L) {
                        val refreshTokenResponse = data.data
                        "自动登录".showToast()
                        saveToken(refreshTokenResponse.newAccessToken, refreshToken)
                        gotoMainActivity()
                    } else {
                        Log.w(TAG, "请求失败，响应体内容 => $data")
                        // "账号密码错误，请重试".showToast()
                    }
                }

                override fun onFailure(call: Call<AjaxResult<RefreshTokenResponse>>, t: Throwable) {
                    t.printStackTrace()
                    Log.d(TAG, t.stackTraceToString())
                    // Log.d(TAG, "请求错误 => $call")
                    if (t is NoNetworkException) {
                        "网络连接失败，请检查网络连接".showToast(Toast.LENGTH_LONG)
                    }
                }
            })
        }
    }

    private fun userLogin(account: String, password: String) {
        val user = User(account = account, password = password)
        userService.userLogin(user).enqueue(object : Callback<AjaxResult<UserLoginResponse>> {
            override fun onResponse(call: Call<AjaxResult<UserLoginResponse>>,
                                    response: Response<AjaxResult<UserLoginResponse>>) {
                val data = response.body()
                if (data != null && data.code == 200L) {
                    unFreezeLoginButton()
                    val userLoginResponse = data.data
                    saveToken(userLoginResponse.token, userLoginResponse.refreshToken)
                    "登录成功".showToast()
                    gotoMainActivity()
                } else {
                    unFreezeLoginButton()
                    "密码或账号错误，请重新登录".showToast(Toast.LENGTH_LONG)
                    Log.w(TAG, "请求失败，响应体内容 => $data")
                }
            }

            override fun onFailure(call: Call<AjaxResult<UserLoginResponse>>, t: Throwable) {
                t.printStackTrace()
                // Log.d(TAG, "请求错误 => $call")
                unFreezeLoginButton()
                if (t is NoNetworkException) {
                    "网络连接失败，请检查网络连接".showToast(Toast.LENGTH_LONG)
                } else {
                    "密码或账号错误，请重新登录".showToast(Toast.LENGTH_LONG)
                }
            }
        })
    }

    private fun saveToken(token: String, refreshToken: String) {
        UserLogisticsSystemApplication.token = token
        UserLogisticsSystemApplication.refreshToken = refreshToken
        getSharedPreferences("data", Context.MODE_PRIVATE).edit {
            putString("token", token)
            putString("refreshToken", refreshToken)
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@UserLoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}