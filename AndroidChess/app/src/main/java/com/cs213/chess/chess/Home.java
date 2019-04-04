package com.cs213.chess.chess;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cs213.chess.chess.saveData.Data;
/**
 * Created by Gino Tarraga & Kaival Patel
 */
public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Data.context = getApplicationContext();
        Data.loadData();

        final Button player1 = (Button)findViewById(R.id.OnePlayer);
        final Button player2 = (Button)findViewById(R.id.TwoPlayer);
        final Button playback = (Button)findViewById(R.id.Playback);
        final Button exit = (Button)findViewById(R.id.Exit);

        if(exit != null) {
            exit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.exit(0);
                }
            });
        }

        if(player1 != null){
            player1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent(Home.this, Game.class);
                    startActivity(i);
                }
            });
        }

        if(player2 != null){
            player2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent(Home.this, Game.class);
                    startActivity(i);
                }
            });
        }

        if(playback != null){
            playback.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent(Home.this, Playback.class);
                    startActivity(i);
                }
            });
        }



    }
}
