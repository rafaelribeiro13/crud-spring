package rafaelribeiro13.com.github.crudspring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import rafaelribeiro13.com.github.crudspring.model.Course;
import rafaelribeiro13.com.github.crudspring.service.CourseService;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    
    private final CourseService service;
    
    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Course>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
       return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Course> save(@RequestBody @Valid Course course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Course> update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course) {
        return ResponseEntity.ok().body(service.update(id, course));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull @Positive Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
