package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

@Model("view_course_open")
data class CourseOpen(
    @Field("course_id") val courseId: String,
    @Field("course_name") val courseName: String,
    @Field("course_type") val courseType: String,
    @Field("course_credit") val courseCredit: Float,
    @Field("class_id") val classId: Int,
    @Field("class_name") val className: String,
    @Field("class_year") val classYear: Int,
    @Field("teacher_id") val teacherId: String,
    @Field("teacher_name") val teacherName: String,
    @Field("semester_id") val semesterId: Int,
    @Field("semester_name") val semesterName: String,
    @Field("course_avg") val course_avg: Float
) {
    constructor() : this("", "", "考试", 0f, 0, "", 0, "", "", 0, "", 0f)
}