package rafaelribeiro13.com.github.crudspring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CourseDTO(
    @JsonProperty("_id")
    Long id, 
    
    @NotBlank
    @Size(min = 5, max = 100)
    String name, 

    @NotNull
    @Size(max = 12)
    @Pattern(regexp = "back-end|front-end")
    String category,

    List<LessonDTO> lessons
) {
    
}