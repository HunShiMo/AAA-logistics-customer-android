package xyz.rayisbest.userlogisticssystem.logic.bean

data class UserLoginResponse (var token: String, var refreshToken: String, var member: Member) {
    data class Member(var accPassId: Long, var phoneNum: String, var userName: String, var userId: Long)
}