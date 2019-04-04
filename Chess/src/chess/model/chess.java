// author Gino Tarraga, Kaival Patel

package chess.model;
import chess.board.board;
import chess.board.game;
import chess.pieces.pieces;
import chess.warnings.warning;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class chess {
   
    public static void main(String[] args){
       
        game game = new game();
        board board = game.getBoard();
        warning warning = new warning();
        pieces pieces = new pieces();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        
        System.out.println(board);
        
        String line = "";
        String[] token;

       
        while(game.get_game_status()){

           
            game.print_turn(game.getTurn());

           
            try{
                line = reader.readLine();
                if(line == null)
                    continue;
            }catch (IOException ignored){}
            System.out.println();

           
            token = line.split(" ");

           
            if(game.draw_request){
                if(line.equalsIgnoreCase("draw")){
                    warning.draw_game();
                    game.game_status = false;
                    continue;
                }
            }

           
            game.draw_request = false;
           
            if(line.equals("resign")){
                game.change_turn();
                game.winner(game.getTurn());
                game.game_status = false;
                continue;
            }

            
            if(token.length < 2 || token.length >= 4){
                    warning.not_valid_input();
                    continue;
            }

           
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
                
                if(token[0].equalsIgnoreCase(token[1])){
                    warning.Illegal_move();
                    continue;
                }

                
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

          
            if(board.game_status() || board.isCheckmate() || board.isStalemate()){
                if(board.isCheckmate()){
                    warning.check_mate();
                }
                if(board.isStalemate())
                    warning.stale_mate();
                game.game_status = false;
                game.winner(game.getTurn());
            }

            
            game.change_turn();
        }
    }
}
