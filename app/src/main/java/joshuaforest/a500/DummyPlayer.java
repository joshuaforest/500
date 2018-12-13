package joshuaforest.a500;

import java.util.ArrayList;

import joshuaforest.a500.Model.Bid;
import joshuaforest.a500.Model.Card;
import joshuaforest.a500.Model.Player;

public class DummyPlayer extends Player {

	@Override
	public void needCard() {
        int index = (int) (Math.random()*this.hand.size());
        Card c = this.hand.get(index);
        if(checkEligiblePlay(c)) g.notifyCardPlayed(this.hand.remove(index));
        else  needCard();
    }


	@Override
	public void needBid() {
		String[] suits = {"Spades","Clubs","Diamonds","Hearts","No Trump"};
		if(Math.random()>.5) {
		    Bid b = new Bid(0,"none",true, this);
			g.notifyBid(b);
		} else {
			String suit = suits[(int)( Math.random()*5)];
			int num = (int)(Math.random()*5)+6;
			Bid b = new Bid(num, suit, false, this);
			g.notifyBid(b);
		}

	}

	public void notifyDealHands(){ }

    public void notifyStartGame(GameplayActivity act){ }

	@Override
	public void takeBlind(ArrayList<Card> blind) {
		hand.addAll(blind);
		for(int i = 0; i<5;i++) {
			int numCards = hand.size();
			int index = (int)(Math.random()*numCards);
			hand.remove(index);
		}
		g.notifyBlindTaken();
	}

	@Override
	public void needSuit(){
        String[] suits = {"Spades","Clubs","Diamonds","Hearts"};
        g.notifySuit(suits[(int)( Math.random()*4)]);
    }

}
