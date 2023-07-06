package xyz.rayisbest.userlogisticssystem.logic.viewmodel

import androidx.lifecycle.ViewModel
import xyz.rayisbest.userlogisticssystem.logic.bean.Address

class PlaceOrderViewModel(senderAddress: Address, receiverAddress: Address): ViewModel() {
    // 寄件人地址信息
    var senderAddress: Address = senderAddress

    // 收件人地址信息
    var receiverAddress: Address = receiverAddress

}