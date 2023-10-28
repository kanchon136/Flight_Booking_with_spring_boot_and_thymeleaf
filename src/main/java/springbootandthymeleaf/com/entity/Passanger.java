package springbootandthymeleaf.com.entity;

import javax.persistence.Entity;
 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="passanger")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passanger {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long passangerId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private  String passportNumber;
	private String email;
	private String address;
	
	@ManyToOne
	private Flight flight;
	
	
	
	

}
