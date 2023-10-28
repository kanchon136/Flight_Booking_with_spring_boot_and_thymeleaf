package springbootandthymeleaf.com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="student", indexes= {
		@Index(name="indx_st_name", columnList="student_name")
})
public class Student{    
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="student_id")
	private long StudentId;
	
	@Column(name="student_name")
	private String studentName;
	
	@Column(name="student_grade")
	private String grade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private Teacher teacher;

}
