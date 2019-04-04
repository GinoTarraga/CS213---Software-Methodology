package com.cs213.chess.chess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs213.chess.chess.board.board;
import com.cs213.chess.chess.board.game;
import com.cs213.chess.chess.pieces.pieces;
import com.cs213.chess.chess.warnings.warning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gino Tarraga & Kaival Patel
 */
public class Game extends Activity {

    private String source = null;
    private String destination = null;
    private String src = null, dest = null;
    private Random randomGenerator;
    game game = new game();
    board board = game.getBoard();
    warning warning = new warning();
    pieces pieces = new pieces();
    AlertDialog levelDialog;
    int rank1, file1;
    char color;
    int a,b,c,d;
    int undoCount = 0;
    int smd = 0;
    int counter = 0;
    boolean drawww = false;
    pieces ppp = null;
    String promotionnn= null;
    public static ArrayList<String> savedMoves;	// Moves that have been made in this game so far

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        setUpBoard();
        update();
        savedMoves = new ArrayList<String>();

        final Button resign = (Button)findViewById(R.id.resignButton);
        final Button draw = (Button)findViewById(R.id.drawButton);
        TextView tv = (TextView)findViewById(R.id.printLabel);
        final Button random = (Button)findViewById(R.id.randomButton);
        final Button undo = (Button)findViewById(R.id.undoButton);
        String turn = null;
        if(game.getTurn() == 'w')
            turn = "white";
        else turn = "black";
        tv.setText(turn + "'s turn");


        if(resign!=null){
            resign.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){

                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Game.this);
                    String player;
                    if(game.getTurn() == 'w'){
                        player = "White";
                    }else player = "black";
                    dlgAlert.setMessage("Do you really want to resign? The opponent will win!");
                    dlgAlert.setTitle(player + " wants to Resign");

                    dlgAlert.setCancelable(true);
                    dlgAlert.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent i = new Intent(Game.this, Save.class);
                                    i.putExtra(Playback.SAVED_MOVES_KEY, savedMoves);
                                    Bundle b = new Bundle();
                                    game.change_turn();
                                    b.putString("winner",game.getTurn()+"");
                                    i.putExtras(b);
                                    startActivity(i);
                                    finish();
                                }
                            }).setNegativeButton("No",null);
                    dlgAlert.create().show();

                }
            });
        }

        if(draw!=null){
            draw.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Game.this);
                    String player = null;
                    if(game.getTurn() == 'w'){
                        player = "White";
                    }else player = "black";

                    dlgAlert.setMessage("\"" + player + "\""+ " is requesting a draw.\n\n Does the opponent also want to draw the game?");
                    dlgAlert.setTitle(player + "'s Draw Request");

                    dlgAlert.setCancelable(true);
                    dlgAlert.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent i = new Intent(Game.this, Save.class);
                                    i.putExtra(Playback.SAVED_MOVES_KEY, savedMoves);
                                    Bundle b = new Bundle();
                                    b.putString("winner",game.getTurn()+"");
                                    i.putExtras(b);
                                    startActivity(i);
                                    finish();
                                }
                            }).setNegativeButton("No",null);
                    dlgAlert.create().show();

                }
            });
        }

        if(random != null){
            random.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    randomCall();
                }
            });
        }

        if(undo != null){
            undo.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    callUndo();
                }
            });
        }
    }

    private void save(){

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

    private void makeMove(){
        if(source == null && destination == null)
            return;
        if(board.move(game.getTurn(),source,destination,null)){
            file1 = board.convert_file(destination.charAt(0));
            rank1 = board.convert_rank(Character.getNumericValue(destination.charAt(1)));
            a = board.convert_file(source.charAt(0));
            b = board.convert_rank(Character.getNumericValue(source.charAt(1)));
            c = file1;
            d = rank1;
            src = source;
            dest = destination;
            smd = 0;
            undoCount++;
            drawww = false;
            pieces p = board.get_piece_at(rank1,file1);
            update();
            if(game.getTurn() == 'w')
                color = 'w';
            else color = 'b';
            final String[] promotion = {null};
            if(board.getPromotion()){
                board.setPromotionOff();

                final CharSequence[] items = {"queen","knight","rook","bishop"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Promote your pawn");
                builder.setSingleChoiceItems(items,-1,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item){
                        switch(item){
                            case 0:
                                promotion[0] = "Q";
                                board.promote(color,rank1,file1,"Q");
                                promotionnn = "Q";
                                break;
                            case 1:
                                promotion[0] = "N";
                                board.promote(color,rank1,file1,"N");
                                promotionnn = "N";
                                break;
                            case 2:
                                promotion[0] = "R";
                                board.promote(color,rank1,file1,"R");
                                promotionnn = "R";
                                break;
                            case 3:
                                promotion[0] = "B";
                                board.promote(color,rank1,file1,"B");
                                promotionnn = "B";
                                break;
                        }
                        levelDialog.dismiss();
                        update();
                    }
                });
                levelDialog = builder.create();
                levelDialog.show();


            }

            ppp = board.getPass();

            //System.out.println(promotionnn + " =========");
            savedMoves.add(source + "," +destination + "," + promotionnn);

            if(board.isEnpassants()){
                board.setEnpassants();
                drawww = true;
            }


            if(board.isCheckmate()){
                Toast.makeText(this, "Checkmate!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Game.this, Save.class);
                i.putExtra(Playback.SAVED_MOVES_KEY, savedMoves);
                Bundle b = new Bundle();
                b.putString("winner",game.getTurn()+"");
                i.putExtras(b);
                startActivity(i);
                finish();
            }
            if(board.isStalemate()){
                Toast.makeText(this, "Stalemate!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Game.this, Save.class);
                i.putExtra(Playback.SAVED_MOVES_KEY, savedMoves);
                Bundle b = new Bundle();
                b.putString("winner",game.getTurn()+"");
                i.putExtras(b);
                startActivity(i);
                finish();
            }
            
            if(board.isDraw()){
                Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Game.this, Save.class);
                i.putExtra(Playback.SAVED_MOVES_KEY, savedMoves);
                Bundle b = new Bundle();
                b.putString("winner","Game Draw!");
                i.putExtras(b);
                startActivity(i);
                finish();
            }

            final TextView tv = (TextView)findViewById(R.id.printLabel);
            String turn = null;
            counter = 0;
            game.change_turn();
            if(game.getTurn() == 'w')
                turn = "white";
            else turn = "black";
            tv.setText(turn + "'s turn");

            if(board.isCheckChecker()){
                board.checkOff();
                Toast.makeText(this, "Check!", Toast.LENGTH_SHORT).show();
                tv.setText(turn + "'s turn" + ": " + "king in check");
            }
        }else if(board.isCheck()){
            if(smd == 1){
                randomCall();
                update();
                source = null;
                destination = null;
                return;
            }
            Toast.makeText(this, "King is in check", Toast.LENGTH_SHORT).show();
        }
        else {
            if(smd == 1){
                randomCall();
                update();
                source = null;
                destination = null;
                return;
            }
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show();
        }

        //System.out.println(board);
        update();
        promotionnn = null;
        source = null;
        destination = null;

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

        String squareName = v
                .getResources()
                .getResourceEntryName(v.getId());

        if(source == null)
            source = squareName;
        else if(source.equals(squareName)) {
            update();
            source = null;
        }
        else if(destination == null) {
            destination = squareName;
            int file2 = board.convert_file(destination.charAt(0));
            int rank2 = board.convert_rank(Character.getNumericValue(destination.charAt(1)));
            pieces p = board.get_piece_at(rank2,file2);
            if(p != null){
                if(p.color == game.getTurn()){
                    source = squareName;
                    destination = null;
                    update();
                    showPotentialMoves();
                    return;
                }
            }
            makeMove();
        }
        else {
            source = squareName;
            destination = null;
        }

        if(source != null)
            showPotentialMoves();

    }

    public void showPotentialMoves(){
        int file1 = board.convert_file(source.charAt(0));
        int rank1 = board.convert_rank(Character.getNumericValue(source.charAt(1)));

        String[][] moves = new String[8][8];
        pieces p = board.get_piece_at(rank1, file1);
        if(p == null){
            Toast.makeText(this, "Invalid Selection!", Toast.LENGTH_SHORT).show();
            source = null;
            update();
        }else{
            if(p.get_color() != game.getTurn()) {
                Toast.makeText(this, "You can't move opponent's piece!", Toast.LENGTH_SHORT).show();
                source = null;
                update();
                return;
            }
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                        if (p.can_it_move(game.getTurn(), file1, rank1, j, i, p,100))
                            moves[i][j] = j + "" + i;
                        else moves[i][j] = " ";

                }
            }
            displayPotentialMoves(moves);
        }
    }

    private void displayPotentialMoves(String[][] moves){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!moves[i][j].equals(" ")){
                    int file1 = Integer.parseInt(String.valueOf(moves[i][j].charAt(0)));
                    int rank1 = Integer.parseInt(String.valueOf(moves[i][j].charAt(1)));
                    //System.out.println(file1 + " " + rank1);
                    displayPotentialMoces(file1,rank1);
                }
            }
        }
    }

    private void displayPotentialMoces(int i, int j){
        ImageView tempView;
        if(i == 0 && j == 0){
            tempView = (ImageView)findViewById(R.id.a8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 0 && j == 1){
            tempView = (ImageView)findViewById(R.id.a7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 0 && j == 2){
            tempView = (ImageView)findViewById(R.id.a6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 0 && j == 3){
            tempView = (ImageView)findViewById(R.id.a5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 0 && j == 4){
            tempView = (ImageView)findViewById(R.id.a4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 0 && j == 5){
            tempView = (ImageView)findViewById(R.id.a3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 0 && j == 6){
            tempView = (ImageView)findViewById(R.id.a2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 0 && j == 7){
            tempView = (ImageView)findViewById(R.id.a1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 0){
            tempView = (ImageView)findViewById(R.id.b8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 1 && j == 1){
            tempView = (ImageView)findViewById(R.id.b7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 2){
            tempView = (ImageView)findViewById(R.id.b6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 3){
            tempView = (ImageView)findViewById(R.id.b5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 4){
            tempView = (ImageView)findViewById(R.id.b4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 5){
            tempView = (ImageView)findViewById(R.id.b3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 6){
            tempView = (ImageView)findViewById(R.id.b2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 1 && j == 7){
            tempView = (ImageView)findViewById(R.id.b1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 0){
            tempView = (ImageView)findViewById(R.id.c8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 2 && j == 1){
            tempView = (ImageView)findViewById(R.id.c7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 2){
            tempView = (ImageView)findViewById(R.id.c6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 3){
            tempView = (ImageView)findViewById(R.id.c5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 4){
            tempView = (ImageView)findViewById(R.id.c4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 5){
            tempView = (ImageView)findViewById(R.id.c3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 6){
            tempView = (ImageView)findViewById(R.id.c2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 2 && j == 7){
            tempView = (ImageView)findViewById(R.id.c1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 0){
            tempView = (ImageView)findViewById(R.id.d8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 3 && j == 1){
            tempView = (ImageView)findViewById(R.id.d7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 2){
            tempView = (ImageView)findViewById(R.id.d6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 3){
            tempView = (ImageView)findViewById(R.id.d5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 4){
            tempView = (ImageView)findViewById(R.id.d4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 5){
            tempView = (ImageView)findViewById(R.id.d3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 6){
            tempView = (ImageView)findViewById(R.id.d2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 3 && j == 7){
            tempView = (ImageView)findViewById(R.id.d1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 0){
            tempView = (ImageView)findViewById(R.id.e8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 4 && j == 1){
            tempView = (ImageView)findViewById(R.id.e7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 2){
            tempView = (ImageView)findViewById(R.id.e6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 3){
            tempView = (ImageView)findViewById(R.id.e5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 4){
            tempView = (ImageView)findViewById(R.id.e4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 5){
            tempView = (ImageView)findViewById(R.id.e3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 6){
            tempView = (ImageView)findViewById(R.id.e2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 4 && j == 7){
            tempView = (ImageView)findViewById(R.id.e1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 0){
            tempView = (ImageView)findViewById(R.id.f8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 5 && j == 1){
            tempView = (ImageView)findViewById(R.id.f7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 2){
            tempView = (ImageView)findViewById(R.id.f6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 3){
            tempView = (ImageView)findViewById(R.id.f5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 4){
            tempView = (ImageView)findViewById(R.id.f4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 5){
            tempView = (ImageView)findViewById(R.id.f3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 6){
            tempView = (ImageView)findViewById(R.id.f2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 5 && j == 7){
            tempView = (ImageView)findViewById(R.id.f1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 0){
            tempView = (ImageView)findViewById(R.id.g8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 6 && j == 1){
            tempView = (ImageView)findViewById(R.id.g7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 2){
            tempView = (ImageView)findViewById(R.id.g6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 3){
            tempView = (ImageView)findViewById(R.id.g5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 4){
            tempView = (ImageView)findViewById(R.id.g4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 5){
            tempView = (ImageView)findViewById(R.id.g3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 6){
            tempView = (ImageView)findViewById(R.id.g2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 6 && j == 7){
            tempView = (ImageView)findViewById(R.id.g1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 0){
            tempView = (ImageView)findViewById(R.id.h8);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }else if(i == 7 && j == 1){
            tempView = (ImageView)findViewById(R.id.h7);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 2){
            tempView = (ImageView)findViewById(R.id.h6);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 3){
            tempView = (ImageView)findViewById(R.id.h5);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 4){
            tempView = (ImageView)findViewById(R.id.h4);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 5){
            tempView = (ImageView)findViewById(R.id.h3);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 6){
            tempView = (ImageView)findViewById(R.id.h2);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
        else if(i == 7 && j == 7){
            tempView = (ImageView)findViewById(R.id.h1);
            tempView.setColorFilter(Color.RED,PorterDuff.Mode.OVERLAY);
        }
    }

    private void randomCall(){
        char turn = game.getTurn();
        List<pieces> list = new ArrayList<pieces>();
       // List<String> location = new ArrayList<String>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                pieces p = board.get_piece_at(i,j);
                if(p != null){
                    if(p.color == turn){
                        list.add(p);
                        //location.add(i + "" + j);
                    }
                }
            }
        }

        if(list.size() <= 0)
            return;




        List<String> moves = new ArrayList<String>();
        pieces p;


        while(moves.size() == 0) {
            int index = (int) (Math.random() * list.size());
            p = list.get(index);
            if (p != null) {
                //System.out.println(p.getPieceType());
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (p.can_it_move(turn, p.filess, p.rankss, i, j, p, 1)) {
                            moves.add(i + "" + j);
                            //System.out.println(i + " +=+= " + j);
                        }
                    }
                }

                int random = (int) (Math.random() * moves.size());
                String position;
                String src = null, dest = null;
                if (moves.size() != 0) {
                    position = moves.get(random);
                    //System.out.println(position + " (((((((");
                    src = convert(p.filess) + "" + convertRank(p.rankss);
                    dest = convert(Integer.parseInt(String.valueOf(position.charAt(0)))) + "" + convertRank(Integer.parseInt(String.valueOf(position.charAt(1))));
                    //System.out.println(src + "----" + dest);
                    source = src;
                    destination = dest;
                    smd = 1;
                    makeMove();

                }
            }
        }
    }

    private String convert(int file){
        if(file == 0)
            return "a";
        if(file == 1)
            return "b";
        if(file == 2)
            return "c";
        if(file == 3)
            return "d";
        if(file == 4)
            return "e";
        if(file == 5)
            return "f";
        if(file == 6)
            return "g";
        else
            return "h";
    }

    private int convertRank(int rank){
        if(rank == 0)
            return 8;
        if(rank == 1)
            return 7;
        if(rank == 2)
            return 6;
        if(rank == 3)
            return 5;
        if(rank == 4)
            return 4;
        if(rank == 5)
            return 3;
        if(rank == 6)
            return 2;
        else return 1;
    }


    private void callUndo(){
        if(undoCount == 0 || counter > 0) {
            Toast.makeText(this, "Can't Undo", Toast.LENGTH_SHORT).show();
            return;
        }


        if(ppp != null){
            board.set_piece_at(ppp.rankss,ppp.filess,ppp);
            ppp.enpassant = true;
            board.enpassant = true;
        }

        pieces p = board.getTempPiece2();
        if(p == null)
            board.set_piece_back(b,a,d,c,board.getTempPiece(),null);
        else {
            board.set_piece_back(b, a, d, c, board.getTempPiece(), p);
        }
        savedMoves.remove(savedMoves.size()-1);
        update();
        game.change_turn();
        undoCount--;
        counter = 1;
        String turn = null;
        if(game.getTurn() == 'w')
            turn = "white";
        else turn = "black";
        TextView tv = (TextView)findViewById(R.id.printLabel);
        tv.setText(turn + "'s turn");
    }
}
