package joshuaforest.a500;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import joshuaforest.a500.Model.Game;
import joshuaforest.a500.Model.Player;

public class MainActivity extends AppCompatActivity {

    TextView totalTextView;
    EditText percentageTxt;
    EditText numberTxt;
    String StartGame = "Hello";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //totalTextView = (TextView) findViewById((R.id.totalTextView));
        //percentageTxt = (EditText) findViewById(R.id.percentageTxt);
        //numberTxt = (EditText) findViewById(R.id.numTxt);



    }


    public void onStartBtnClick(View view){
        /**float percentage = Float.parseFloat(percentageTxt.getText().toString());
         float dec = percentage / 100;
         float total = dec * Float.parseFloat(numberTxt.getText().toString());
         totalTextView.setText(Float.toString(total));**/


        /**Player[] p = {new DummyPlayer(), new DummyPlayer(), new DummyPlayer(), new DummyPlayer()};
        Game g = new Game(p);
        g.startGame();
        totalTextView.setText(g.getTeam1Score()+" "+g.getTeam2Score());
        //System.out.println(g.getTeam1Score());
        //System.out.println(g.getTeam2Score());**/


        Intent startGame = new Intent(this, GameplayActivity.class);
        startActivity(startGame);

        //Button startGame = (Button) view;
        //startGame.setText("Yello");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
