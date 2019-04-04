package com.cs213.chess.chess.saveData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Gino Tarraga & Kaival Patel
 */
public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    public String title;
    public ArrayList<String>moves;
    public Calendar datePlayed;

    public SaveData(String title, ArrayList<String> moves){
        this.title = title;
        this.moves = moves;
        this.datePlayed = Calendar.getInstance();
    }

    public String toString() {
        return title +" - "+ datePlayed.getTime().toString();
    }


}
