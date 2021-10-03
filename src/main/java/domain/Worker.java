package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Worker extends Person implements Serializable{

	float salary;

	public Worker() {
		super();
	}
	
	public Worker(String name, String surname, Date birthDate, String dNI, String email, String password, String currentAccount) {
		super(name, surname, birthDate, dNI, email, password, currentAccount);
		this.salary=0;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}


}
