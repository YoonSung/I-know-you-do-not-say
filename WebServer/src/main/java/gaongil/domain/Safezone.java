package gaongil.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Safezone {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	//TODO Intergration with LocationLog (embeddable)
	@Column(nullable=false, columnDefinition="DECIMAL(10, 8)")
	private float latitude;
	
	@Column(nullable=false, columnDefinition="DECIMAL(11, 8)")
	private float longitude;
	
	@Column(nullable=false)
	private int distance;
}
