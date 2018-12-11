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

	int bidIndex;
	Bid highBid;
	int tricksLeft;
	int playerIndex;


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

		dealHands();

    }

    private void finishGame(){

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
        dealer = (dealer+1)%4;
        team1Tricks = 0;
        team2Tricks = 0;

        if(team1score>=500 || team2score>=500 || team1score<=500 || team2score<=500) finishGame();
        else dealHands();
	}

	private void playHands() {
		lead = curBid.getPlayer().getIndex();
		playerIndex = 0;
        players[lead].needCard();
        tricksLeft=10;
	}

	public void notifyCardPlayed(Card c){
        playedCards[(lead+playerIndex)%4]=c;
        playerIndex++;
        if(playedCards[lead].getIsJoker() && playerIndex-1==0 && curBid.getSuit().equals("No Trump")) {
            players[lead].needSuit();
        }
        else if(playerIndex<4){
            players[lead+playerIndex].needCard();
        }
        else{
            lead = countTrick(lead);
            playerIndex = 0;
            playedCards = new Card[4];
            tricksLeft--;
            if(tricksLeft>0) {
                players[lead].needCard();
            } else{
                countScores();
            }
        }
    }

    public void notifySuit(String s){
        playedCards[(lead+playerIndex-1)%4].setSuit(s);
        players[playerIndex].needCard();
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
	
	public void startBids() {
	    bidIndex = (dealer + 1)%4;
	    highBid = new Bid(0,"none",true,players[0]);
        players[bidIndex].needBid();

	}

	public void notifyBid(Bid b){

        if(b.compareTo(highBid)==-1 || b.compareTo(highBid)==0) {
            b.setPass(true);
        }
        if(b.compareTo(highBid)==1) {
            highBid = b;
            setTrump = bidIndex%2;
        }
        bidIndex = (bidIndex +1)%4;
        if(bidIndex == (dealer + 1)%4){
            if(highBid.getPass()) {
                dealHands();
            }
            else{
                curBid.getPlayer().takeBlind(blind);
            }
        }

        else{
            players[bidIndex].needBid();
        }
    }

    public void notifyBlindTaken(){
        playHands();
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
        startBids();
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
