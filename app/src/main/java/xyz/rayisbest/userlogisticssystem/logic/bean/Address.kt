package xyz.rayisbest.userlogisticssystem.logic.bean

data class Address(var position: Int = -1, var addressId: Long=0, var userId: Long=0,
                   var province: String="", var city: String="", var district: String="",
                   var street: String="", var addressDetail:String="", var name: String="",var phoneNum:String="")