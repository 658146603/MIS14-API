package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_score_info")
data class ScoreInfo(
    @Field("student_id") val studentId: String,
    @Field("student_name") val studentName: String,
    @Field("semester_year") val semesterYear: Int,
    @Field("semester_name") val semesterNo: Int,
    @Field("teacher_id") val teacherId: String,
    @Field("teacher_name") val teacherName: String,
    @Field("class_id") val classId: Int,
    @Field("class_name") val className: String,
    @Field("course_id") val courseId: String,
    @Field("course_name") val courseName: String,
    @Field("course_credit") val courseCredit: Float
) {
    constructor() : this("", "", 0, 0, "", "", 0, "", "", "", 0f)
}