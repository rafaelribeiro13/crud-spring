package rafaelribeiro13.com.github.crudspring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import rafaelribeiro13.com.github.crudspring.CourseRepository;
import rafaelribeiro13.com.github.crudspring.dto.CourseDTO;
import rafaelribeiro13.com.github.crudspring.dto.CoursePageDTO;
import rafaelribeiro13.com.github.crudspring.dto.mapper.CourseMapper;
import rafaelribeiro13.com.github.crudspring.exception.ResourceNotFoundException;
import rafaelribeiro13.com.github.crudspring.model.Course;

@Service
public class CourseService {
 
    private final CourseRepository repository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository repository, CourseMapper courseMapper) {
        this.repository = repository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO findAll(
        @PositiveOrZero int page, 
        @Positive @Max(100) int pageSize
    ) {
        Page<Course> pageCourse = repository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> courses = pageCourse.get().map(courseMapper::toDTO).toList();
        return new CoursePageDTO(courses, pageCourse.getTotalElements(), pageCourse.getTotalPages());
    }
    
    public CourseDTO findById(@NotNull @Positive Long id) {
        return repository
            .findById(id)
            .map(courseMapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public CourseDTO save(@Valid @NotNull CourseDTO dto) {
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(dto)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO dto) {
        return repository
            .findById(id)
            .map(courseFound -> {
                Course course = courseMapper.toEntity(dto);

                courseFound.setName(dto.name());
                courseFound.setCategory(courseMapper.convertCategoryValue(dto.category()));
                courseFound.clearLessons();

                course.getLessons().forEach(courseFound::addLesson);

                return courseMapper.toDTO(repository.save(courseFound));
            })
            .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        
        repository.deleteById(id);
    }
 
}
