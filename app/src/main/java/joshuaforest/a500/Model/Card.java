package joshuaforest.a500.Model;

public class Card {
	private int rank;
	private String suit;
	private boolean isJoker;
	
	public Card(int rank, String suit, boolean isJoker) {
		this.rank = rank;
		this.suit = suit;
		this.isJoker = isJoker;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public boolean getIsJoker() {
		return isJoker;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public String toString() {
		if(isJoker) {
			return "Joker";
		}
		switch(rank) {
		case 11: return "Jack of "+suit;
		case 12: return "Queen of "+suit;
		case 13: return "King of "+suit;
		case 14: return "Ace of "+suit;
		}
		return rank+" of "+suit;
	}

	public String getFileName(){
		if(isJoker) {
			return "red_joker";
		}
		String[] ranks = {"four","five","six","seven","eight","nine",
                "ten","jack","queen","king","ace"};
		String rank = ranks[this.rank-4];
		String name = rank + "_of_" + this.suit.toLowerCase();
		return name;
	}


	private boolean isTrump(String trump) {
		if(getIsJoker()) return true;
		if(trump.equals("Hearts")) {
			if(getSuit().equals("Hearts") || (getSuit().equals("Diamonds")&&getRank()==11)) return true;
		}
		if(trump.equals("Diamonds")) {
			if(getSuit().equals("Diamonds") || (getSuit().equals("Hearts")&&getRank()==11)) return true;
		}
		if(trump.equals("Spades")) {
			if(getSuit().equals("Spades") || (getSuit().equals("Clubs")&&getRank()==11)) return true;
		}
		if(trump.equals("Clubs")) {
			if(getSuit().equals("Clubs") || (getSuit().equals("Spades")&&getRank()==11)) return true;
		}
		return false;
	}

	/**
	 * for purposes of sorting hands
	 * @return if this card is greater than c
	 */
	public boolean compareTo(Card c, String trump){
	    if(c.isJoker) return false;
	    if(this.isJoker) return true;
	    if(c.isTrump(trump) && !this.isTrump(trump)) return false;
		if(!c.isTrump(trump) && this.isTrump(trump)) return true;
		if(c.isTrump(trump) && this.isTrump(trump)){
			if(this.rank == 11 && this.suit.equals(trump)) return true;
			if(c.rank == 11 && c.suit.equals(trump)) return false;
			if(this.rank == 11) return true;
			if(c.rank == 11) return false;
		}
	    if(c.suit.equals(this.suit)){
	    	return this.rank > c.rank;
		}
		if(this.suit.equals("Spades")) {
			return false;
		} else if(this.suit.equals("Clubs")){
			return c.suit.equals("Spades");
		} else if(this.suit.equals("Diamonds")){
			return c.suit.equals("Clubs") || c.suit.equals("Spades");

		} else if(this.suit.equals("Hearts")){
			return true;
		}
		return false;
	}
	
}
