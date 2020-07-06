package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import com.zjut.mis14.sql.SQL
import java.sql.SQLException

@Model("view_student")
data class Student(
    @Field("student_id") val studentId: String,
    @Field("student_name") val studentName: String,
    @Field("student_sex") val studentSex: String,
    @Field("student_age") val studentAge: Int,
    @Field("student_place") val studentPlace: String,
    @Field("student_credit") val studentCredit: Int,
    @Field("student_gpa") val studentGpa: Float,
    @Field("class_id") val classId: Int,
    @Field("class_name") val className: String
) {
    constructor() : this("", "", "男", 0, "", 0, 0f, 0, "")

    companion object {
        fun insert(id: String, name: String, sex: Int, age: Int, place: Int, clazz: Int) {
            try {
                DatabaseProvider.getConn()?.use {
                    try {
                        it.prepareStatement("insert into student (id, name, sex, age, src_place, credit, class) values (?, ?, ?, ?, ?, 0, ?)")
                            .use { ps ->
                                ps.apply {
                                    setString(1, id)
                                    setString(2, name)
                                    setInt(3, sex)
                                    setInt(4, age)
                                    setInt(5, place)
                                    setInt(6, 0)
                                    setInt(7, clazz)
                                    execute()
                                }
                            }
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}