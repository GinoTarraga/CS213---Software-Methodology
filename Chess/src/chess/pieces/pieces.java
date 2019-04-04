// author Gino Tarraga, Kaival Patel
package chess.pieces;

import chess.board.board;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class pieces {
    public char color; 
    public char type; 
    public int  file;
    public int rank;
    public board board; 
    public int timesmoved = 0;
    public boolean promote;
    public boolean enpassant;


    public pieces(){}
    public pieces(char color, board board){
        this.board = board;
        this.color = color;
    }

    public String getPieceType(){
        return this.color + "" + this.type;
    }

    public char pieceType(){
        return this.type;
    }

    public boolean isPromote(){
        return this.promote;
    }

    public void setPromotionOff(){
        this.promote = false;
    }

    public boolean getenpassant(){
    	return this.enpassant;
    }

    
    public void setEnpassantoff(){
    	this.enpassant = false;
    }

    public void setTimesmoved(pieces piece){
        piece.timesmoved++;
    }

    public void resetTimesmoved(pieces piece){
        piece.timesmoved -= 1;
    }

    public int getTimesmoved(){
        return this.timesmoved;
    }

    public char get_color(){
        return this.color;
    }

    public boolean is_valid_input(String str){
        if(str.length() != 2) return false;

        if(str.charAt(0) >= 'a' && str.charAt(0) <= 'h')
            if(str.charAt(1) >= 1 && str.charAt(1) <= '8')
                return true;
        return false;
    }

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){
        return false;
    }


}
