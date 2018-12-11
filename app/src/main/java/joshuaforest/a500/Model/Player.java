package joshuaforest.a500.Model;

import java.util.ArrayList;

public abstract class Player {

	protected Game g;
	protected ArrayList<Card> hand;
	protected int index;
	
	public void setGame(Game g){
		this.g = g;
		this.hand = new ArrayList<Card>();
	}

	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public void printHand() {
		for(Card c : hand) {
			System.out.println(c.toString());
		}
	}
	
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}

	public int getIndex() {return this.index;}

	public abstract void needBid();

    public abstract void needSuit();

    public abstract void needCard();
	
	public abstract void takeBlind(ArrayList<Card> blind);

	public boolean checkEligiblePlay(Card c){
		if(hand.indexOf(c)==-1) return false;
		if(index == g.lead){
			return true;
		}
		Card leadCard = g.playedCards[g.lead];
		if(g.isTrump(leadCard)){
			if(hasTrump()) return g.isTrump(c);
			return true;
		} else{
			if(hasSuit(leadCard.getSuit()) && !g.isTrump(c)){
				return c.getSuit().equals(leadCard.getSuit());
			}
			return true;
		}
	}

	public boolean hasTrump(){
		for(Card c:hand){
			if(g.isTrump(c)) return true;
		}
		return false;
	}

	public boolean hasSuit(String suit){
		if(suit.equals(g.getBid().getSuit())) return hasTrump();
		else{
			for(Card c:hand){
				if(!g.isTrump(c)){
					if(c.getSuit().equals(suit)) return true;
				}
			}
		}
		return false;
	}
	
}
