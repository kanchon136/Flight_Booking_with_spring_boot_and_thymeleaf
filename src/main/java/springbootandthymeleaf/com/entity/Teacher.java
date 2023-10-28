package springbootandthymeleaf.com.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="teacher", indexes= {
	@Index(name="indx_teacher_name", columnList="teacher_name")	
})
public class Teacher {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="teacher_id")
	private long teacherId;
    
	@Column(name="teacher_name")
	private String teacherName;
	
	@Column(name="teacher_address")
	private String address;
	
	@OneToMany(mappedBy="teacher" , fetch= FetchType.EAGER, cascade= CascadeType.ALL )
	private List<Student> students;
	

}
