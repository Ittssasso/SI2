package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso ({RegisteredClient.class, Admin.class, Worker.class})
public abstract class Person implements Serializable{

	String name;
	String surname;
	Date birthDate;
	String DNI;
	@Id
	@XmlID
	String email;
	String password;
	String currentAccount;

	protected Person() {
		super();
	}
	
	protected Person(String name, String surname, Date birthDate, String dNI, String email, String password, String currentAccount) {
		super();
		this.name = name;
		this.surname = surname;
		this.birthDate=birthDate;
		DNI = dNI;
		this.email = email;
		this.password = password;
		this.currentAccount = currentAccount;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date bD) {
		this.birthDate = bD;
	}
	
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCurrentAccount() {
		return currentAccount;
	}
	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}



}
