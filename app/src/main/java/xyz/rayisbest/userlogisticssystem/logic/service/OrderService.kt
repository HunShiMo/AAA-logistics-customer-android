package xyz.rayisbest.userlogisticssystem.logic.service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import xyz.rayisbest.userlogisticssystem.logic.bean.AjaxResultWithoutData
import xyz.rayisbest.userlogisticssystem.logic.bean.Order

interface OrderService {

    @POST("/api/order/order")
    fun addOrder(@Body order: Order): Call<AjaxResultWithoutData>

}