/**
 * @author Gino Tarraga & Kaival Patel
 */
package com.cs213.chess.chess.warnings;

/**
 * This class will print out all the warnings
 */
public class warning {
    /**
     * default warning constructor
     */
    public warning(){}

    //print out warnings

    /**
     * illegale move warning
     */
    public void Illegal_move(){
        //System.out.println("\nIllegal move, please try again\n");
        return;
    }

    /**
     * check mate warning
     */
    public void check_mate(){
        //System.out.println("\nCheckmate\n");
        return;
    }

    /**
     * stalemate warning
     */
    public void stale_mate(){
        //System.out.println("\nStalemate\n");
        return;
    }

    /**
     * can't move opponents piece warning
     */
    public void not_ur_piece(){
        //System.out.println("\nYou cannot move opponent's move, try again\n");
    }

    /**
     * draw game
     */
    public void draw_game(){
        System.out.println("\ngame draw\n");
    }

    /**
     * prints out a message that you took out an opponent's piece
     * @param ch
     */
    public void nice_move(char ch){
//        switch (ch){
//            case 'p':
//                System.out.println("\nnice!, you took out opponent's pawn\n");
//                break;
//            case 'Q':
//                System.out.println("\nnice!, you took out opponent's queen\n");
//                break;
//            case 'N':
//                System.out.println("\nnice!, you took out opponent's Knight\n");
//                break;
//            case 'B':
//                System.out.println("\nnice!, you took out opponent's bishop\n");
//                break;
//            case 'R':
//                System.out.println("\nnice!, you took out opponent's rook\n");
//                break;
//            case 'K':
//                System.out.println("\nnice!, you took out opponent's king and took the game\n");
//        }
    }

    /**
     * prints out check warning
     */
    public void check(){

        //System.out.println("\ncheck!\n");
    }

    /**
     * prints out a warning if moving a piece puts player's king in check
     */
    public void kingInTrouble(){
        //System.out.println("Doing this move puts your king in check, try again");
    }
    /**
     * if there is no piece to move
     */
    public void no_piece_there(){
        //System.out.println("\nThere is no piece there to move, try again\n");
    }
    /**
     * not valid entry warning
     */
    public void not_valid_input(){
        //System.out.println("\nThis is not a valid input, try again\n");
    }



}
