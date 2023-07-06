package xyz.rayisbest.userlogisticssystem.logic.bean

import java.io.Serializable
import java.math.BigDecimal
import java.util.Date

data class Order(var waybillId: Long = 0, var orderId: Long = 0, var userId: Long = 0, var sendAddressId: Long = 0,
                 var receiveAddressId: Long = 0, var goodsName: String = "", var goodsDetails: String = "",
                 var orderStatus: String = "", var payWay: String = "", var pickTime: String = "",
                 var pickWorkId: Long = 0, var price: BigDecimal = BigDecimal.ZERO, var createTime: Date = Date(),
                 var branchId: Long = 0): Serializable