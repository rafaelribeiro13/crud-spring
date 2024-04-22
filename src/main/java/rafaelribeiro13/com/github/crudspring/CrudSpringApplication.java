package rafaelribeiro13.com.github.crudspring;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import rafaelribeiro13.com.github.crudspring.enums.Category;
import rafaelribeiro13.com.github.crudspring.model.Course;
import rafaelribeiro13.com.github.crudspring.model.Lesson;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository repository) {
		return args -> {
			repository.deleteAll();
			
			var course = new Course();
			course.setId(1L);
			course.setName("Angular");
			course.setCategory(Category.FRONT_END);

			var lesson01 = new Lesson();
			lesson01.setName("Introdução");
			lesson01.setYoutubeUrl("https://youtu.be/Nb4uxLxdvxo?list=PLGxZ4Rq3BOBpwaVgAPxTxhdX_TfSVlTcY");
			lesson01.setCourse(course);

			var lesson02 = new Lesson();
			lesson02.setName("Github");
			lesson02.setYoutubeUrl("https://www.youtube.com/watch?v=TsaLQAetPLU");
			lesson02.setCourse(course);
			
			course.getLessons().addAll(List.of(lesson01, lesson02));
			repository.save(course);
		};
	}

}
