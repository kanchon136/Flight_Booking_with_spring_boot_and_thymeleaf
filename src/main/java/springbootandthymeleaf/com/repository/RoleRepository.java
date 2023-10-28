package springbootandthymeleaf.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.Role;

public interface RoleRepository  extends JpaRepository<Role, Long>{

}
