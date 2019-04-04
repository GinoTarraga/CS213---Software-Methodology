package com.cs213.chess.chess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs213.chess.chess.saveData.Data;
import com.cs213.chess.chess.saveData.SaveData;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Gino Tarraga and Kaival Patel
 */
public class Save extends Activity {

    ArrayList<String> moves = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);

        Button save = (Button)findViewById(R.id.saveButton);
        Button cancel = (Button)findViewById(R.id.cancelButton);


        Bundle b = getIntent().getExtras();
        String winner = b.getString("winner");

        Intent i = getIntent();
        moves = i.getStringArrayListExtra(Playback.SAVED_MOVES_KEY);

        TextView WINNER = (TextView)findViewById(R.id.winner);
        assert winner != null;
        if(winner.equals("w"))
            WINNER.setText("White won!");
        else
            WINNER.setText("Black won!");


        if(cancel != null){
            cancel.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent i = new Intent(Save.this,Home.class);
                    startActivity(i);
                }
            });
        }

        if(save != null){
            save.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    TextView textView = (TextView)findViewById(R.id.saveInput);
                    String title = textView.getText().toString();
                    if(title == null){
                        Toast.makeText(Save.this, "Enter title", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SaveData data = new SaveData(title,moves);
                    if(Data.gameMoves == null)
                        Data.gameMoves = new ArrayList<SaveData>();
                    Data.gameMoves.add(data);
                    try {
                        Data.saveData();
                        Toast.makeText(Save.this, "Game Saved", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(Save.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
