package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("wangcf_semester14")
data class Semester(
    @Field("wcf_id14") val semesterId: Int,
    @Field("wcf_year14") val semesterYear: Int,
    @Field("wcf_no14") val semesterNo: Int
) {
    constructor() : this(0, 0, 0)
}