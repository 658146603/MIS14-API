package model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_course")
data class Course(
    @Field("course_id") val courseId: String,
    @Field("course_name") val courseName: String,
    @Field("course_credit") val courseCredit: Float,
    @Field("course_credit_hour") val courseCreditHour: Int,
    @Field("course_type") val courseType: String
) {
    constructor() : this("", "", 0f, 0, "考试")
}