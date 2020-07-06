package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import com.zjut.mis14.sql.SQL
import java.sql.SQLException
import kotlin.math.exp

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
                    it.prepareStatement("insert into score (course, student, score) values (?, ?, ?)").use { ps ->
                        ps.apply {
                            setString(1, courseId)
                            setString(2, studentId)
                            setInt(3, score)
                            execute()
                        }
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        fun selectByYear(studentId: String?, year: Int?): HashSet<Score> {
            val courseOpenInYear = SQL().queryList(CourseOpen::class.java, "semester_year" to year)
            val scores = HashSet<Score>()
            courseOpenInYear.forEach {
                scores.addAll(SQL().queryList(Score::class.java, "student_id" to studentId, "course_id" to it.courseId))
            }
            return scores
        }
    }
}