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

	/**
	 * for purposes of sorting hands
	 * @return if this card is greater than c
	 */
	public boolean compareTo(Card c){
	    if(c.isJoker) return false;
	    if(this.isJoker) return true;
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
