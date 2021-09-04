package com.federicobenedetti.agendaunivpm

import com.federicobenedetti.agendaunivpm.ui.main.classes.Course
import com.federicobenedetti.agendaunivpm.ui.main.classes.Student
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import org.junit.Assert
import org.junit.Test

class WhoAmITest {
    private var whoamI = WhoAmI

    private var studentCoursesStringList = listOf(
        "Course_0",
        "Course_1"
    )

    private var studentCourses = listOf(
        Course(
            "Course_0",
            "Lorem ipsum",
            "Lorem ipsum but short",
            "Lorem ipsum but long",
            "2035/2036",
            "Teacher_0",
            "00:00",
            arrayListOf(
                "Lesson_0",
                "Lesson_1"
            )
        ),
        Course(
            "Course_1",
            "Lorem ipsum",
            "Lorem ipsum but short",
            "Lorem ipsum but long",
            "2035/2036",
            "Teacher_1",
            "00:00",
            arrayListOf(
                "Lesson_2",
                "Lesson_3"
            )
        ),
    )

    private var student = Student(
        "S123456",
        "abcde1234",
        321654987,
        2021,
        studentCoursesStringList,
        false
    )


    @Test
    fun assert_that_set_logged_in_student_works() {
        whoamI.setLoggedInStudent(student)

        Assert.assertEquals(
            "S123456",
            whoamI.getStudentMatricola()
        )

        Assert.assertEquals(
            321654987,
            whoamI.getStudentPhoneNumber()
        )

        Assert.assertEquals(
            2021,
            whoamI.getStudentCourseYear()
        )

        Assert.assertEquals(
            false,
            whoamI.getStudentSituazioneTasse()
        )

        Assert.assertEquals(
            true,
            whoamI.checkIfStudentIsSubscribedToCourse("Course_0")
        )

        Assert.assertEquals(
            true,
            whoamI.checkIfStudentIsSubscribedToCourse("Course_1")
        )

        Assert.assertEquals(
            false,
            whoamI.checkIfStudentIsSubscribedToCourse("Course_2")
        )
    }

    @Test
    fun assert_that_get_student_courses_works() {
        whoamI.setLoggedInStudent(student)

        Assert.assertEquals(
            studentCoursesStringList,
            whoamI.getStudentCoursesStringList()
        )
    }

    @Test
    fun assert_that_set_null_courses_list_does_nothing() {
        whoamI.setLoggedInStudentCourses(null)

        var result = whoamI.getStudentCourses()

        Assert.assertNotNull(
            result
        )

        Assert.assertEquals(
            0,
            result.size
        )
    }

    @Test
    fun assert_that_get_lessons_student_can_see_works() {
        whoamI.setLoggedInStudentCourses(studentCourses)
        whoamI.setLessonStudentCanSee()

        var result = whoamI.getLessonStudentCanSee()

        Assert.assertNotNull(
            result
        )

        Assert.assertEquals(
            4,
            result.size
        )

        Assert.assertEquals(
            listOf(
                "Lesson_0",
                "Lesson_1",
                "Lesson_2",
                "Lesson_3"
            ),
            result
        )
    }
}