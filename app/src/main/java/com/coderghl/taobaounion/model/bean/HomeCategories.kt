package com.coderghl.taobaounion.model.bean

data class HomeCategories(
    var success: Boolean,
    var code: Int,
    var message: String,
    var data: ArrayList<Data>
) {
    data class Data(var id: Int, var title: String)
}
