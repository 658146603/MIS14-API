package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import java.sql.SQLException

data class Teacher(
    val teacherId: String,
    val teacherName: String,
    val teacherSex: String,
    val teacherAge: Int,
    val teacherPhone: String,
    val teacherTitle: String
) {
    constructor() : this("", "", "", 0, "", "")

    companion object {
        fun insert(id: String, name: String, sex: Int, age: Int, phone: String, title: Int) {

            try {
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("insert into teacher (id, name, sex, age, phone, title) values (?, ?, ?, ?, ? ,?)")
                        .use { ps ->
                            ps.apply {
                                setString(1, id)
                                setInt(2, sex)
                                setInt(3, age)
                                setString(4, phone)
                                setInt(5, title)
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