package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Prediction implements Serializable{
	@Id 
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer predictionNumber;
	private String prediction; 
	private float fee;
	@XmlIDREF
	private Question question;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Bet> bets = new Vector<Bet>();
	
	public Prediction() {
		super();
	}
	
	public Prediction(String prediction, float fee, Question question) {
		this.prediction=prediction;
		this.fee=fee;
		this.question=question;
	}
	
	/**
	 * Get the  number of the prediction
	 * 
	 * @return the prediction number
	 */
	public Integer getPredictionNumber() {
		return predictionNumber;
	}

	/**
	 * Set the prediction number
	 * 
	 * @param predictionNumber to be setted
	 */
	public void setPredictionNumber(Integer predictionNumber) {
		this.predictionNumber = predictionNumber;
	}


	/**
	 * Get the prediction description of the bet
	 * 
	 * @return the bet prediction
	 */

	public String getPrediction() {
		return prediction;
	}


	/**
	 * Set the prediction description of the bet
	 * 
	 * @param prediction to be setted
	 */	
	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}



	/**
	 * Get the fee of the bet's prediction
	 * 
	 * @return the fee
	 */
	
	public float getFee() {
		return fee;
	}


	/**
	 * Get the fee of the bet's prediction
	 * 
	 * @param  fee of the bet's prediction
	 */

	public void setFee(float fee) {
		this.fee = fee;
	}


	/**
	 * Get the question associated to the prediction
	 * 
	 * @return the associated question
	 */
	public Question getQuestion() {
		return question;
	}



	/**
	 * Set the question associated to the prediction
	 * 
	 * @param question to associate to the prediction
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	/**
	 * This method returns all the bets for the prediction
	 * @return vector of bets
	 */
	public Vector<Bet> getBets() {
		return bets;
	}

	/**
	 * This method sets bets for this prediction
	 * @param predictions for the question
	 */
	public void setBets(Vector<Bet> bets) {
		this.bets=bets;
	}
	
	public void addBet(Bet bet) {
		bets.add(bet);
	}
	
}
