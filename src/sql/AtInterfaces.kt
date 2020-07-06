package com.zjut.mis14.sql

import java.lang.annotation.Inherited
import kotlin.collections.HashMap

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Inherited
internal annotation class Model(val value: String)

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Inherited
internal annotation class Field(val value: String)

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Inherited
internal annotation class ForeignKey(val value: String)

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Inherited
internal annotation class PrimaryKey

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Inherited
internal annotation class AutoInc

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Inherited
internal annotation class View

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Inherited
internal annotation class Cached

interface SQLAble<T> {
    fun insert(): Long
    fun query(vararg pair: Pair<String, Any>): T?
    fun update(): Boolean
    fun all(): List<T>
    fun filter(vararg pair: Pair<String, Any>): List<T>
}

abstract class Cacheables<V> {
    private val collection: HashMap<Int, V> = HashMap()

    fun get(key: Int): V? {
        val data: V? = collection[key]
        if(data == null){
            val tData = fetch(key) ?: return null
            collection[key] = tData
            return tData
        }
        return data
    }

    protected abstract fun fetch(key: Int): V?
}