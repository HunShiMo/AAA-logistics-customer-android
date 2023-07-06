package xyz.rayisbest.userlogisticssystem.logic.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResult
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResultWithoutData

interface AddressService {

    @GET("/api/users/address/getAddressListByUserId")
    fun getAddressByUserId(@Query("userId")userId:Long): Call<AjaxResult<List<Address>>>


    @PUT("/api/users/address")
    fun updateAddress(@Body address: Address): Call<AjaxResultWithoutData>

    @POST("/api/users/address")
    fun addAddress(@Body address: Address): Call<AjaxResultWithoutData>

    @DELETE("/api/users/address/{addressId}")
    fun removeAddress(@Path("addressId") addressId: Long): Call<AjaxResultWithoutData>
}