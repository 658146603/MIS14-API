package model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_src_place")
data class SrcPlace(
    @Field("place_id") val placeId: Int,
    @Field("place_name") val placeName: String,
    @Field("place_count") val placeCount: Int
) {
    constructor() : this(0, "", 0)
}