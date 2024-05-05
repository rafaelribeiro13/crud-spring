package rafaelribeiro13.com.github.crudspring.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rafaelribeiro13.com.github.crudspring.enums.Category;
import rafaelribeiro13.com.github.crudspring.enums.validation.ValueOfEnum;

public record CourseDTO(
    @JsonProperty("_id")
    Long id, 
    
    @NotBlank
    @Size(min = 5, max = 100)
    String name, 

    @NotNull
    @Size(max = 12)
    @ValueOfEnum(enumClass = Category.class)
    String category,

    @NotNull
    @NotEmpty
    @Valid
    List<LessonDTO> lessons
) {
    
}