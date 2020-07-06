package model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import java.sql.SQLException

@Model("view_course")
data class Course(
    @Field("course_id") val courseId: String,
    @Field("course_name") val courseName: String,
    @Field("course_credit") val courseCredit: Float,
    @Field("course_credit_hour") val courseCreditHour: Int,
    @Field("course_type") val courseType: String
) {
    constructor() : this("", "", 0f, 0, "考试")

    companion object {
        fun insert(id: String, name: String, credit: Float, hour: Int, type: Int) {
            try {
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("insert into course (id, name, credit, credit_hour, type) values (?, ?, ?, ?, ?)")
                        .use { ps ->
                            ps.apply {
                                setString(1, id)
                                setString(2, name)
                                setFloat(3, credit)
                                setInt(4, hour)
                                setInt(5, type)
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