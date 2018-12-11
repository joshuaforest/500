package joshuaforest.a500;

import java.util.ArrayList;

import joshuaforest.a500.Model.Bid;
import joshuaforest.a500.Model.Card;
import joshuaforest.a500.Model.Player;

public class DummyPlayer extends Player {

	@Override
	public Card playCard() {
		int index = (int) (Math.random()*this.hand.size());
		Card c = this.hand.get(index);
		if(checkEligiblePlay(c)) return this.hand.remove(index);
		else return playCard();
	}

	@Override
	public Bid bid() {
		String[] suits = {"Spades","Clubs","Diamonds","Hearts","No Trump"};
		if(Math.random()>.5) {
			return new Bid(0,"none",true, this);
		} else {
			String suit = suits[(int)( Math.random()*5)];
			int num = (int)(Math.random()*5)+6;
			return new Bid(num, suit, false, this);
		}

	}
	
	@Override
	public void takeBlind(ArrayList<Card> blind) {
		hand.addAll(blind);
		for(int i = 0; i<5;i++) {
			int numCards = hand.size();
			int index = (int)(Math.random()*numCards);
			hand.remove(index);
		}
	}
	
	public String chooseSuit() {
		String[] suits = {"Spades","Clubs","Diamonds","Hearts"};
		return suits[(int)( Math.random()*4)];
	}

}
