package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import java.sql.SQLException

@Model("view_class")
data class Clazz(
    @Field("major_id") val majorId: Int,
    @Field("major_name") val majorName: String,
    @Field("class_id") val clazzId: Int,
    @Field("class_name") val clazzName: String,
    @Field("class_year") val clazzYear: Int
) {
    constructor() : this(0, "", 0, "", 0)

    companion object {
        fun insert(majorId: Int, clazzName: String, clazzYear: Int) {
            try{
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("insert into wangcf_class14 (wcf_name14, wcf_major14, wcf_year14) values (?, ?, ?)").use { ps->
                        ps.apply {
                            setInt(1, majorId)
                            setString(2, clazzName)
                            setInt(3, clazzYear)
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