package xyz.rayisbest.userlogisticssystem.logic.bean

data class ImageData(var imageRes: Int, var imageUrl: String, var title: String, var viewType: Int) {

    constructor(cImageRes:Int, cTitle: String, cViewType: Int) : this(cImageRes, "", cTitle, cViewType)

    constructor(cImageUrl:String, cTitle: String, cViewType: Int): this(0, cImageUrl, cTitle, cViewType)

}