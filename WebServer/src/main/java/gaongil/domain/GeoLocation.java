package gaongil.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class GeoLocation {
	@Column(nullable=false, columnDefinition="DECIMAL(10, 8)")
	private float latitude;
	
	@Column(nullable=false, columnDefinition="DECIMAL(11, 8)")
	private float longitude;
}
