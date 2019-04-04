package com.cs213.chess.chess;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import com.cs213.chess.chess.board.board;
import com.cs213.chess.chess.board.game;
import com.cs213.chess.chess.Game;
import com.cs213.chess.chess.pieces.pieces;

/**
 * Created by Gino Tarraga & Kaival Patel
 */
public class ReplayGame extends Activity {

    public static ArrayList<String> savedMoves;	// The moves to replay
    public static int currentMoveIndex = 0;	// The index of the current move
    public static boolean whoseMove;	// True if it's white's move, false if it's black's move
    public String input;	// The String move, taken from savedMoves
    game game = new game();
    board board = game.getBoard();
    pieces pieces = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replay);
        currentMoveIndex = 0;
        game = new game();
        board = game.getBoard();

        Intent intent = getIntent();
        savedMoves = intent.getStringArrayListExtra(Playback.SAVED_MOVES_KEY);

        if (savedMoves == null) {
            Toast.makeText(this, "No moves to replay", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ReplayGame.this, Playback.class);
            startActivity(i);
            finish();
        }

        TextView tv = (TextView)findViewById(R.id.turn);
        tv.setText("White's turn");

        setUpBoard();
        update();

        Button nextMove = (Button)findViewById(R.id.next_move_button);

        if(nextMove != null){
            nextMove.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    if(currentMoveIndex >= savedMoves.size()){
                        Toast.makeText(ReplayGame.this, "Game over.", Toast.LENGTH_SHORT).show();
                        currentMoveIndex = 0;
                        Intent i = new Intent(ReplayGame.this, Playback.class);
                        startActivity(i);
                        finish();
                    }

                    if(currentMoveIndex < savedMoves.size()) {
                        input = savedMoves.get(currentMoveIndex);
                        String[] token = input.split(",");
                        String src = token[0];
                        String dest = token[1];
                        String promotion = token[2];

                        int file1 = board.convert_file(src.charAt(0));
                        int rank1 = board.convert_rank(Character.getNumericValue(src.charAt(1)));
                        int file2 = board.convert_file(dest.charAt(0));
                        int rank2 = board.convert_rank(Character.getNumericValue(dest.charAt(1)));
                        TextView tv = (TextView)findViewById(R.id.turn);
                        String player;
                        if(currentMoveIndex % 2 != 0)
                            player = "white's turn";
                        else player = "black's turn";
                        tv.setText(player);


                        //board.move(game.getTurn(), token[0], token[1], null);
                        pieces p = board.get_piece_at(rank1,file1);

                        //board.set_piece_at(rank1,file1,rank2,file2,p);
                        if(board.move(game.getTurn(),src,dest,null)){

                        }else board.set_piece_at(rank1,file1,rank2,file2,p);
                        game.change_turn();
                        update();
                        //game.change_turn();
                        currentMoveIndex++;
                    }
                }
            });
        }




    }


    public void setUpBoard(){
        ImageView tempView;
		/* FIRST ROW */
        tempView = (ImageView)findViewById(R.id.a1);
        //tempView.setColorFilter(Color.RED, PorterDuff.Mode.OVERLAY);
        tempView.setImageResource(R.drawable.wr);
        tempView = (ImageView)findViewById(R.id.b1);
        tempView.setImageResource(R.drawable.wn);
        tempView = (ImageView)findViewById(R.id.c1);
        tempView.setImageResource(R.drawable.wb);
        tempView = (ImageView)findViewById(R.id.d1);
        tempView.setImageResource(R.drawable.wq);
        tempView = (ImageView)findViewById(R.id.e1);
        tempView.setImageResource(R.drawable.wk);
        tempView = (ImageView)findViewById(R.id.f1);
        tempView.setImageResource(R.drawable.wb);
        tempView = (ImageView)findViewById(R.id.g1);
        tempView.setImageResource(R.drawable.wn);
        tempView = (ImageView)findViewById(R.id.h1);
        tempView.setImageResource(R.drawable.wr);
		/* SECOND ROW */
        tempView = (ImageView)findViewById(R.id.a2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.b2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.c2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.d2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.e2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.f2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.g2);
        tempView.setImageResource(R.drawable.wp);
        tempView = (ImageView)findViewById(R.id.h2);
        tempView.setImageResource(R.drawable.wp);
		/* EIGHTH ROW */
        tempView = (ImageView)findViewById(R.id.a8);
        tempView.setImageResource(R.drawable.br);
        tempView = (ImageView)findViewById(R.id.b8);
        tempView.setImageResource(R.drawable.bn);
        tempView = (ImageView)findViewById(R.id.c8);
        tempView.setImageResource(R.drawable.bb);
        tempView = (ImageView)findViewById(R.id.d8);
        tempView.setImageResource(R.drawable.bq);
        tempView = (ImageView)findViewById(R.id.e8);
        tempView.setImageResource(R.drawable.bk);
        tempView = (ImageView)findViewById(R.id.f8);
        tempView.setImageResource(R.drawable.bb);
        tempView = (ImageView)findViewById(R.id.g8);
        tempView.setImageResource(R.drawable.bn);
        tempView = (ImageView)findViewById(R.id.h8);
        tempView.setImageResource(R.drawable.br);
		/* SEVENTH ROW */
        tempView = (ImageView)findViewById(R.id.a7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.b7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.c7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.d7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.e7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.f7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.g7);
        tempView.setImageResource(R.drawable.bp);
        tempView = (ImageView)findViewById(R.id.h7);
        tempView.setImageResource(R.drawable.bp);

    }

    public void update(){
        pieces p;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                p = board.get_piece_at(i,j);
                getSquare(i,j,p);
            }
        }
    }

    public void getSquare(int i, int j, pieces p){
        ImageView tempView;
        if(i == 0 && j == 0){
            tempView = (ImageView)findViewById(R.id.a8);
            update(tempView,p);
        }else if(i == 0 && j == 1){
            tempView = (ImageView)findViewById(R.id.b8);
            update(tempView,p);
        }
        else if(i == 0 && j == 2){
            tempView = (ImageView)findViewById(R.id.c8);
            update(tempView,p);
        }
        else if(i == 0 && j == 3){
            tempView = (ImageView)findViewById(R.id.d8);
            update(tempView,p);
        }
        else if(i == 0 && j == 4){
            tempView = (ImageView)findViewById(R.id.e8);
            update(tempView,p);
        }
        else if(i == 0 && j == 5){
            tempView = (ImageView)findViewById(R.id.f8);
            update(tempView,p);
        }
        else if(i == 0 && j == 6){
            tempView = (ImageView)findViewById(R.id.g8);
            update(tempView,p);
        }
        else if(i == 0 && j == 7){
            tempView = (ImageView)findViewById(R.id.h8);
            update(tempView,p);
        }
        else if(i == 1 && j == 0){
            tempView = (ImageView)findViewById(R.id.a7);
            update(tempView,p);
        }else if(i == 1 && j == 1){
            tempView = (ImageView)findViewById(R.id.b7);
            update(tempView,p);
        }
        else if(i == 1 && j == 2){
            tempView = (ImageView)findViewById(R.id.c7);
            update(tempView,p);
        }
        else if(i == 1 && j == 3){
            tempView = (ImageView)findViewById(R.id.d7);
            update(tempView,p);
        }
        else if(i == 1 && j == 4){
            tempView = (ImageView)findViewById(R.id.e7);
            update(tempView,p);
        }
        else if(i == 1 && j == 5){
            tempView = (ImageView)findViewById(R.id.f7);
            update(tempView,p);
        }
        else if(i == 1 && j == 6){
            tempView = (ImageView)findViewById(R.id.g7);
            update(tempView,p);
        }
        else if(i == 1 && j == 7){
            tempView = (ImageView)findViewById(R.id.h7);
            update(tempView,p);
        }
        else if(i == 2 && j == 0){
            tempView = (ImageView)findViewById(R.id.a6);
            update(tempView,p);
        }else if(i == 2 && j == 1){
            tempView = (ImageView)findViewById(R.id.b6);
            update(tempView,p);
        }
        else if(i == 2 && j == 2){
            tempView = (ImageView)findViewById(R.id.c6);
            update(tempView,p);
        }
        else if(i == 2 && j == 3){
            tempView = (ImageView)findViewById(R.id.d6);
            update(tempView,p);
        }
        else if(i == 2 && j == 4){
            tempView = (ImageView)findViewById(R.id.e6);
            update(tempView,p);
        }
        else if(i == 2 && j == 5){
            tempView = (ImageView)findViewById(R.id.f6);
            update(tempView,p);
        }
        else if(i == 2 && j == 6){
            tempView = (ImageView)findViewById(R.id.g6);
            update(tempView,p);
        }
        else if(i == 2 && j == 7){
            tempView = (ImageView)findViewById(R.id.h6);
            update(tempView,p);
        }
        else if(i == 3 && j == 0){
            tempView = (ImageView)findViewById(R.id.a5);
            update(tempView,p);
        }else if(i == 3 && j == 1){
            tempView = (ImageView)findViewById(R.id.b5);
            update(tempView,p);
        }
        else if(i == 3 && j == 2){
            tempView = (ImageView)findViewById(R.id.c5);
            update(tempView,p);
        }
        else if(i == 3 && j == 3){
            tempView = (ImageView)findViewById(R.id.d5);
            update(tempView,p);
        }
        else if(i == 3 && j == 4){
            tempView = (ImageView)findViewById(R.id.e5);
            update(tempView,p);
        }
        else if(i == 3 && j == 5){
            tempView = (ImageView)findViewById(R.id.f5);
            update(tempView,p);
        }
        else if(i == 3 && j == 6){
            tempView = (ImageView)findViewById(R.id.g5);
            update(tempView,p);
        }
        else if(i == 3 && j == 7){
            tempView = (ImageView)findViewById(R.id.h5);
            update(tempView,p);
        }
        else if(i == 4 && j == 0){
            tempView = (ImageView)findViewById(R.id.a4);
            update(tempView,p);
        }else if(i == 4 && j == 1){
            tempView = (ImageView)findViewById(R.id.b4);
            update(tempView,p);
        }
        else if(i == 4 && j == 2){
            tempView = (ImageView)findViewById(R.id.c4);
            update(tempView,p);
        }
        else if(i == 4 && j == 3){
            tempView = (ImageView)findViewById(R.id.d4);
            update(tempView,p);
        }
        else if(i == 4 && j == 4){
            tempView = (ImageView)findViewById(R.id.e4);
            update(tempView,p);
        }
        else if(i == 4 && j == 5){
            tempView = (ImageView)findViewById(R.id.f4);
            update(tempView,p);
        }
        else if(i == 4 && j == 6){
            tempView = (ImageView)findViewById(R.id.g4);
            update(tempView,p);
        }
        else if(i == 4 && j == 7){
            tempView = (ImageView)findViewById(R.id.h4);
            update(tempView,p);
        }
        else if(i == 5 && j == 0){
            tempView = (ImageView)findViewById(R.id.a3);
            update(tempView,p);
        }else if(i == 5 && j == 1){
            tempView = (ImageView)findViewById(R.id.b3);
            update(tempView,p);
        }
        else if(i == 5 && j == 2){
            tempView = (ImageView)findViewById(R.id.c3);
            update(tempView,p);
        }
        else if(i == 5 && j == 3){
            tempView = (ImageView)findViewById(R.id.d3);
            update(tempView,p);
        }
        else if(i == 5 && j == 4){
            tempView = (ImageView)findViewById(R.id.e3);
            update(tempView,p);
        }
        else if(i == 5 && j == 5){
            tempView = (ImageView)findViewById(R.id.f3);
            update(tempView,p);
        }
        else if(i == 5 && j == 6){
            tempView = (ImageView)findViewById(R.id.g3);
            update(tempView,p);
        }
        else if(i == 5 && j == 7){
            tempView = (ImageView)findViewById(R.id.h3);
            update(tempView,p);
        }
        else if(i == 6 && j == 0){
            tempView = (ImageView)findViewById(R.id.a2);
            update(tempView,p);
        }else if(i == 6 && j == 1){
            tempView = (ImageView)findViewById(R.id.b2);
            update(tempView,p);
        }
        else if(i == 6 && j == 2){
            tempView = (ImageView)findViewById(R.id.c2);
            update(tempView,p);
        }
        else if(i == 6 && j == 3){
            tempView = (ImageView)findViewById(R.id.d2);
            update(tempView,p);
        }
        else if(i == 6 && j == 4){
            tempView = (ImageView)findViewById(R.id.e2);
            update(tempView,p);
        }
        else if(i == 6 && j == 5){
            tempView = (ImageView)findViewById(R.id.f2);
            update(tempView,p);
        }
        else if(i == 6 && j == 6){
            tempView = (ImageView)findViewById(R.id.g2);
            update(tempView,p);
        }
        else if(i == 6 && j == 7){
            tempView = (ImageView)findViewById(R.id.h2);
            update(tempView,p);
        }
        else if(i == 7 && j == 0){
            tempView = (ImageView)findViewById(R.id.a1);
            update(tempView,p);
        }else if(i == 7 && j == 1){
            tempView = (ImageView)findViewById(R.id.b1);
            update(tempView,p);
        }
        else if(i == 7 && j == 2){
            tempView = (ImageView)findViewById(R.id.c1);
            update(tempView,p);
        }
        else if(i == 7 && j == 3){
            tempView = (ImageView)findViewById(R.id.d1);
            update(tempView,p);
        }
        else if(i == 7 && j == 4){
            tempView = (ImageView)findViewById(R.id.e1);
            update(tempView,p);
        }
        else if(i == 7 && j == 5){
            tempView = (ImageView)findViewById(R.id.f1);
            update(tempView,p);
        }
        else if(i == 7 && j == 6){
            tempView = (ImageView)findViewById(R.id.g1);
            update(tempView,p);
        }
        else if(i == 7 && j == 7){
            tempView = (ImageView)findViewById(R.id.h1);
            update(tempView,p);
        }
    }

    public void update(ImageView tempView, pieces p){
        if(p == null){
            tempView.setImageResource(R.drawable.cut);
            tempView.setImageTintMode(PorterDuff.Mode.LIGHTEN);
            tempView.setColorFilter(Color.TRANSPARENT,PorterDuff.Mode.OVERLAY);
        }else{
            setImagePiece(tempView,p);
            tempView.setColorFilter(Color.TRANSPARENT,PorterDuff.Mode.OVERLAY);
        }
    }

    public void setImagePiece(ImageView tempView,pieces p){
        if(p.getPieceType().equalsIgnoreCase("wp")){
            tempView.setImageResource(R.drawable.wp);
        }else if(p.getPieceType().equalsIgnoreCase("bp")){
            tempView.setImageResource(R.drawable.bp);
        }else if(p.getPieceType().equalsIgnoreCase("wr")){
            tempView.setImageResource(R.drawable.wr);
        }else if(p.getPieceType().equalsIgnoreCase("br")){
            tempView.setImageResource(R.drawable.br);
        }else if(p.getPieceType().equalsIgnoreCase("wn")){
            tempView.setImageResource(R.drawable.wn);
        }else if(p.getPieceType().equalsIgnoreCase("bn")){
            tempView.setImageResource(R.drawable.bn);
        }else if(p.getPieceType().equalsIgnoreCase("wb")){
            tempView.setImageResource(R.drawable.wb);
        }else if(p.getPieceType().equalsIgnoreCase("bb")){
            tempView.setImageResource(R.drawable.bb);
        }else if(p.getPieceType().equalsIgnoreCase("wk")){
            tempView.setImageResource(R.drawable.wk);
        }else if(p.getPieceType().equalsIgnoreCase("bk")){
            tempView.setImageResource(R.drawable.bk);
        }else if(p.getPieceType().equalsIgnoreCase("wq")){
            tempView.setImageResource(R.drawable.wq);
        }else if(p.getPieceType().equalsIgnoreCase("bq")){
            tempView.setImageResource(R.drawable.bq);
        }
    }

    public void squareClicked(View v){
        return;
    }

}
