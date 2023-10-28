package springbootandthymeleaf.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.Student;

public interface StudentRepository  extends JpaRepository<Student, Long>{

}
