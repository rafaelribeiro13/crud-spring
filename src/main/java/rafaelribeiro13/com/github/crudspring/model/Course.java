package rafaelribeiro13.com.github.crudspring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@SQLDelete(sql = "UPDATE courses SET status='Inativo' WHERE id=?")
@SQLRestriction("status <> 'Inativo'")
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
    @Size(max = 12)
    @Pattern(regexp = "back-end|front-end")
    @Column(length = 12, nullable = false)
    private String category;

    @NotNull
    @Size(max = 12)
    @Pattern(regexp = "Ativo|Inativo")
    @Column(length = 12, nullable = false)
    private String status = "Ativo";
    
}
