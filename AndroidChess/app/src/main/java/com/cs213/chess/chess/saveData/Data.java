package com.cs213.chess.chess.saveData;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Gino Tarraga & Kaival Patel
 */
public class Data implements Serializable {
    private static final long serialVersionUID = 93082451920156397L;

    public static ArrayList<SaveData> gameMoves;

    public static Context context;

    public static void loadData() {
        File f = new File(context.getFilesDir(), "save.dat");
        if (f.exists()) {
            try {
                FileInputStream saveFile = context.openFileInput("save.dat");
                ObjectInputStream save = new ObjectInputStream(saveFile);
                gameMoves = (ArrayList<SaveData>) save.readObject();
                save.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        } else {
            Data.gameMoves = new ArrayList<SaveData>();
        }
    }

    public static void saveData() {
        try {
            FileOutputStream saveFile = context.openFileOutput("save.dat", 0);
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            save.writeObject(gameMoves);
            save.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
