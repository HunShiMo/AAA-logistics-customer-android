package xyz.rayisbest.userlogisticssystem.logic.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.rayisbest.userlogisticssystem.logic.bean.Address
import xyz.rayisbest.userlogisticssystem.logic.viewmodel.PlaceOrderViewModel

class PlaceOrderViewModelFactory(private val senderAddress: Address, private val receiverAddress: Address): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaceOrderViewModel(senderAddress, receiverAddress) as T
    }
}