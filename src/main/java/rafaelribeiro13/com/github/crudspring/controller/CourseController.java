package rafaelribeiro13.com.github.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import rafaelribeiro13.com.github.crudspring.CourseRepository;
import rafaelribeiro13.com.github.crudspring.model.Course;
import org.springframework.web.bind.annotation.PutMapping;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    private final CourseRepository repository;
    
    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
       return repository
            .findById(id)
            .map(course -> ResponseEntity.ok().body(course))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> save(@RequestBody @Valid Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(course));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
        return repository
            .findById(id)
            .map(courseFound -> {
                courseFound.setName(course.getName());
                courseFound.setCategory(course.getCategory());

                Course updated = repository.save(courseFound);
                return ResponseEntity.ok().body(updated);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        return repository
            .findById(id)
            .map(course -> {
                repository.delete(course);
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
