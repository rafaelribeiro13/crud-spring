package rafaelribeiro13.com.github.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rafaelribeiro13.com.github.crudspring.CourseRepository;
import rafaelribeiro13.com.github.crudspring.model.Course;

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
    public ResponseEntity<Course> findById(@PathVariable Long id) {
       return repository
            .findById(id)
            .map(course -> ResponseEntity.ok().body(course))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> save(@RequestBody Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(course));
    }

}
