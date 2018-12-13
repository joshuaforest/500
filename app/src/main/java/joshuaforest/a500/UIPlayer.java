package joshuaforest.a500;

import java.util.ArrayList;

import joshuaforest.a500.Model.Bid;
import joshuaforest.a500.Model.Card;
import joshuaforest.a500.Model.Player;

public class UIPlayer extends Player {
    GameplayActivity act;

    @Override
    public void needBid() {
        act.needBid();
    }

    @Override
    public void needSuit() {
        //TODO
    }

    @Override
    public void needCard() {
        //TODO
    }

    @Override
    public void takeBlind(ArrayList<Card> blind) {
        //TODO

    }

    @Override
    public void notifyDealHands(){
        sortHand("No Trump");
    }

    @Override
    public void notifyStartGame(GameplayActivity act){
        this.act = act;
        act.startGame();
    }


}
