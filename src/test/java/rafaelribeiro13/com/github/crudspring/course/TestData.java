package rafaelribeiro13.com.github.crudspring.course;

import rafaelribeiro13.com.github.crudspring.dto.CourseDTO;
import rafaelribeiro13.com.github.crudspring.dto.LessonDTO;
import rafaelribeiro13.com.github.crudspring.enums.Category;
import rafaelribeiro13.com.github.crudspring.enums.Status;
import rafaelribeiro13.com.github.crudspring.model.Course;
import rafaelribeiro13.com.github.crudspring.model.Lesson;

import java.util.Collections;
import java.util.List;

public class TestData {

    private static final String COURSE_NAME = "Spring";
    private static final String INVALID_COURSE_NAME = "Spr";
    private static final String LOREN_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc et quam nec diam tristique mollis eget quis urna. Sed dapibus lectus in arcu rutrum, non luctus sem finibus. Cras nisl neque, pellentesque et tortor id, dapibus auctor turpis.";
    private static final String LESSON_NAME = "Spring Intro";
    private static final String LESSON_YOUTUBE = "abcdefgh123";

    private TestData() {}

    public static Course createValidCourse() {
        var course = new Course();
        course.setId(1L);
        course.setName(COURSE_NAME);
        course.setCategory(Category.BACK_END);
        course.setStatus(Status.ACTIVE);

        var lesson = new Lesson();
        lesson.setName(LESSON_NAME);
        lesson.setYoutubeUrl(LESSON_YOUTUBE);
        lesson.setCourse(course);
        course.addLesson(lesson);

        return course;
    }

    public static CourseDTO createValidCourseDTO() {
        return new CourseDTO(1L, COURSE_NAME, Category.BACK_END.getValue(), createLessonsDTO());
    }

    public static List<CourseDTO> createInvalidCoursesDTO() {
        final String validName = "COURSE_NAME";
//        final String validCategory = Category.BACK_END.getValue();
        final String empty = "";

        return List.of(
                new CourseDTO(null, validName, empty, createLessonsDTO()),
                new CourseDTO(null, validName, "Invalid Category", createLessonsDTO())
//                new CourseDTO(null, "test", validCategory, createLessonsDTO()),
//                new CourseDTO(null, LOREN_IPSUM, validCategory, createLessonsDTO()),
//                new CourseDTO(null, validName, null, createLessonsDTO()),
//                new CourseDTO(null, LOREN_IPSUM, validCategory, Collections.emptyList())
        );
    }

    private static List<LessonDTO> createLessonsDTO() {
        return List.of(new LessonDTO(1L, LESSON_NAME, LESSON_YOUTUBE));
    }

    private static Course buildCourse(String name, Category category) {
        var course = new Course();
        course.setName(name);
        course.setCategory(category);
        return course;
    }

}
