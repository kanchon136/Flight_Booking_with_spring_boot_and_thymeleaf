package springbootandthymeleaf.com.entity;

import java.time.LocalDate;
 
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;





@Entity
@Table(name="flight")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"aircraft","departureAirport","destinationAirport","passangers"})
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long flightId;
	
	private String flightNumber;
	
	@ManyToOne
	private Airport departureAirport;
	
	@ManyToOne
	private Airport destinationAirport;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate departureDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate arivalDate; // arrivalDate
	
	private String departureTime;
	
	private String arivalTime;
	
	private double flightCharge;
	
	@ManyToOne
	private Aircraft aircraft;
	
	@OneToMany(mappedBy="flight")
	private List<Passanger> passangers;
	
	
	
	
	

}
