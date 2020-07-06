package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_class")
data class Clazz(
    @Field("major_id") val majorId: Int,
    @Field("major_name") val majorName: String,
    @Field("class_id") val clazzId: Int,
    @Field("class_name") val clazzName: String,
    @Field("class_year") val clazzYear: Int
) {
    constructor(): this(0, "", 0, "", 0)
}