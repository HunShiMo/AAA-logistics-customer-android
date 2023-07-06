package xyz.rayisbest.userlogisticssystem.logic.bean

import java.io.Serializable

data class PackageInfo(var senderAddress: Address = Address(), var receiverAddress: Address= Address(),
                       var orderInfo: Order= Order(), var waybill: Waybill = Waybill()
): Serializable {

}
