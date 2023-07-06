package xyz.rayisbest.userlogisticssystem.logic.bean

import java.io.Serializable
import java.util.Date

data class Waybill(var waybillId: Long=0, var pickupWorkId: Long=0, var dispatchWorkId: Long=0,
                   var waybillStatus: String="", var startTime: Date= Date(), var endTime: Date=Date(), var goodsTypeId: Long=0,
                   var goodsWeight: Double=0.0, var goodsNum: Int=0, var goodsLength: Double=0.0, var goodsWidth: Double=0.0,
                   var goodsHeight: Double=0.0, var orderId: Long=0): Serializable
