package joshuaforest.a500;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import joshuaforest.a500.Model.Card;
import joshuaforest.a500.Model.Game;
import joshuaforest.a500.Model.Player;


/**
 * Created by joshuaforest on 10/12/18.
 */

public class GameplayActivity extends Activity {

    LinearLayout handLayout;
    LinearLayout bidLayout;
    Button bid;
    Spinner bidNumbers;
    Spinner bidSuit;

    Game g;
    UIPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_layout);
        getLayouts();


        player = new UIPlayer();
        Player[] p = {player, new DummyPlayer(), new DummyPlayer(), new DummyPlayer()};
        Game g = new Game(p);
        g.startGame(this);
    }

    private void getLayouts(){
        handLayout = (LinearLayout) findViewById((R.id.handLayout));
        bidLayout = (LinearLayout) findViewById((R.id.bidLayout));
        bidNumbers = (Spinner) bidLayout.findViewById(R.id.spnNumber);
        bidSuit = (Spinner) bidLayout.findViewById(R.id.spnSuit);
        bid = (Button) bidLayout.findViewById(R.id.btnBid);
    }

    public void startGame(){


    }

    public void needBid(){
        bid.setEnabled(false);

        //Set bid number resources to bid number spinner
        ArrayAdapter<CharSequence> numAdapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bidNumbers.setAdapter(numAdapter);

        //Set bid suit resources to bid suit spinner
        ArrayAdapter<CharSequence> suitAdapter = ArrayAdapter.createFromResource(this, R.array.suits, android.R.layout.simple_spinner_item);
        suitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bidSuit.setAdapter(suitAdapter);

        ArrayList<Card> cards = player.getHand();
        for(int i=0;i<handLayout.getChildCount();i++){
            ImageButton b = (ImageButton) handLayout.getChildAt(i);
            b.setClickable(false);
            String fileName = cards.get(i).getFileName();
            int resourceId = getResources().getIdentifier(fileName, "drawable", getPackageName());
            b.setImageResource(resourceId);
        }

    }


    public void setViewHandToNothing(){
        for(int i=0;i<handLayout.getChildCount();i++){
            ImageButton b = (ImageButton) handLayout.getChildAt(i);
            b.setImageDrawable(null);
        }
    }

    public void onBtnQuit(View view) {
        Intent quitGame = new Intent( getApplicationContext(), MainActivity.class);
        setViewHandToNothing();
        startActivity(quitGame);
    }

    public void selectCard(View view) {
        ImageButton b = (ImageButton) view;
        b.setImageDrawable(null);
        b.setVisibility(view.GONE);
    }
}
