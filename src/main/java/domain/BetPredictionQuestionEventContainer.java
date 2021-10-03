package domain;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class BetPredictionQuestionEventContainer implements Serializable {

	private Bet bet;
	private Vector<Prediction> prediction = new Vector<Prediction>();
	private Vector<Question> question = new Vector<Question>();
	private Vector<Event> event = new Vector<Event>();
	
	public BetPredictionQuestionEventContainer(Bet bet) {
		this.bet=bet;
		this.prediction=bet.getPredictions();
		for(int i=0; i<bet.getPredictions().size(); i++) {
			Question q = bet.getPredictions().get(i).getQuestion();
			this.question.add(q);
			this.event.add(q.getEvent());
		}
	}
	public BetPredictionQuestionEventContainer() {
		this.bet=null;
		this.prediction=null;
		this.question=null;
		this.event=null;
	}
	public Bet getBet() {
		return bet;
	}
	public void setBet(Bet bet) {
		this.bet = bet;
	}
	public Vector<Prediction> getPredictions() {
		return prediction;
	}
	public void setPredictions(Vector<Prediction> prediction) {
		this.prediction = prediction;
	}
	public Vector<Question> getQuestion() {
		return question;
	}
	public void setQuestion(Vector<Question> question) {
		this.question = question;
	}
	public Vector<Event> getEvent() {
		return event;
	}
	public void setEvent(Vector<Event> event) {
		this.event = event;
	}
	public boolean getMultiple() {
		if(bet.getPredictions().size()==1)
			return false;
		else
			return true;
	}
	public String toString() {
		if(prediction.size()==1) {
			float f = prediction.get(0).getFee()*bet.getFeeMultiplier();
			return event.get(0).getEventDate() + ": " + event.get(0).getDescription() + ": " + question.get(0).getQuestion() + ": " + prediction.get(0).getPrediction() + ", x"
					+ String.valueOf(f) + "; " + ResourceBundle.getBundle("Etiquetas").getString("Bet") + ": "+ bet.toString();
		}
		else
			return ResourceBundle.getBundle("Etiquetas").getString("multipleNumber") + ": " + bet.getBetNumber() + ". " + ResourceBundle.getBundle("Etiquetas").getString("moneyInBet") + ": " + bet.getMoney();
	}

	
}


