package xyz.rayisbest.userlogisticssystem.logic.bean

data class Branch(var branchId: Long = 0, var transitCenterId: Long=0, var address: String="",
                  var facade_image: String="", var head: String = "", var headPhoneNum: String="",
                  var headIdCardImage: String="", var branchName: String = "") {
}