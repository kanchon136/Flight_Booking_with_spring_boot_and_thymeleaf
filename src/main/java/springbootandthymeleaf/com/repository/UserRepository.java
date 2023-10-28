package springbootandthymeleaf.com.repository;

import java.util.Optional;
 

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User>findByUserName(String username);

}
