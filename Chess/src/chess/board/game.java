// author Gino Tarraga, Kaival Patel

package chess.board;

import chess.pieces.pieces;


public class game {

    public board board;
    private char turn;
    public boolean game_status;
    public boolean draw_request;

  
    public game(){
        this.set(new board());
        this.turn = 'w';
        this.game_status = true;
        this.draw_request = false;
    }

    public boolean draw_request(){
        draw_request = true;
        return draw_request;
    }

    public board getBoard(){
        return board;
    }

   
    public char getTurn(){
        return this.turn;
    }

    public void change_turn(){
        this.turn = (this.turn == 'w') ? 'b': 'w';
    }

    public boolean get_game_status(){
        return this.game_status;
    }

    public void set_game_status_false(){
        this.game_status = false;
    }

    public void set(board board){
        this.board = board;
    }

 
    public void winner(char color){
        if(color == 'w'){
            System.out.println("White wins");
        }else{
            System.out.println("Black wins");
        }
    }

    public void print_turn(char color){
        if(color == 'w'){
            System.out.print("\nWhite's move: ");
        }else{
            System.out.print("\nBlack's move: ");
        }
    }




}
