package springbootandthymeleaf.com.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
		
@Entity
@Table(name="airport")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Airport {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer airportId;
    private String airportCode;
    private String airportName;
    private String city;
    private String state;
    private String country;
    
    @OneToMany(mappedBy="departureAirport")
    private List<Flight> flights ;


 

    

    
}
