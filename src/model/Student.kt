package com.zjut.mis14.model

import com.zjut.mis14.sql.Field
import com.zjut.mis14.sql.Model

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

    fun insert(id: String, name: String, sex: Int, age: Int, place: String, clazz: Clazz) {

    }
}