package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisteredClient extends Person implements Serializable {

	private float balance;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	//@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)

    private Vector<Bet> movementsBet = new Vector<Bet>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Movements> movements=new Vector<Movements>();
	private boolean replicable;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private HashMap<RegisteredClient, ReplicatedProperties> hmap = new HashMap<RegisteredClient, ReplicatedProperties>();
	private int tokens;
	
	public RegisteredClient() {
		super();
	}
	

	public RegisteredClient(String name, String surname, Date birthDate, String dNI, String email, String password, String currentAccount, boolean replicable) {
		super(name, surname, birthDate, dNI, email, password, currentAccount);
		this.replicable=replicable;
		this.balance = 0;
		this.tokens = 0;
	    movements=new Vector<Movements>();

	}
	
	/**
	 * get client's money amount
	 * @return balance
	 */
	public float getBalance() {
		return balance;
	}

	/**
	 * set client's amount of money
	 * @param balance
	 */
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	/**
	 * get amount of tokens the client has
	 * @return amount of tokens
	 */
	public int getTokens() {
		return tokens;
	}
	
	/**
	 * set amount of tokens the client has
	 * @param amount of tokens
	 */
	public void setTokens(int t) {
		this.tokens=t;
	}
	
	public HashMap<RegisteredClient, ReplicatedProperties> getHmap() {
		return hmap;
	}
	
	public void addHash(RegisteredClient rc, ReplicatedProperties percentage) {
		hmap.put(rc, percentage);
	}
	
	public Vector<Bet> getMovementsBet(){
		/*Vector<Bet> r = new Vector<Bet>(); 
		for (Bet b : movementsBet) {
			if (b.getMoney()!=0.0) r.add(b);
			
		}*/
		//return r;
		return this.movementsBet;
	}
	
	public Vector<Movements> getMovements(){
		return movements;
	}
	
	public void setMovements(Vector<Bet> movements) {
		this.movementsBet=movements;
	}
	
	public void addMovements(String description,float money) {
		Movements move = new Movements ( description, money, new Date());
		movements.add(move);
	}
	
	public void addMovements1(String description, float money, Date date) {
		Movements move = new Movements ( description, money, date);
		movements.add(move);
	}
	
	public Movements addMovements2(String description, float money, Date date, String number ) {
		Movements move = new Movements ( description, money, date, number);
		movements.add(move);
		return move;
	}
	
	public void addBet(Bet bet) {
		movementsBet.add(bet);
	}
	public Bet addBet(float money, Vector<Prediction> preds) {
		Bet b=new Bet(money, preds);
		b.setClient(this);
		movementsBet.add(b);
		return b;
	}
	
	public String toString(){
		return "+balance";
	}
	
	public boolean getReplicable() {
		return replicable;
	}
	
	public void setReplicable(boolean p) {
		this.replicable=p;
	}
	
	public Vector<Movements> viewAllMovements(){
		Vector<Movements> move = new Vector<Movements>();
		java.util.Date dtoday= new java.util.Date();
		Date dfrom;
		for(int i=0; i<movements.size(); i++) {
			dfrom=movements.get(i).getDate();
			if(dfrom.before(dtoday) || dfrom.equals(dtoday)) {
				move.add(movements.get(i));
			}
		}
		return move;
	}
	
	public Vector<Movements> viewMovements(Date date1, Date date2){
		Vector<Movements> move = new Vector<Movements>();
		Date date3;
		for(int i=0; i<movements.size(); i++) {
			date3=movements.get(i).getDate();
			if((date1.before(date2) || date1.equals(date2)) && (date3.after(date1) || date3.equals(date1)) && (date3.before(date2)|| date3.equals(date2))) {
				move.add(movements.get(i));
			}
		}
		return move;
	}

//	public Vector<Bet> viewAllBets() {
//		Vector<Bet> betMovements =new Vector<Bet>();
//		java.util.Date dtoday= new java.util.Date();
//		Date dfrom;
//		for(int i=0; i<movementsBet.size(); i++) {
//			dfrom=movementsBet.get(i).getPrediction().getQuestion().getEvent().getEventDate();
//			if(dfrom.before(dtoday) || dfrom.equals(dtoday)) {
//				betMovements.add(movementsBet.get(i));
//			}
//		}
//		return betMovements;	
//	}
//	
//	public Vector<Bet> viewBets(Date date1, Date date2) {
//		Vector<Bet> betMovements =new Vector<Bet>();
//		Date date3;
//		for(int i=0; i<movementsBet.size(); i++) {
//			date3=movementsBet.get(i).getPrediction().getQuestion().getEvent().getEventDate();
//			if((date1.before(date2) || date1.equals(date2)) && (date3.after(date1) || date3.equals(date1)) && (date3.before(date2)|| date3.equals(date2))) {
//				betMovements.add(movementsBet.get(i));
//			}
//		}
//		return betMovements;
//	}
//	
//	public Vector<Bet> viewAllProfits() {
//		Vector<Bet> betMovements =new Vector<Bet>();
//		java.util.Date dtoday= new java.util.Date();
//		Date dfrom;
//		for(int i=0; i<movementsBet.size(); i++) {
//			dfrom=movementsBet.get(i).getPrediction().getQuestion().getEvent().getEventDate();
//			if(dfrom.before(dtoday) || dfrom.equals(dtoday)){
//					if( movementsBet.get(i).isWin()==true)betMovements.add(movementsBet.get(i));
//			}
//		}
//		return betMovements;
//			
//	}
//	
//	public Vector<Bet> viewProfits(Date date1, Date date2) {
//		Vector<Bet> betMovements =new Vector<Bet>();
//		Date date3;
//		for(int i=0; i<movementsBet.size(); i++) {
//			date3=movementsBet.get(i).getPrediction().getQuestion().getEvent().getEventDate();
//			if(date1.before(date2) || date1.equals(date2)) {
//				if( movementsBet.get(i).isWin()==true && (date3.after(date1) || date3.equals(date1)) && (date3.before(date2)|| date3.equals(date2))) betMovements.add(movementsBet.get(i));
//			}			
//		}
//		return betMovements;
//	}



}
