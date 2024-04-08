package rafaelribeiro13.com.github.crudspring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public List<Course> findAll() {
        return repository.findAll();
    }

}
