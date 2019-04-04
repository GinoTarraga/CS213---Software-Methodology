/**
 * @author Gino Tarraga & Kaival Patel
 */
package com.cs213.chess.chess.pieces;

import android.support.v7.app.AppCompatActivity;

import com.cs213.chess.chess.board.board;

public class pieces{
    int files = 0;
    int ranks = 0;
    public char color; //color of the piece
    public char type; //what type of piece is it? (rook, knight, king, etc..)
    public int  file;
    public int rank;
    public board board; //board
    public int timesmoved = 0;
    public boolean promote;
    public boolean enpassant;
    public int rankss;
    public int filess;

    public void setFiles(int file){
        this.files = file;
    }
    public void setRanks(int rank){
        this.ranks = rank;
    }

    public int getfiles(){
        return files;
    }

    public int getranks(){
        return ranks;
    }

    /**
     * default constructors
     */
    public pieces(){}
    public pieces(char color, board board){
        this.board = board;
        this.color = color;
    }


    /**
     * returns the current piece with color
     * @return piece type with color (i.e. wB, bB, wK, etc.)
     */
    public String getPieceType(){
        return this.color + "" + this.type;
    }

    /**
     * returns the piece type only
     * @return piece type only
     */
    public char pieceType(){
        return this.type;
    }

    /**
     * checks if pawn can be promoted or not
     * @return if the pawn can be promoted
     */
    public boolean isPromote(){
        return this.promote;
    }

    /**
     * sets the pawn promotion off
     */
    public void setPromotionOff(){
        this.promote = false;
    }

    /**
     * checks if enpassant or not
     * @return enpassant
     */
    public boolean getenpassant(){return this.enpassant;}

    /**
     * turns the enpassant off
     */
    public void setEnpassantoff(){this.enpassant = false;}

    /**
     * sets the how many times a piece has moved
     * @param piece current piece
     */
    public void setTimesmoved(pieces piece){
        piece.timesmoved++;
    }

    /**
     * resets the times moves count if hace to move it back
     * @param piece current piece
     */
    public void resetTimesmoved(pieces piece){
        piece.timesmoved -= 1;
    }

    /**
     * returns the number of times a piece has moved
     * @return return how many times a piece has moved
     */
    public int getTimesmoved(){
        return this.timesmoved;
    }

    /**
     * gets the color of the current piece
     * @return the color of the piece
     */
    public char get_color(){
        return this.color;
    }


    /**
     * checks for valid input
     * @param str input
     * @return returns if valid input
     */
    public boolean is_valid_input(String str){
        if(str.length() != 2) return false;

        if(str.charAt(0) >= 'a' && str.charAt(0) <= 'h')
            if(str.charAt(1) >= 1 && str.charAt(1) <= '8')
                return true;
        return false;
    }

    /**
     * default can it move checker which returns false
     * @param color color
     * @param file1 file1
     * @param rank1 rank1
     * @param file2 file2
     * @param rank2 rank2
     * @param piece piece
     * @return
     */
    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){
        return false;
    }
    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece, int xu){
        return false;
    }


}
