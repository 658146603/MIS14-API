package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import java.sql.SQLException

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

    companion object {
        fun insert(courseId: String, studentId: String, score: Int) {
            try {
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("if not exists(select * from wangcf_score14 where wcf_course14 = ? and wcf_student14 = ?) insert into wangcf_score14 (wcf_course14, wcf_student14, wcf_score14) values (?, ?, ?) else update wangcf_score14 set wcf_score14=? where wcf_course14 = ? and wcf_student14 = ?")
                        .use { ps ->
                            ps.apply {
                                setString(1, courseId)
                                setString(2, studentId)
                                setString(3, courseId)
                                setString(4, studentId)
                                setInt(5, score)
                                setInt(6, score)
                                setString(7, courseId)
                                setString(8, studentId)
                                execute()
                            }
                        }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun selectByYear(studentId: String?, year: Int?): ArrayList<Score> {
            if (studentId == null || year == null) {
                return arrayListOf()
            }
            val scores = ArrayList<Score>()
            DatabaseProvider.getConn()?.use {
                it.prepareStatement("select distinct view_score.course_id, student_id, student_name, view_score.class_id, view_score.course_id, view_score.course_name, view_score.course_credit, view_score.course_type, course_score from view_course_open , view_score where view_score.course_id = view_course_open.course_id and student_id = ? and semester_year = ?")
                    .use { ps ->
                        ps.apply {
                            setString(1, studentId)
                            setInt(2, year)
                        }.executeQuery().use { rs ->
                            while (rs.next()) {
                                val score = Score(
                                    rs.getString("student_id"),
                                    rs.getString("student_name"),
                                    rs.getInt("class_id"),
                                    rs.getString("course_id"),
                                    rs.getString("course_name"),
                                    rs.getString("course_type"),
                                    rs.getFloat("course_credit"),
                                    rs.getInt("course_score")
                                )
                                scores.add(score)
                            }
                        }
                    }
            }

            return scores
        }
    }
}