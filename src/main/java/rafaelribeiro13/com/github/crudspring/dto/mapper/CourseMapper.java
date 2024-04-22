package rafaelribeiro13.com.github.crudspring.dto.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import rafaelribeiro13.com.github.crudspring.dto.CourseDTO;
import rafaelribeiro13.com.github.crudspring.dto.LessonDTO;
import rafaelribeiro13.com.github.crudspring.enums.Category;
import rafaelribeiro13.com.github.crudspring.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course entity) {
        if (entity == null) {
            return null;
        }

        List<LessonDTO> lessons = entity.getLessons().stream()
            .map(les -> new LessonDTO(les.getId(), les.getName(), les.getYoutubeUrl()))
            .toList();

        return new CourseDTO(
            entity.getId(), 
            entity.getName(), 
            entity.getCategory().getValue(),
            lessons
        );
    }

    public Course toEntity(CourseDTO dto) {
        if (dto == null) {
            return null;
        }
        
        var entity = new Course();

        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setName(dto.name());
        entity.setCategory(convertCategoryValue(dto.category()));


        return entity;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }

        return switch (value) {
            case "back-end" -> Category.BACK_END;
            case "front-end" -> Category.FRONT_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
    
}
