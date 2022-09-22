package com.coderghl.taobaounion.model.bean

data class HomePagerContent(
    var code: Long?,
    var `data`: List<Data>?,
    var message: String?,
    var success: Boolean?
) {
    data class Data(
        var category_id: Long?,
        var category_name: Any?,
        var click_url: String?,
        var commission_rate: String?,
        var coupon_amount: Long?,
        var coupon_click_url: String?,
        var coupon_end_time: String?,
        var coupon_info: Any?,
        var coupon_remain_count: Long?,
        var coupon_share_url: String?,
        var coupon_start_fee: String?,
        var coupon_start_time: String?,
        var coupon_total_count: Long?,
        var item_description: String?,
        var item_id: Long?,
        var level_one_category_id: Long?,
        var level_one_category_name: String?,
        var nick: String?,
        var pict_url: String?,
        var seller_id: Long?,
        var shop_title: String?,
        var small_images: SmallImages?,
        var title: String?,
        var user_type: Long?,
        var volume: Long?,
        var zk_final_price: String?
    ) {
        data class SmallImages(
            var string: List<String>?
        )
    }
}


