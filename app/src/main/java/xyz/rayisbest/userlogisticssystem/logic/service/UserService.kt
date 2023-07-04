package xyz.rayisbest.userlogisticssystem.logic.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResult
import xyz.rayisbest.userlogisticssystem.logic.bean.RefreshTokenBody
import xyz.rayisbest.userlogisticssystem.logic.bean.RefreshTokenResponse
import xyz.rayisbest.userlogisticssystem.logic.bean.User
import xyz.rayisbest.userlogisticssystem.logic.bean.UserLoginResponse

interface UserService {

    @POST("/api/auth/user/login")
    fun userLogin(@Body user: User): Call<AjaxResult<UserLoginResponse>>

    @POST("/api/auth/token/refreshAccessToken")
    fun refreshToken(@Body refreshTokenBody: RefreshTokenBody): Call<AjaxResult<RefreshTokenResponse>>

}