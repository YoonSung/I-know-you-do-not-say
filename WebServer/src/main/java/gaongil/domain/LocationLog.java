package gaongil.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbl_location_log")
public class LocationLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long pid;
	
	@Column(nullable=false, columnDefinition="DECIMAL(10, 8)")
	private float latitude;
	
	@Column(nullable=false, columnDefinition="DECIMAL(11, 8)")
	private float longitude;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false, updatable=false)
	private Date createdTime;
}
