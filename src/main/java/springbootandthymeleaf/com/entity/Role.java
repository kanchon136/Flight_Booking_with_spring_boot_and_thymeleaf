package springbootandthymeleaf.com.entity;

 
 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
 
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="roleid")
	private Long roleId;
	
	@Column(name="rolename", nullable=false)
	@NotBlank(message="* Role name is required")
	private String roleName;
	
	//@ManyToMany(mappedBy="role")
	//private List<User> users;
	
	 

}
