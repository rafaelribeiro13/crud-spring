package rafaelribeiro13.com.github.crudspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import rafaelribeiro13.com.github.crudspring.CourseRepository;
import rafaelribeiro13.com.github.crudspring.dto.CourseDTO;
import rafaelribeiro13.com.github.crudspring.dto.mapper.CourseMapper;
import rafaelribeiro13.com.github.crudspring.exception.ResourceNotFoundException;

@Service
public class CourseService {
 
    private final CourseRepository repository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository repository, CourseMapper courseMapper) {
        this.repository = repository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> findAll() {
        return repository
            .findAll()
            .stream()
            .map(courseMapper::toDTO)
            .toList();
    }

    public CourseDTO findById(@NotNull @Positive Long id) {
        return repository
            .findById(id)
            .map(courseMapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public CourseDTO save(@Valid @NotNull CourseDTO dto) {
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(dto)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO dto) {
        return repository
            .findById(id)
            .map(courseFound -> {
                courseFound.setName(dto.name());
                courseFound.setCategory(courseMapper.convertCategoryValue(dto.category()));

                return courseMapper.toDTO(repository.save(courseFound));
            })
            .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        
        repository.deleteById(id);
    }
 
}
