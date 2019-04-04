/**
 * @author Gino Tarraga & Kaival Patel 
 */

package com.cs213.chess.chess.board;

import com.cs213.chess.chess.pieces.pieces;


public class game {

    public board board;
    private char turn;
    public boolean game_status;
    public boolean draw_request;

    /**
     * default constructor
     */
    public game(){
        this.set(new board());
        this.turn = 'w';
        this.game_status = true;
        this.draw_request = false;
    }

    /**
     * checks if a there is a draw request
     * @return the game draw request
     */
    public boolean draw_request(){
        draw_request = true;
        return draw_request;
    }

    /**
     * return the current board
     * @return the board
     */
    public board getBoard(){
        return board;
    }

    /**
     * returns who's turn it is
     * @return the current players turn
     */
    public char getTurn(){
        return this.turn;
    }

    /**
     * changes the players turn
     */
    public void change_turn(){
        this.turn = (this.turn == 'w') ? 'b': 'w';
    }

    /**
     * gets the current game status
     * @return the game status
     */
    public boolean get_game_status(){
        return this.game_status;
    }

    /**
     * sets the game status to false, hense it will end the game
     */
    public void set_game_status_false(){
        this.game_status = false;
    }

    /**
     * sets the current board
     * @param board board
     */
    public void set(board board){
        this.board = board;
    }

    /**
     * prints out the winner
     * @param color color
     */
    public void winner(char color){
        if(color == 'w'){
            System.out.println("White wins");
        }else{
            System.out.println("Black wins");
        }
    }

    /**
     * prints out the who's turn it is
     * @param color color
     */
    public void print_turn(char color){
        if(color == 'w'){
            System.out.print("\nWhite's move: ");
        }else{
            System.out.print("\nBlack's move: ");
        }
    }




}
