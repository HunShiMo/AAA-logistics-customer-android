package xyz.rayisbest.userlogisticssystem.logic.bean

import java.io.Serializable

data class Branch(var branchId: Long = 0, var transitCenterId: Long=0, var address: String="",
                  var facadeImage: String="", var head: String = "", var headPhoneNum: String="",
                  var headIdCardImage: String="", var branchName: String = ""):Serializable {
}