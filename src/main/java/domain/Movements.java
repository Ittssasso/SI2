package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Movements implements Serializable {

	@Id
	@GeneratedValue
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer movementsNumber;
	private String description;
	private float money;
	private Date date;
	private String number;

	public Movements(String description, float money, Date date) {
		super();
		this.description = description;
		this.money = money;
		this.date = date;
	}
	
	public Movements(String description, float money, Date date, String number) {
		super();
		this.description=description;
		this.money = money;
		this.date=date;
		this.number=number;
	}

	public Movements() {
		super();
	}

	public Integer getMovementsNumber() {
		return movementsNumber;
	}

	public void setMovementsNumber(Integer movementsNumber) {
		this.movementsNumber = movementsNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "Movements [description=" + description + ", money=" + money + ", date=" + date + "]";
	}

}
