package com.federicobenedetti.agendaunivpm

import com.federicobenedetti.agendaunivpm.ui.main.classes.*
import com.federicobenedetti.agendaunivpm.ui.main.singletons.DataPersistanceUtils
import com.federicobenedetti.agendaunivpm.ui.main.singletons.WhoAmI
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock


class DataPersistanceUtilsTest {
    private var dataPersistanceUtils = DataPersistanceUtils

    @Mock
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


    init {
        whoamI.setLoggedInStudent(student)
        whoamI.setLoggedInStudentCourses(studentCourses)
    }

    /**
     * Teachers
     */
    @Test
    fun assert_that_set_teachers_will_not_set_null_teachers_list() {
        dataPersistanceUtils.reset()
        
        dataPersistanceUtils.setTeachers(null)

        var result = dataPersistanceUtils.getTeachers()

        Assert.assertNotNull(
            result
        )

        Assert.assertEquals(
            0,
            result.size
        )
    }

    @Test
    fun assert_that_set_teachers_will_set_non_null_teachers_list_correctly() {
        var teachers = listOf(
            Teacher("Teacher_0", "Mario", "Pluto", "Rossi")
        )
        dataPersistanceUtils.setTeachers(teachers)
        Assert.assertEquals(
            teachers,
            dataPersistanceUtils.getTeachers()
        )

        dataPersistanceUtils.reset()
    }

    @Test
    fun assert_that_get_teacher_by_id_returns_correct_teacher() {
        var teachers = listOf(
            Teacher("Teacher_0", "Mario", "Pluto", "Rossi")
        )
        dataPersistanceUtils.setTeachers(teachers)
        var result = dataPersistanceUtils.getTeacherById("Teacher_0")

        Assert.assertEquals(
            teachers[0],
            result
        )
    }

    @Test
    fun assert_that_get_teacher_by_wrong_id_returns_null() {
        var teachers = listOf(
            Teacher("Teacher_0", "Mario", "Pluto", "Rossi")
        )
        dataPersistanceUtils.setTeachers(teachers)
        var result = dataPersistanceUtils.getTeacherById("Teacher_1598")

        Assert.assertEquals(
            null,
            result
        )
    }

    @Test
    fun assert_that_get_teacher_by_null_id_returns_null() {
        var teachers = listOf(
            Teacher("Teacher_0", "Mario", "Pluto", "Rossi")
        )
        dataPersistanceUtils.setTeachers(teachers)
        var result = dataPersistanceUtils.getTeacherById(null)

        Assert.assertEquals(
            null,
            result
        )
    }

    /**
     * Courses
     */
    @Test
    fun assert_that_set_courses_will_not_set_null_courses_list() {
        dataPersistanceUtils.setCourses(null)

        var result = dataPersistanceUtils.getCourses()

        Assert.assertNotNull(
            result
        )

        Assert.assertEquals(
            0,
            result.size
        )
    }

    @Test
    fun assert_that_set_courses_will_set_non_null_courses_list_correctly() {
        var courses = listOf(
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
            Course(
                "Course_2",
                "Lorem ipsum",
                "Lorem ipsum but short",
                "Lorem ipsum but long",
                "2035/2036",
                "Teacher_2",
                "00:00",
                arrayListOf(
                    "Lesson_4",
                    "Lesson_5"
                )
            ),
        )

        dataPersistanceUtils.setCourses(courses)
        Assert.assertEquals(
            courses,
            dataPersistanceUtils.getCourses()
        )

        dataPersistanceUtils.reset()
    }


    /**
     * Lessons
     */
    @Test
    fun assert_that_set_lessons_will_not_set_null_lessons_list() {
        dataPersistanceUtils.setLessons(null)

        var result = dataPersistanceUtils.getLessons()

        Assert.assertNotNull(
            result
        )

        Assert.assertEquals(
            0,
            result.size
        )
    }

    @Test
    fun assert_that_set_lessons_will_set_non_null_lessons_list_correctly() {
        var lesson = listOf(
            Lesson(
                "Lesson_0",
                "00:00",
                "Lorem ipsum",
                "2040/2041",
                "Lorem ipsum but short",
                "Teacher_0",
                "Lesson pippo pluto paperino",
                "Course_0"
            ),
            Lesson(
                "Lesson_1",
                "00:00",
                "Lorem ipsum",
                "2040/2041",
                "Lorem ipsum but short",
                "Teacher_1",
                "Lesson pippo pluto paperino",
                "Course_1"
            ),
            Lesson(
                "Lesson_2",
                "00:00",
                "Lorem ipsum",
                "2040/2041",
                "Lorem ipsum but short",
                "Teacher_2",
                "Lesson pippo pluto paperino",
                "Course_2"
            ),
        )

        dataPersistanceUtils.setLessons(lesson)
        Assert.assertEquals(
            lesson,
            dataPersistanceUtils.getLessons()
        )

        dataPersistanceUtils.reset()
    }

    @Test
    fun assert_that_get_lesson_by_id_returns_correct_lesson() {
        var lessons = listOf(
            Lesson(
                "Lesson_0",
                "00:00",
                "Lorem ipsum",
                "2040/2041",
                "Lorem ipsum but short",
                "Teacher_0",
                "Lesson pippo pluto paperino",
                "Course_0"
            ),
        )

        dataPersistanceUtils.setLessons(lessons)
        var result = dataPersistanceUtils.getLessonById("Lesson_0")

        Assert.assertEquals(
            lessons[0],
            result
        )
    }

    @Test
    fun assert_that_get_lesson_by_wrong_id_returns_null() {
        var lessons = listOf(
            Lesson(
                "Lesson_0",
                "00:00",
                "Lorem ipsum",
                "2040/2041",
                "Lorem ipsum but short",
                "Teacher_0",
                "Lesson pippo pluto paperino",
                "Course_0"
            ),
        )

        dataPersistanceUtils.setLessons(lessons)
        var result = dataPersistanceUtils.getLessonById("Lesson_1515")

        Assert.assertEquals(
            null,
            result
        )
    }

    @Test
    fun assert_that_get_lesson_by_null_id_returns_null() {
        var lessons = listOf(
            Lesson(
                "Lesson_0",
                "00:00",
                "Lorem ipsum",
                "2040/2041",
                "Lorem ipsum but short",
                "Teacher_0",
                "Lesson pippo pluto paperino",
                "Course_0"
            ),
        )

        dataPersistanceUtils.setLessons(lessons)
        var result = dataPersistanceUtils.getLessonById(null)

        Assert.assertEquals(
            null,
            result
        )
    }

    /**
     * Calendar Lessons
     */
    @Test
    fun assert_that_set_calendar_lessons_will_not_set_null_calendar_lessons_list() {
        dataPersistanceUtils.setCalendarLessons(null)
        var result = dataPersistanceUtils.getCalendarLessons()

        Assert.assertNotNull(
            result
        )

        Assert.assertEquals(
            0,
            result.size
        )
    }

    @Test
    fun assert_that_set_calendar_lessons_will_set_non_null_calendar_lessons_list_correctly() {
        var calendarLesson = listOf(
            CalendarLesson(
                "Lesson_0",
                "15:42"
            ),
            CalendarLesson(
                "Lesson_1",
                "15:45"
            ),
            CalendarLesson(
                "Lesson_2",
                "15:43"
            ),
        )

        dataPersistanceUtils.setCalendarLessons(calendarLesson)
        Assert.assertEquals(
            calendarLesson,
            dataPersistanceUtils.getCalendarLessons()
        )

        dataPersistanceUtils.reset()
    }

    @Test
    fun assert_that_set_calendar_lesson_which_user_can_see_works() {
        var calendarLesson = listOf(
            CalendarLesson(
                "Lesson_0",
                "15:42"
            ),
            CalendarLesson(
                "Lesson_1",
                "15:45"
            ),
            CalendarLesson(
                "Lesson_2",
                "15:43"
            ),
            CalendarLesson(
                "Lesson_3",
                "17:17"
            )
        )

        dataPersistanceUtils.setCalendarLessons(calendarLesson)

        var result = dataPersistanceUtils.getCalendarLessonsWhichUserCanSee();

        Assert.assertNotNull(result)
        Assert.assertEquals(
            4,
            result.size
        )

        for ((i, lesson) in calendarLesson.withIndex()) {
            Assert.assertEquals(
                lesson,
                result[i]
            )
        }
    }
}