package springbootandthymeleaf.com.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="aircraft")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Aircraft {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long aircraftId;
	private String manufacturer;
	private String model;
	private Integer numberOfSeats;
	
	@OneToMany(mappedBy="aircraft")
	private List<Flight> flights;
}
