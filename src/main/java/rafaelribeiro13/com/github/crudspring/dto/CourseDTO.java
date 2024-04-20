package rafaelribeiro13.com.github.crudspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import rafaelribeiro13.com.github.crudspring.model.Course;

public record CourseDTO(
    @JsonProperty("_id")
    Long id, 
    
    @NotBlank
    @Size(min = 5, max = 100)
    String name, 

    @NotNull
    @Size(max = 12)
    @Pattern(regexp = "back-end|front-end")
    String category
) {

    public static CourseDTO from(Course course) {
        return new CourseDTO(
            course.getId(), 
            course.getName(), 
            course.getCategory()
        );
    }

    public static Course toEntity(CourseDTO dto) {
        var course = new Course();
        course.setId(dto.id());
        course.setName(dto.name());
        course.setCategory(dto.category());
        course.setStatus("Ativo");

        return course;
    }

}
