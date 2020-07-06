package com.zjut.mis14

import com.zjut.mis14.model.*
import com.zjut.mis14.sql.SQL
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import model.Course

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val client = HttpClient(Apache) {
    }

    routing {

        get("/majors") {
            val result = SQL().queryList(Major::class.java)
            call.respond(result)
        }

        get("/classes") {
            val params = call.request.queryParameters
            val majorId = params["major_id"]
            val result = SQL().queryList(Clazz::class.java, "major_id" to majorId)
            call.respond(result)
        }

        get("/student/class") {
            val clazz = call.request.queryParameters["class_id"]
            val result = SQL().queryList(Student::class.java, "class_id" to clazz)
            call.respond(result)
        }

        get("/student/all") {
            val result = SQL().queryList(Student::class.java)
            call.respond(result)
        }

        get("/teacher/all") {
            val result = SQL().queryList(Student::class.java)
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
            val studentId = params["student_id"]
            val result = SQL().queryList(Score::class.java, "student_id" to studentId)
            call.respond(result)
        }

        get("/score/course/open") {
            val params = call.request.queryParameters
            val clazzId = params["class_id"]
            val courseId = params["course_id"]
            val result = SQL().queryList(Score::class.java, "class_id" to clazzId, "course_id" to courseId)
            call.respond(result)
        }

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/html-dsl") {
            call.respondHtml {
                head {
                    title { +"你好" }
                }
                body {
                    h1 { +"HTML" }
                    ul {
                        for (n in 1..10) {
                            li { +"第${n}项" }
                        }
                    }
                }
            }
        }

        get("/styles.css") {
            call.respondCss {
                body {
                    backgroundColor = Color.red
                }
                p {
                    fontSize = 2.em
                }
                rule("p.myclass") {
                    color = Color.blue
                }
            }
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
