package springbootandthymeleaf.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
