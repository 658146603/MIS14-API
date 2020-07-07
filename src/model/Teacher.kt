package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import java.sql.SQLException

@Model("view_teacher")
data class Teacher(
    @Field("teacher_id") val teacherId: String,
    @Field("teacher_name")val teacherName: String,
    @Field("teacher_sex")val teacherSex: String,
    @Field("teacher_age")val teacherAge: Int,
    @Field("teacher_phone")val teacherPhone: String,
    @Field("teacher_title")val teacherTitle: String
) {
    constructor() : this("", "", "", 0, "", "")

    companion object {
        fun insert(id: String, name: String, sex: Int, age: Int, phone: String, title: Int) {

            try {
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("insert into wangcf_teacher14 (wcf_id14, wcf_name14, wcf_sex14, wcf_age14, wcf_phone14, wcf_title14) values (?, ?, ?, ?, ? ,?)")
                        .use { ps ->
                            ps.apply {
                                setString(1, id)
                                setString(2, name)
                                setInt(3, sex)
                                setInt(4, age)
                                setString(5, phone)
                                setInt(6, title)
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