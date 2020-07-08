package com.zjut.mis14.conn

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseProvider {
    private const val url = "jdbc:sqlserver://47.93.33.34:1433;databaseName=wangchengfeiMIS14;user=sa;password=$password"

    fun getConn(): Connection? {
        return try {
            DriverManager.getConnection(url)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }
}