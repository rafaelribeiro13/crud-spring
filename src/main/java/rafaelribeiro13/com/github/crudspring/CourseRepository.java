package rafaelribeiro13.com.github.crudspring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rafaelribeiro13.com.github.crudspring.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
    
}
