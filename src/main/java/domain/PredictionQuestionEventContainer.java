package domain;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class PredictionQuestionEventContainer implements Serializable {

	private Prediction prediction = new Prediction();
	private Question question = new Question();
	private Event event = new Event();
	
	public PredictionQuestionEventContainer(Prediction prediction) {
		this.prediction=prediction;
		this.question=prediction.getQuestion();
		this.event=prediction.getQuestion().getEvent();
	}
	
	public PredictionQuestionEventContainer() {
		this.prediction=null;
		this.question=null;
		this.event=null;
	}
	
	public Prediction getPredictions() {
		return prediction;
	}
	public void setPredictions(Prediction prediction) {
		this.prediction = prediction;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public String toString() {
			float f = prediction.getFee();
			return event.getEventDate() + ": " + event.getDescription() + ": " + question.getQuestion() + ": " + prediction.getPrediction() 
				+ ", x" + String.valueOf(f);
	}	
}


