package springbootandthymeleaf.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springbootandthymeleaf.com.entity.FileEntity;

public interface FileEntityRepository extends JpaRepository<FileEntity, Integer>{

}
