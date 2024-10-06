package rafaelribeiro13.com.github.crudspring.course;

import jakarta.validation.ConstraintDefinitionException;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import rafaelribeiro13.com.github.crudspring.CourseRepository;
import rafaelribeiro13.com.github.crudspring.dto.CourseDTO;
import rafaelribeiro13.com.github.crudspring.dto.CoursePageDTO;
import rafaelribeiro13.com.github.crudspring.dto.mapper.CourseMapper;
import rafaelribeiro13.com.github.crudspring.exception.ResourceNotFoundException;
import rafaelribeiro13.com.github.crudspring.model.Course;
import rafaelribeiro13.com.github.crudspring.service.CourseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@SuppressWarnings("null")
@ActiveProfiles("test")
@SpringJUnitConfig(classes = {CourseService.class, CourseMapper.class})
class CourseServiceTest {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("Should return a list of courses with pagination")
    void testFindAllPageable() {
        // GIVEN
        List<Course> courses = List.of(TestData.createValidCourse());
        Page<Course> coursePage = new PageImpl<>(courses);

        // WHEN
        when(this.courseRepository.findAll(any(PageRequest.class))).thenReturn(coursePage);
        List<CourseDTO> dtoList = new ArrayList<>(courses.size());

        for (Course course : courses) {
            dtoList.add(courseMapper.toDTO(course));
        }

        CoursePageDTO coursePageDto = this.courseService.findAll(0, 5);

        // THEN
        assertEquals(dtoList, coursePageDto.courses());
        assertThat(coursePageDto.courses()).isNotEmpty();
        assertEquals(1, coursePageDto.totalElements());
        assertThat(coursePageDto.courses().get(0).lessons()).isNotEmpty();
        verify(this.courseRepository).findAll(any(PageRequest.class));
    }

    @Test
    @DisplayName("Should return a course by id")
    void testFindById() {
        // GIVEN
        Course course = TestData.createValidCourse();
        Optional<Course> ofResult = Optional.of(course);

        // WHEN
        when(this.courseRepository.findById(anyLong())).thenReturn(ofResult);
        CourseDTO actualFindByIdResult = this.courseService.findById(1L);

        // THEN
        assertEquals(this.courseMapper.toDTO(course), actualFindByIdResult);
        verify(this.courseRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should throw NotFound exception when course not found")
    void testFindByIdNotFound() {
        when(this.courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> this.courseService.findById(123L));
        verify(this.courseRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should create a course when valid")
    void testCreate() {
        // GIVEN
        CourseDTO courseDTO = TestData.createValidCourseDTO();
        Course course = TestData.createValidCourse();
        
        // WHEN
        when(this.courseRepository.save(any())).thenReturn(course);

        // THEN
        assertEquals(this.courseMapper.toDTO(course), this.courseService.save(courseDTO));
        verify(this.courseRepository).save(any());
    }

    @Test
    @DisplayName("Should throw an exception when creating an invalid course")
    void testCreateInvalid() {
        List<CourseDTO> courses = TestData.createInvalidCoursesDTO();

        for (CourseDTO courseDTO : courses) {
            assertThrows(IllegalArgumentException.class, () -> this.courseService.save(courseDTO));
        }

        verify(this.courseRepository, times(0)).save(any());
        then(this.courseRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Should update a course when valid")
    void testUpdate() {
        // GIVEN
        Course course = TestData.createValidCourse();
        Optional<Course> ofResult = Optional.of(course);

        // WHEN
        Course course1 = TestData.createValidCourse();
        when(this.courseRepository.save(any())).thenReturn(course1);
        when(this.courseRepository.findById(anyLong())).thenReturn(ofResult);

        // THEN
        CourseDTO courseDTO = TestData.createValidCourseDTO();
        assertEquals(this.courseMapper.toDTO(course1), this.courseService.update(1L, courseDTO));
        verify(this.courseRepository).save(any());
        verify(this.courseRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should throw an exception when updating an invalid course ID")
    void testUpdateNotFound() {
        // GIVEN
        Course course = TestData.createValidCourse();
        Optional<Course> ofResult = Optional.of(course);

        // WHEN
        when(this.courseRepository.save(any())).thenThrow(new ResourceNotFoundException(123L));
        when(this.courseRepository.findById(anyLong())).thenReturn(ofResult);

        // THEN
        CourseDTO courseDTO = TestData.createValidCourseDTO();
        assertThrows(ResourceNotFoundException.class, () -> this.courseService.update(123L, courseDTO));
        verify(this.courseRepository).save(any());
        verify(this.courseRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should soft delete a course")
    void testDelete() {
        // GIVEN
        Course course = TestData.createValidCourse();

        // WHEN
        when(this.courseRepository.existsById(anyLong())).thenReturn(true);
        Mockito.doNothing().when(this.courseRepository).deleteById(anyLong());
        this.courseService.delete(course.getId());

        // THEN
        verify(this.courseRepository).existsById(anyLong());
        verify(this.courseRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should return empty when course not found - delete")
    void testDeleteNotFound() {
        // GIVEN
        Long nonExistentId = 123L;

        // WHEN
        when(this.courseRepository.existsById(anyLong())).thenReturn(false);

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> this.courseService.delete(nonExistentId));
        verify(this.courseRepository).existsById(anyLong());
        verify(this.courseRepository, times(0)).deleteById(anyLong());
    }

}