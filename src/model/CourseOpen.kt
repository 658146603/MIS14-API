package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import java.sql.SQLException

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
    @Field("semester_year") val semesterYear: Int,
    @Field("semester_no") val semesterNo: Int,
    @Field("course_avg") val course_avg: Float
) {
    constructor() : this("", "", "考试", 0f, 0, "", 0, "", "", 0, 0, 0, 0f)

    companion object {
        fun insert(courseId: String, teacherId: String, classId: Int, semesterId: Int) {
            try {
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("insert into wangcf_course_open14 (wcf_course14, wcf_teacher14, wcf_class14, wcf_semester14) values (?, ?, ?, ?)")
                        .use { ps ->
                            ps.apply {
                                setString(1, courseId)
                                setString(2, teacherId)
                                setInt(3, classId)
                                setInt(4, semesterId)
                                execute()
                            }
                        }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}