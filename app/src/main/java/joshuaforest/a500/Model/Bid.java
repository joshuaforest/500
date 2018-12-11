package joshuaforest.a500.Model;

public class Bid implements Comparable<Object> {
private int num;
private String suit;
private boolean pass;
private Player p;

public Bid(int num, String suit, boolean pass, Player p) {
	this.num = num;
	this.suit = suit;
	this.pass = pass;
	this.p = p;
}

@Override
public int compareTo(Object o) {
	if(this.pass && ((Bid) o).getPass()) return 0;
	if(this.pass && !((Bid) o).getPass()) return -1;
	if(!this.pass && ((Bid) o).getPass()) return 1;
	
	
	if(num == ((Bid) o).getNum() && suit.equals(((Bid) o).getSuit())) {
		return 0;
	}
	if(num >((Bid) o).getNum()) return 1;
	if(getSuitVal()>((Bid) o).getSuitVal() && num == ((Bid) o).getNum()) return 1;
	return -1;

}

public int getSuitVal() {
	switch(suit) {
	case "Spades": return 0;
	case "Clubs": return 1;
	case "Diamonds": return 2;
	case "Hearts": return 3;
	case "No Trump": return 4;
	default: return -1;
	}
}

public int getNum() {
	return num;
}

public void setNum(int num) {
	this.num = num;
}

public String getSuit() {
	return suit;
}

public void setSuit(String suit) {
	this.suit = suit;
}

public boolean getPass() {
	return pass;
}

public void setPass(boolean p) {
	this.pass = p;
}

public String toString() {
	if(pass) {
		return "pass";
	} else {
		return num + " "+suit;
	}
}

public Player getPlayer() {
	return p;
}

public int getValue() {
	int addedScore = 0;
	if(pass) return 0;
	if(suit.equals("Spades")) addedScore = 40;
	else if(suit.equals("Clubs")) addedScore = 60;
	else if(suit.equals("Diamonds")) addedScore = 80;
	else if(suit.equals("Hearts")) addedScore = 100;
	else addedScore = 120;
	return addedScore + (num-6)*100;
}



}
