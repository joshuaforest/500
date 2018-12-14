package joshuaforest.a500;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintHorizontalLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import joshuaforest.a500.Model.Bid;
import joshuaforest.a500.Model.Card;
import joshuaforest.a500.Model.Game;
import joshuaforest.a500.Model.Player;


/**
 * Created by joshuaforest on 10/12/18.
 */

public class GameplayActivity extends Activity implements AdapterView.OnItemSelectedListener {

    LinearLayout handLayout;
    LinearLayout bidLayout;
    Button bid;
    Spinner bidNumbers;
    Spinner bidSuit;
    TextView[] playerBids;

    Game g;
    UIPlayer player;
    Player[] p;

    boolean bidRankGiven = false;
    int bidNum;
    boolean bidSuitGiven = false;
    String suitBeingBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_layout);
        getLayouts();


        player = new UIPlayer();
        Player[] p = {player, new DummyPlayer(), new DummyPlayer(), new DummyPlayer()};
        this.p = p;
        g = new Game(p);
        g.startGame(this);
    }

    private void getLayouts(){
        handLayout = (LinearLayout) findViewById((R.id.handLayout));
        bidLayout = (LinearLayout) findViewById((R.id.bidLayout));
        bidNumbers = (Spinner) bidLayout.findViewById(R.id.spnNumber);
        bidSuit = (Spinner) bidLayout.findViewById(R.id.spnSuit);
        bid = (Button) bidLayout.findViewById(R.id.btnBid);

        TextView player0bid = (TextView) findViewById(R.id.textView);
        TextView player1bid = (TextView) findViewById(R.id.textView1);
        TextView player2bid = (TextView) findViewById(R.id.textView2);
        TextView player3bid = (TextView) findViewById(R.id.textView3);
        TextView[] playerBids =  {player0bid, player1bid, player2bid, player3bid};
        for(TextView t : playerBids){
            t.setVisibility(View.INVISIBLE);
        }
        this.playerBids = playerBids;
    }

    public void startGame(){


    }

    public void needBid(){
        bidRankGiven = false;
        bidSuitGiven = false;
        bid.setEnabled(false);

        //Set bid number resources to bid number spinner
        ArrayAdapter<CharSequence> numAdapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bidNumbers.setAdapter(numAdapter);
        bidNumbers.setOnItemSelectedListener(this);

        //Set bid suit resources to bid suit spinner
        ArrayAdapter<CharSequence> suitAdapter = ArrayAdapter.createFromResource(this, R.array.suits, android.R.layout.simple_spinner_item);
        suitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bidSuit.setAdapter(suitAdapter);
        bidSuit.setOnItemSelectedListener(this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==bidSuit.getId()) {
            suitBeingBid = adapterView.getItemAtPosition(i).toString();
            if(!suitBeingBid.equals("Suit")) bidSuitChanged();
            else{
                bidSuitRemoved();
            }
        }
        else if(adapterView.getId()==bidNumbers.getId()) {
            String bidNumStr = adapterView.getItemAtPosition(i).toString();
            if(!bidNumStr.equals("#")){
                bidNum = Integer.parseInt(bidNumStr);
                bidNumberChanged();
            } else{
                bidNumberRemoved();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        if(adapterView.getId()==bidSuit.getId()) bidSuitRemoved();
        else if(adapterView.getId()==bidNumbers.getId()) bidNumberRemoved();
    }

    public void bidNumberChanged(){
        bidRankGiven = true;
        if(bidSuitGiven == true){
            Bid b = new Bid(bidNum,suitBeingBid,false,player);
            if(b.compareTo(g.getBid()) == 1) bid.setEnabled(true);
            else bid.setEnabled(false);
        }
    }

    public void bidNumberRemoved(){
        bidRankGiven = false;
        bidNum = 0;
        bid.setEnabled(false);
    }

    public void bidSuitChanged(){
        bidSuitGiven = true;
        if(bidRankGiven == true){
            Bid b = new Bid(bidNum,suitBeingBid,false,player);
            if(b.compareTo(g.getBid()) == 1) bid.setEnabled(true);
            else bid.setEnabled(false);
        }
    }

    public void bidSuitRemoved(){
        bidSuitGiven = false;
        suitBeingBid = null;
        bid.setEnabled(false);
    }

    public void bidSet(Bid b){
       for(int i = 0; i<4; i++){
           if(b.getPlayer() == p[i]) {
               playerBids[i].setVisibility(View.VISIBLE);
               playerBids[i].setText(b.toString());
           }
       }
    }


   public void pass(View view) {
        Bid b = new Bid(0,"nothing",true,player);
        player.makeBid(b);
    }

    public void makeBid(View view){
        Bid b = new Bid(bidNum,suitBeingBid,false,player);
        player.makeBid(b);
    }

}
