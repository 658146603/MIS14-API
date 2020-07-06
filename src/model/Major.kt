package com.zjut.mis14.model

import com.zjut.mis14.conn.DatabaseProvider
import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model
import java.sql.SQLException

@Model("view_major")
data class Major(
    @Field("major_id") val majorId: Int,
    @Field("major_name") val MajorName: String
) {
    constructor() : this(0, "")

    companion object {
        fun insert(majorName: String) {
            try{
                DatabaseProvider.getConn()?.use {
                    it.prepareStatement("insert into major (name) values (?)").use { ps ->
                        ps.apply { setString(1, majorName) }
                        ps.execute()
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
    }
}