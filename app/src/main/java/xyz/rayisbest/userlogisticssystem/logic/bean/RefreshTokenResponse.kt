package xyz.rayisbest.userlogisticssystem.logic.bean

data class RefreshTokenResponse(var newAccessToken: String, var expireTime: Long)