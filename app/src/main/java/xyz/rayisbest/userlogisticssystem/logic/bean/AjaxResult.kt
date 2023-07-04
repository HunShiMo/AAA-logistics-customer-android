package xyz.rayisbest.userlogisticssystem.logic.bean

data class AjaxResult<T>(var msg: String, var code: Long, var data: T)