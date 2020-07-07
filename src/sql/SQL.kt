package com.zjut.mis14.sql

import com.zjut.mis14.conn.DatabaseProvider
import java.sql.*
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList

class SQL {

    fun <T, K> query(clazz: Class<T>, pair: Pair<String, K>?): T? {
        val table = clazz.model() ?: return null
        val model = table.first
        val fields = table.second
        val col = pair?.first
        val value = pair?.second

        val conn = DatabaseProvider.getConn() ?: return null

        if (col != null && value != null && col !in fields.map { it.second }) return null

        var instance: T? = null
        try {
            val ps = if (col != null && value != null) {
                conn.prepareStatement("select * from $model where $col = ?").apply { setObject(1, value) }
            } else {
                conn.prepareStatement("select * from $model")
            }
            val rs = ps.executeQuery()
            if (rs.next()) instance = instance(clazz, fields, rs)

            rs.close()
            ps.close()
            conn.close()
            return instance
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun <T, K1, K2> query(clazz: Class<T>, pair0: Pair<String, K1>, pair1: Pair<String, K2>): T? {
        val table = clazz.model() ?: return null
        val model = table.first
        val fields = table.second
        val col0 = pair0.first
        val val0 = pair0.second
        val col1 = pair1.first
        val val1 = pair1.second

        val conn = DatabaseProvider.getConn() ?: return null

        if (col0 !in fields.map { it.second } || col1 !in fields.map { it.second }) return null

        var instance: T? = null
        try {
            val ps = conn.prepareStatement("select * from $model where $col0=? and $col1=?")
            ps.setObject(1, val0)
            ps.setObject(2, val1)
            val rs = ps.executeQuery()
            if (rs.next()) instance = instance(clazz, fields, rs)

            rs.close()
            ps.close()
            conn.close()
            return instance
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return null
    }

    fun <T> queryList(clazz: Class<T>): ArrayList<T> {
        val list = ArrayList<T>()

        val table = clazz.model() ?: return list
        val model = table.first
        val fields = table.second

        val conn = DatabaseProvider.getConn() ?: return list

        try {
            val ps: PreparedStatement = conn.prepareStatement("select * from $model")
            val rs = ps.executeQuery()
            while (rs.next()) {
                val instance = instance(clazz, fields, rs)
                list.add(instance)
            }
            rs.close()
            ps.close()
            conn.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return list
    }

    fun <T, K> queryList(clazz: Class<T>, pair: Pair<String, K>): ArrayList<T> {
        val list = ArrayList<T>()

        val table = clazz.model() ?: return list
        val model = table.first
        val fields = table.second
        val col = pair.first
        val value = pair.second

        val conn = DatabaseProvider.getConn() ?: return list

        if (col !in fields.map { it.second }) return list

        try {
            val ps: PreparedStatement =
                conn.prepareStatement("select * from $model where $col = ?").apply { setObject(1, value) }
            val rs = ps.executeQuery()
            while (rs.next()) {
                val instance = instance(clazz, fields, rs)
                list.add(instance)
            }
            rs.close()
            ps.close()
            conn.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return list
    }

    fun <T, K1, K2> queryList(clazz: Class<T>, pair0: Pair<String, K1>, pair1: Pair<String, K2>): ArrayList<T> {
        val list = ArrayList<T>()

        val table = clazz.model() ?: return list
        val model = table.first
        val fields = table.second
        val col0 = pair0.first
        val val0 = pair0.second
        val col1 = pair1.first
        val val1 = pair1.second

        val conn = DatabaseProvider.getConn() ?: return list
        try {
            val ps: PreparedStatement =
                conn.prepareStatement("select * from $model where $col0 = ? and $col1 = ?").apply {
                    setObject(1, val0)
                    setObject(2, val1)
                }
            val rs = ps.executeQuery()
            while (rs.next()) {
                val instance = instance(clazz, fields, rs)
                list.add(instance)
            }
            rs.close()
            ps.close()
            conn.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return list
    }

    private fun <T> instance(clazz: Class<T>, fields: List<Pair<java.lang.reflect.Field, String?>>, rs: ResultSet): T {
        val constructor = clazz.getConstructor()
        constructor.isAccessible = true
        val instance = constructor.newInstance()
        for (field in fields) {
            val type = field.first
            type.isAccessible = true
            val name = field.second
            type.set(instance, when {
                type.type == String::class.java -> rs.getString(name)
                type.type == Integer::class.java || type.type == Int::class.java -> rs.getInt(name)
                type.type == Date::class.java -> rs.getDate(name)
                type.type == Time::class.java -> rs.getTime(name)
                type.type == Boolean::class.java -> rs.getInt(name) != 0
                type.type == Float::class.java -> rs.getFloat(name)
                type.hasFK() -> type.fk()?.let { query(it.second, it.first to rs.getObject(name)) }
                else -> rs.getObject(name)
            })
        }
        return instance
    }

    private fun <T> Class<T>.model(): Pair<String, List<Pair<java.lang.reflect.Field, String?>>>? {
        val models = getAnnotationsByType(Model::class.java)
        if (models.isEmpty()) return null

        val model: String = models[0].value

        val fields: List<Pair<java.lang.reflect.Field, String?>> =
            fields().map { it to it.getAnnotationsByType(Field::class.java).firstOrNull()?.value }
                .filter { it.second != null }

        return model to fields
    }

    private fun java.lang.reflect.Field.fk(): Pair<String, Class<*>>? {
        val fks = this.getAnnotationsByType(ForeignKey::class.java)
        val ref = fks.firstOrNull()?.let { it.value to this.type }
        if (ref != null) return ref
        return null
    }

    private fun Class<*>.fields(): HashSet<java.lang.reflect.Field> {
        var clazz = this
        val fields = HashSet<java.lang.reflect.Field>()
        while (clazz != Any::class.java) {
            fields.addAll(listOf(*clazz.declaredFields))
            clazz = clazz.superclass
        }
        return fields
    }

    private fun java.lang.reflect.Field.hasFK(): Boolean {
        return !getAnnotationsByType(ForeignKey::class.java).isNullOrEmpty()
    }

}
