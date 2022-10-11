package com.coderghl.taobaounion.model.bean

data class TicketResult(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
)

data class DataX(
    val model: String
)

data class TbkTpwdCreateResponse(
    val `data`: DataX,
    val request_id: String
)

data class Data(
    val tbk_tpwd_create_response: TbkTpwdCreateResponse
)