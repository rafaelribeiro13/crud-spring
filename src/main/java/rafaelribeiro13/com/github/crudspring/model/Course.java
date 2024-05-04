package rafaelribeiro13.com.github.crudspring.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import rafaelribeiro13.com.github.crudspring.enums.Category;
import rafaelribeiro13.com.github.crudspring.enums.Status;
import rafaelribeiro13.com.github.crudspring.enums.converters.CategoryConverter;
import rafaelribeiro13.com.github.crudspring.enums.converters.StatusConverter;

@SQLDelete(sql = "UPDATE courses SET status='Inactive' WHERE id=?")
@SQLRestriction("status <> 'Inactive'")
@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;
    
    @NotBlank
    @Size(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;
    
    @NotNull
    @Column(length = 12, nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotNull
    @Column(length = 12, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {
    }

    public Course(Long id, @NotBlank @Size(min = 5, max = 100) String name, @NotNull Category category,
            @NotNull Status status, List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.lessons = lessons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(
        @NotBlank
        @Size(min = 5, max = 100) 
        String name
    ) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(@NotNull Status status) {
        this.status = status;
    }

    public List<Lesson> getLessons() {
        return Collections.unmodifiableList(this.lessons);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public void addAllLessons(List<Lesson> lessons) {
        this.lessons.addAll(lessons);
    }

    public void removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
    }

    public void clearLessons() {
        this.lessons.clear();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Course other = (Course) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (category != other.category)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + 
            ", name=" + name + 
            ", category=" + category + 
            ", status=" + status + 
            ", lessons=" + lessons + 
            "]";
    }

}
