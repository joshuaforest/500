package joshuaforest.a500.Model;

import java.util.ArrayList;

public class Game {
	private Player[] players;
	private int team1score, team2score;
	private ArrayList<Card> deck;
	private ArrayList<Card> blind;
	private Bid curBid;
	private int dealer;
	private int team1Tricks,team2Tricks;
	private int setTrump;
	Card[] playedCards = new Card[4];
	int lead;


	public Game(Player[] players) {
		for(int i=0;i<4;i++) {
			players[i].setGame(this);
			players[i].setIndex(i);
		}
		this.players = players;
		resetDeck();
	}
	
	public void startGame() {
		team1score = 0;
		team2score = 0;
		dealer = 0;
		
		while(team1score < 500 && team2score <500 && team1score > -500 && team2score > -500) {
			dealHands();
			curBid = startBids();
			curBid.getPlayer().takeBlind(blind);
			playHands();
			countScores();
			dealer = (dealer+1)%4;
			team1Tricks = 0;
			team2Tricks = 0;
		}
	}
	
	private void countScores() {
		if(setTrump == 0) {
			if(curBid.getNum() <= team1Tricks) {
				if(team1Tricks == 10 && 250 > curBid.getValue()) {
					team1score += 250;
				} else {
					team1score += curBid.getValue();
				}
			} else {
				team1score -= curBid.getValue();
			}
			team2score+=10*team2Tricks;
		}
		if(setTrump == 1) {
			if(curBid.getNum() <= team2Tricks) {
				if(team2Tricks == 10 && 250 > curBid.getValue()) {
					team2score += 250;
				} else {
					team2score += curBid.getValue();
				}
			} else {
				team2score -= curBid.getValue();
			}
			team1score+=10*team1Tricks;
		}
	}

	public void playHands() {
		lead = curBid.getPlayer().getIndex();
		for(int i=0;i<10;i++) {
			for(int j=0;j<4;j++) {
				playedCards[(lead+j)%4]=players[(lead+j)%4].playCard();
				if(playedCards[(lead+j)%4].getIsJoker() && j==0 && curBid.getSuit().equals("No Trump")) {
					playedCards[(lead+j)%4].setSuit(players[(lead+j)%4].chooseSuit());
				}
			}
			lead = countTrick(lead);
			playedCards = new Card[4];
		}
	}

	public int getLead(){
		return lead;
	}
	
	public int countTrick(int lead) {
		int winner = lead;
		for(int i=1;i<4;i++) {
			if(compareCards(playedCards[(lead+i)%4],playedCards[winner], lead)) winner = (lead+i)%4;
		}
		if(winner == 1 || winner == 3)  team1Tricks++;
		else team2Tricks++;
		return winner;
	}
	
	public boolean compareCards(Card c1, Card c2, int lead) {
		if(c1.getIsJoker()) return true;
		if(c2.getIsJoker()) return false;
		if(isTrump(c1) && !isTrump(c2)) return true;
		if(!isTrump(c1) && isTrump(c2)) return false;
		if(checkRight(c1)) return true;
		if(checkRight(c2)) return false;
		if(checkLeft(c1)) return true;
		if(checkLeft(c2)) return false;
		if(c1.getSuit().equals(playedCards[lead].getSuit()) && !c2.getSuit().equals(playedCards[lead].getSuit())) return true;
		if(!c1.getSuit().equals(playedCards[lead].getSuit()) && c2.getSuit().equals(playedCards[lead].getSuit())) return false;
		return c1.getRank()>c2.getRank();
		
	}
	
	public boolean checkRight(Card c) {
		String trump = curBid.getSuit();
		if(c.getSuit().equals(trump) && c.getRank()==11) return true;
		return false;
	}
	
	public boolean checkLeft(Card c) {
		String trump = curBid.getSuit();
		if(trump.equals("Hearts")) {
			if(c.getSuit().equals("Diamonds") && c.getRank()==11) return true;
		} else if(trump.equals("Diamonds")) {
			if(c.getSuit().equals("Hearts") && c.getRank()==11) return true;
		} else if(trump.equals("Clubs")) {
			if(c.getSuit().equals("Spades") && c.getRank()==11) return true;
		} else if(trump.equals("Spades")) {
			if(c.getSuit().equals("Clubs") && c.getRank()==11) return true;
		}
		return false;
	}
	
	public boolean isTrump(Card c) {
		String trump = curBid.getSuit();
		if(c.getIsJoker()) return true;
		if(trump.equals("Hearts")) {
			if(c.getSuit().equals("Hearts") || (c.getSuit().equals("Diamonds")&&c.getRank()==11)) return true;
		}
		if(trump.equals("Diamonds")) {
			if(c.getSuit().equals("Diamonds") || (c.getSuit().equals("Hearts")&&c.getRank()==11)) return true;
		}
		if(trump.equals("Spades")) {
			if(c.getSuit().equals("Spades") || (c.getSuit().equals("Clubs")&&c.getRank()==11)) return true;
		}
		if(trump.equals("Clubs")) {
			if(c.getSuit().equals("Clubs") || (c.getSuit().equals("Spades")&&c.getRank()==11)) return true;
		}
		return false;
	}
	
	public Bid startBids() {
		int bidIndex = (dealer + 1)%4;
		Bid highBid = new Bid(0,"none",true,players[0]);
		for(int i=0;i<4;i++) {
			Bid b = players[bidIndex].bid();
			bidIndex = (bidIndex +1)%4;
			if(b.compareTo(highBid)==-1 || b.compareTo(highBid)==0) {
				b.setPass(true);
			}
			if(b.compareTo(highBid)==1) {
				highBid = b;
				setTrump = bidIndex%2;
				}
		}
		if(highBid.getPass()) {
			dealHands();
			return startBids();
		}
		return highBid;
	}
	
	public Bid getBid() {
		return curBid;
	}
	
	public void dealHands() {
		resetDeck();
		for(Player p: players) {
			dealHand(p);
		}
		blind = deck;
	}
	
	public void dealHand(Player p) {
		ArrayList<Card> hand = new ArrayList<Card>();
		for(int i=0;i<10;i++) {
			int index = (int) (Math.random()*deck.size());
			hand.add(deck.remove(index));
		}
		p.setHand(hand);
	}
	
	public void resetDeck() {
		deck = new ArrayList<Card>();
		for(int i=4;i<=14;i++) {
			deck.add(new Card(i,"Spades",false));
			deck.add(new Card(i,"Clubs",false));
			deck.add(new Card(i,"Diamonds",false));
			deck.add(new Card(i,"Hearts",false));
		}
		deck.add(new Card(15,"None",true));
		for(int i=0;i<4;i++) {
			players[i].setHand(new ArrayList<Card>());
		}
	}
	
	public int getTeam1Score() {
		return team1score;
	}
	
	public int getTeam2Score() {
		return team2score;
	}
	
	public Player[] getPlayers() {
		return players;
	}


}
