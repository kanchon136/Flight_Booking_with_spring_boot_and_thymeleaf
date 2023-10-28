package springbootandthymeleaf.com.entity;

import java.util.List;
 

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="userid")
	private Long userId;
	
	@Column(name="username", nullable=false)
	@NotBlank(message="* user name is require")
	private String userName;
	
	@Column(name="email", nullable=false)
	@NotBlank(message="* email is mandatory")
	//@Email()
	private String email;
	
	@Column(name="password", nullable=false)
	@NotBlank(message="* password not empty")
	@Size(min=8)
	private String password;
	
	@ManyToMany(cascade= CascadeType.MERGE)
	@JoinTable(name="user_roles",
	joinColumns= {@JoinColumn(name="user_id" , referencedColumnName="userid")},
	inverseJoinColumns= {@JoinColumn(name="role_id", referencedColumnName="roleid")})
	private List<Role> roles;
	
	 
	

}
