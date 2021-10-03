package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends Person implements Serializable {

	public Admin() {
		super();
	}
	
	public Admin(String name, String surname, Date birthDate, String dNI, String email, String password, String currentAccount) {
		super(name, surname, birthDate, dNI, email, password, currentAccount);
	}
	
}
