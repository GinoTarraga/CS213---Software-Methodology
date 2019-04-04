/**
 * @author Gino Tarraga & Kaival Patel
 */

package com.cs213.chess.chess.model;
import com.cs213.chess.chess.board.board;
import com.cs213.chess.chess.board.game;
import com.cs213.chess.chess.pieces.pieces;
import com.cs213.chess.chess.warnings.warning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class chess {
    /**
     * main class
     * @param args reads arguments from command lines
     */
    public static void main(String[] args){
        //creates new game, board, warning, reader, and pieces objects
        game game = new game();
        board board = game.getBoard();
        warning warning = new warning();
        pieces pieces = new pieces();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //prints out the initial board
        System.out.println(board);
        //line to store the user input, and token array to split uer input
        String line = "";
        String[] token;

        //while game is active play the game
        while(game.get_game_status()){

            //prints out who's turn it is
            game.print_turn(game.getTurn());

            //reads in a line from user, if null try again
            try{
                line = reader.readLine();
                if(line == null)
                    continue;
            }catch (IOException ignored){}
            System.out.println();

            //splits the line into tokens
            token = line.split(" ");

            //if there was a draw request in previous move then check if the current player wants draw or not
            //if yes end the game and print out draw
            if(game.draw_request){
                if(line.equalsIgnoreCase("draw")){
                    warning.draw_game();
                    game.game_status = false;
                    continue;
                }
            }

            //draw game request denied
            game.draw_request = false;
            //check if the player enters resign
            //if yes end the game the other player wins
            if(line.equals("resign")){
                game.change_turn();
                game.winner(game.getTurn());
                game.game_status = false;
                continue;
            }

            //if its not a valid input print error and try again
            if(token.length < 2 || token.length >= 4){
                    warning.not_valid_input();
                    continue;
            }

            //check if the user asked for draw
            else if(token.length == 3){
                if(token[2].equalsIgnoreCase("draw?")){
                    if(board.move(game.getTurn(),token[0], token[1], null)) {
                        System.out.println(board);
                        if(board.isCheckChecker()){
                            board.checkOff();
                            warning.check();
                        }
                        if(board.isDraw()){
                            warning.draw_game();
                            game.game_status = false;
                            continue;
                        }
                    }else{
                        if(board.isStalemate()){
                            game.change_turn();
                            game.winner(game.getTurn());
                            warning.stale_mate();
                            game.game_status = false;
                        }
                        continue;
                    }
                    game.draw_request();
                    game.change_turn();
                    continue;
                    //System.out.println("do something");
                }
                else if(token[2].equals("N") || token[2].equals("Q") || token[2].equals("B") || token[2].equals("R")){
                    if(board.move(game.getTurn(),token[0], token[1], token[2])) {
                        System.out.println(board);
                    }
                }
                else{
                    warning.not_valid_input();
                    continue;
                }
            }

            else{
                //if source and destination is the same then try again
                if(token[0].equalsIgnoreCase(token[1])){
                    warning.Illegal_move();
                    continue;
                }

                //if valid input then move the piece or print a warning
                if(pieces.is_valid_input(token[0]) && pieces.is_valid_input(token[1])){
                    if(board.move(game.getTurn(),token[0], token[1],null)) {
                        System.out.println(board);
                        if(board.isCheckChecker()){
                            board.checkOff();
                            warning.check();
                        }
                        if(board.isDraw()){
                            warning.draw_game();
                            game.game_status = false;
                            continue;
                        }
                    }else{
                        if(board.isStalemate()){
                            warning.stale_mate();
                            game.change_turn();
                            game.winner(game.getTurn());
                            game.game_status = false;
                        }
                        continue;
                    }
                }else{
                    warning.not_valid_input();
                    continue;
                }
            }

            //check to see if the king has be taken out or a checkmate
            if(board.game_status() || board.isCheckmate() || board.isStalemate()){
                if(board.isCheckmate()){
                    warning.check_mate();
                }
                if(board.isStalemate())
                    warning.stale_mate();
                game.game_status = false;
                game.winner(game.getTurn());
            }

            //change the player's turn
            game.change_turn();
        }
    }
}
