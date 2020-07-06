package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_major")
data class Major(
    @Field("major_id") val majorId: Int,
    @Field("major_name") val MajorName: String
) {
    constructor() : this(0, "")
}