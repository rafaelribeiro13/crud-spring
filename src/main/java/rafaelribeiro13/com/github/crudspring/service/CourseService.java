package rafaelribeiro13.com.github.crudspring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import rafaelribeiro13.com.github.crudspring.CourseRepository;
import rafaelribeiro13.com.github.crudspring.model.Course;

@Service
public class CourseService {
 
    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Optional<Course> findById(@PathVariable @NotNull @Positive Long id) {
        return repository.findById(id);
    }

    public Course save(@Valid Course course) {
        return repository.save(course);
    }

    public Optional<Course> update(@NotNull @Positive Long id, @Valid Course course) {
        return repository
            .findById(id)
            .map(courseFound -> {
                courseFound.setName(course.getName());
                courseFound.setCategory(course.getCategory());

                return repository.save(courseFound);
            });
    }

    public boolean delete(@NotNull @Positive Long id) {
        return repository
            .findById(id)
            .map(course -> {
                repository.deleteById(course.getId());
                return true;
            })
            .orElse(false);
    }
 

    
}
