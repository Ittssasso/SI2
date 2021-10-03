package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable {

	@Id @GeneratedValue
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer betNumber;
	private float money = 0; 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Prediction> predictions=new Vector<Prediction>();
	@OneToOne 
	@XmlIDREF
	private RegisteredClient client = null;
	private boolean win = false;
	private boolean multiple = false;
	private boolean locked = false;
	private int feeMultiplier = 1;
	@OneToOne
	@XmlIDREF
	private RegisteredClient replicatedClient = null;
	
	public Bet() {
		super();
	}
	
	public Bet(float money, Vector<Prediction> prediction) {
		this.money=money;
		this.predictions=prediction;
		win=false;
		if(prediction.size()==1)
			this.multiple=false;
		else this.multiple=true;
		locked=false;
		feeMultiplier=1;
		replicatedClient = null;
	}
	
	
	/**
	 * Get the  number of the bet
	 * 
	 * @return the bet number
	 */
	public Integer getBetNumber() {
		return this.betNumber;
	}

	/**
	 * Set the bet number to a prediction
	 * 
	 * @param betNumber to be setted
	 */
	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}
	
	/**
	 * Get the betted money
	 * 
	 * @return amoount of money betted
	 */
	public float getMoney() {
		return money;
	}

	/**
	 * Set the amount of money to bet
	 * 
	 * @param money to be betted
	 */
	public void setMoney(float money) {
		this.money = money;
	}
	
	/**
	 * Get the prediction associated to the bet
	 * 
	 * @return the associated prediciton
	 */
	public Vector<Prediction> getPredictions() {
		return predictions;
	}

	/**
	 * Set the prediction associated to the bet
	 * 
	 * @param prediction to associate to the bet
	 */
	public void setPredicitons(Vector<Prediction> prediction) {
		this.predictions = prediction;
	}
	
	/**
	 * Get the client associated to the bet
	 * 
	 * @return the associated client
	 */
	public RegisteredClient getClient() {
		return client;
	}

	/**
	 * Set the client associated to the bet
	 * 
	 * @param client who betted
	 */
	public void setClient(RegisteredClient client) {
		this.client = client;
	}
	
	/**
	 * Get if the bet has been a win or a loss
	 * 
	 * @return is a win
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * Set if the bet is a win or a loss
	 * 
	 * @param win
	 */
	public void setWin(boolean win) {
		this.win = win;
	}
	
	/**
	 * Get if the bet is part of a multiple bet
	 * 
	 * @return bet is multiple or not
	 */
	public boolean getMultiple() {
		return multiple;
	}
	
	/**
	 * Set if the bet is a multiple bet or not
	 * 
	 * @param multiple
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	
	/**
	 * Get if the bet is locked (cannot be deleted)
	 * 
	 * @return bet is locked or not
	 */
	public boolean getLocked() {
		return locked;
	}
	
	/**
	 * Set if the bet is locked. In other words, it cannot be deleted
	 * 
	 * @param locked
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	/**
	 * Get the number which will multiply the fee when the bet is won
	 * 
	 * @return feeMultiplier
	 */
	public int getFeeMultiplier() {
		return feeMultiplier;
	}
	
	/**
	 * Set the number which will multiply the fee when the bet is won
	 */
	public void setFeeMultiplier() {
		feeMultiplier = (int) ((1 + Math.random() * 2));
		setLocked(true);
	}

	public String toString() {
		return String.valueOf(money);
	}

	public RegisteredClient getReplicatedClient() {
		return replicatedClient;
	}
	
	public void setReplicatedClient(RegisteredClient rC) {
		replicatedClient = rC;
	}
	
}


