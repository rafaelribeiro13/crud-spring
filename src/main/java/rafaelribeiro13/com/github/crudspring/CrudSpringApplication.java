package rafaelribeiro13.com.github.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import rafaelribeiro13.com.github.crudspring.model.Course;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository repository) {
		return args -> {
			repository.deleteAll();

			var course01 = new Course();
			course01.setId(1L);
			course01.setName("Angular");
			course01.setCategory("front-end");

			repository.save(course01);
		};
	}

}
