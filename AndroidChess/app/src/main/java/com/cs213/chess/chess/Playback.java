package com.cs213.chess.chess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cs213.chess.chess.saveData.Data;
import com.cs213.chess.chess.saveData.SaveData;

import java.util.Collections;
import java.util.Comparator;

/**
 *
 * Created by Gino Tarraga & Kaival Patel
 */

public class Playback extends Activity {
    public static final String SAVED_MOVES_KEY = "saved moves";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.playback);
        loadSavedGames();
        selectgame();

        Button date = (Button)findViewById(R.id.sortDate);
        Button title = (Button)findViewById(R.id.sortTitle);

        if(date != null){
            date.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(Data.gameMoves != null) {
                        int totalSavedGames = Data.gameMoves.size();
                        Collections.sort(Data.gameMoves, new Comparator<SaveData>() {
                            public int compare(SaveData o1, SaveData o2) {
                                return o1.datePlayed.compareTo(o2.datePlayed);
                            }
                        });

                        String[] games = new String[totalSavedGames];

                        for (int i = 0; i<totalSavedGames; i++) {
                            games[i] = Data.gameMoves.get(i).toString();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Playback.this, R.layout.games, games);
                        ListView list  = (ListView) findViewById(R.id.gameList);
                        list.setAdapter(adapter);
                    }
                }
            });
        }

        if(title != null){
            title.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(Data.gameMoves != null){
                        int totalSavedGames = Data.gameMoves.size();

                        Collections.sort(Data.gameMoves, new Comparator<SaveData>() {
                            public int compare(SaveData o1, SaveData o2) {
                                if(o1 != null && o2 != null)
                                {
                                    return o1.title.compareToIgnoreCase(o2.title);
                                }
                                return 0;
                            }
                        });

                        String[] games = new String[totalSavedGames];

                        for (int i = 0; i<totalSavedGames; i++) {
                            games[i] = Data.gameMoves.get(i).toString();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Playback.this, R.layout.games, games);
                        ListView list  = (ListView) findViewById(R.id.gameList);
                        list.setAdapter(adapter);
                    }
                }
            });
        }

    }

    private void loadSavedGames(){
        if(Data.gameMoves != null) {
            int totalgames = Data.gameMoves.size();
            String[] games = new String[totalgames];

            for (int i = 0; i < totalgames; i++) {
                games[i] = Data.gameMoves.get(i).toString();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.games,
                    games);

            ListView list = (ListView) findViewById(R.id.gameList);
            list.setAdapter(adapter);
        }
        else Toast.makeText(this, "There are no games", Toast.LENGTH_SHORT).show();
    }

    private void selectgame(){
        ListView list  = (ListView) findViewById(R.id.gameList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {
                SaveData p = Data.gameMoves.get(position);
                Intent intent = new Intent(Playback.this, ReplayGame.class);
                intent.putExtra(SAVED_MOVES_KEY, p.moves);
                startActivity(intent);
                finish();
            }
            {
            }
        });
    }


}
