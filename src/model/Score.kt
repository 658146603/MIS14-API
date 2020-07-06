package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_score")
data class Score(
    @Field("student_id") val studentId: String,
    @Field("student_name") val studentName: String,
    @Field("class_id") val classId: Int,
    @Field("course_id") val courseId: String,
    @Field("course_name") val courseName: String,
    @Field("course_type") val courseType: String,
    @Field("course_credit") val courseCredit: Float,
    @Field("course_score") val courseScore: Int
) {
    constructor() : this("", "", 0, "", "", "考试", 0f, 0)
}