package com.zjut.mis14

import com.zjut.mis14.model.*
import com.zjut.mis14.sql.SQL
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.freemarker.FreeMarker
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveParameters
import model.Course
import model.SrcPlace

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    val client = HttpClient(Apache) {
    }

    routing {

        static {
            defaultResource("index.html", "web")
            resources("web")
        }

        get("/major/all") {
            val result = SQL().queryList(Major::class.java)
            call.respond(result)
        }

        get("/class/major") {
            val params = call.request.queryParameters
            val majorId = params["major_id"]
            val result = SQL().queryList(Clazz::class.java, "major_id" to majorId)
            call.respond(result)
        }

        get("/src/place/all") {
            val result = SQL().queryList(SrcPlace::class.java)
            call.respond(result)
        }

        get("/student/class") {
            val clazz = call.request.queryParameters["class_id"]
            val result = SQL().queryList(Student::class.java, "class_id" to clazz)
            call.respond(result.apply { sortByDescending { it.studentGpa } })
        }

        get("/student/all") {
            val result = SQL().queryList(Student::class.java)
            call.respond(result)
        }

        get("/teacher/all") {
            val result = SQL().queryList(Teacher::class.java)
            call.respond(result)
        }

        get("/course/all") {
            val result = SQL().queryList(Course::class.java)
            call.respond(result)
        }

        get("/course/open/all") {
            val result = SQL().queryList(CourseOpen::class.java)
            call.respond(result)
        }

        get("/course/open/teacher") {
            val params = call.request.queryParameters
            val teacherId = params["teacher_id"]
            val result = SQL().queryList(CourseOpen::class.java, "teacher_id" to teacherId)
            call.respond(result)
        }

        get("/course/open/course") {
            val params = call.request.queryParameters
            val courseId = params["course_id"]
            val result = SQL().queryList(CourseOpen::class.java, "course_id" to courseId)
            call.respond(result)
        }

        get("/course/open/class") {
            val params = call.request.queryParameters
            val clazzId = params["class_id"]
            val result = SQL().queryList(CourseOpen::class.java, "class_id" to clazzId)
            call.respond(result)
        }

        get("/score/all") {
            val result = SQL().queryList(Score::class.java)
            call.respond(result)
        }

        get("/score/student") {
            val params = call.request.queryParameters
            val studentId = params["student_id"] ?: return@get
            val result = SQL().queryList(Score::class.java, "student_id" to studentId)
            call.respond(result)
        }

        get("/score/student/year") {
            val params = call.request.queryParameters
            val studentId = params["student_id"]
            val year = params["year"]?.toIntOrNull()
            val result = Score.selectByYear(studentId, year)
            call.respond(result)
        }

        get("/score/course") {
            val params = call.request.queryParameters
            val clazzId = params["class_id"]
            val courseId = params["course_id"]
            val result = SQL().queryList(Score::class.java, "class_id" to clazzId, "course_id" to courseId)
            call.respond(result)
        }

        get("/score/info") {
            val params = call.request.queryParameters
            val course = params["course_id"]
            val student = params["student_id"]
            val result =
                SQL().query(ScoreInfo::class.java, "course_id" to course, "student_id" to student) ?: return@get
            call.respond(result)
        }

        get("/score/info/student/all") {
            val params = call.request.queryParameters
            val student = params["student_id"]
            val result = SQL().queryList(ScoreInfo::class.java, "student_id" to student)
            call.respond(result)
        }

        get("/score/class/course/open") {
            val params = call.request.queryParameters
            val clazz = params["class_id"]
            val course = params["course_id"]
            val result = SQL().queryList(ScoreInfo::class.java, "class_id" to clazz, "course_id" to course)
            call.respond(result)
        }

        get("/semester/all") {
            val result = SQL().queryList(Semester::class.java)
            call.respond(result)
        }

        get("/year/all") {
            val result = SQL().queryList(Semester::class.java).map { Year(it.semesterYear, it.semesterYear) }.toSet()
            call.respond(result)
        }

        post("/major/add") {
            val params = call.receiveParameters()
            val name = params["major_name"] ?: return@post
            Major.insert(name)
        }

        post("/class/add") {
            val params = call.receiveParameters()
            val name = params["class_name"] ?: return@post
            val major = params["major_id"]?.toIntOrNull() ?: return@post
            val year = params["class_tear"]?.toIntOrNull() ?: return@post
            Clazz.insert(major, name, year)
        }

        post("/teacher/add") {
            val params = call.receiveParameters()
            val id = params["teacher_id"] ?: return@post
            val name = params["teacher_name"] ?: return@post
            val sex = params["teacher_sex"]?.toIntOrNull() ?: return@post
            val age = params["teacher_age"]?.toIntOrNull() ?: return@post
            val phone = params["teacher_phone"] ?: return@post
            val title = params["teacher_title"]?.toIntOrNull() ?: return@post

            Teacher.insert(id, name, sex, age, phone, title)
        }

        post("/student/add") {
            val params = call.receiveParameters()
            val id = params["student_id"] ?: return@post
            val name = params["student_name"] ?: return@post
            val sex = params["student_sex"]?.toIntOrNull() ?: return@post
            val age = params["student_age"]?.toIntOrNull() ?: return@post
            val place = params["student_place"]?.toIntOrNull() ?: return@post
            val clazz = params["class_id"]?.toIntOrNull() ?: return@post
            Student.insert(id, name, sex, age, place, clazz)
        }

        post("/course/open/add") {
            val params = call.receiveParameters()
            val course = params["course_id"] ?: return@post
            val teacher = params["teacher_id"] ?: return@post
            val clazz = params["class_id"]?.toIntOrNull() ?: return@post
            val semester = params["semester_year"]?.toIntOrNull() ?: return@post

            CourseOpen.insert(course, teacher, clazz, semester)
        }

        post("/score/add") {
            val params = call.receiveParameters()
            val course = params["course_id"] ?: return@post
            val student = params["student_id"] ?: return@post
            val score = params["score"]?.toIntOrNull() ?: return@post
            Score.insert(course, student, score)
            call.respond("status" to 200)
        }

        post("/course/add") {
            val params = call.receiveParameters()
            val id = params["course_id"] ?: return@post
            val name = params["course_name"] ?: return@post
            val credit = params["credit"]?.toFloatOrNull() ?: return@post
            val hour = params["credit_hour"]?.toIntOrNull() ?: return@post
            val type = params["course_type"]?.toIntOrNull() ?: return@post
            Course.insert(id, name, credit, hour, type)
        }

    }
}

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
