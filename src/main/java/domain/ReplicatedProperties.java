package domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplicatedProperties {
	
	private int percentage;	
	
	public ReplicatedProperties() {
		super();
	}
	public ReplicatedProperties(int percen) {
		this.percentage=percen;
	}
	
	public int getPercentage() {
		return percentage;
	}
	
	public void setPercentage(int percen) {
		this.percentage=percen;
	}
}
