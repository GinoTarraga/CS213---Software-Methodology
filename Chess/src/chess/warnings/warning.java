// author Gino Tarraga, Kaival Patel
package chess.warnings;

public class warning {
  
    public warning(){}

    public void Illegal_move(){
        System.out.println("\nIllegal move, please try again\n");
        return;
    }

    public void check_mate(){
        System.out.println("\nCheckmate\n");
        return;
    }

    public void stale_mate(){
        System.out.println("\nStalemate\n");
        return;
    }

    public void not_ur_piece(){
        System.out.println("\nYou cannot move opponent's move, try again\n");
    }

    public void draw_game(){
        System.out.println("\ngame draw\n");
    }

    public void nice_move(char ch){
        switch (ch){
            case 'p':
                System.out.println("\nnice!, you took out opponent's pawn\n");
                break;
            case 'Q':
                System.out.println("\nnice!, you took out opponent's queen\n");
                break;
            case 'N':
                System.out.println("\nnice!, you took out opponent's Knight\n");
                break;
            case 'B':
                System.out.println("\nnice!, you took out opponent's bishop\n");
                break;
            case 'R':
                System.out.println("\nnice!, you took out opponent's rook\n");
                break;
            case 'K':
                System.out.println("\nnice!, you took out opponent's king and took the game\n");
        }
    }

    public void check(){
        System.out.println("\ncheck!\n");
    }

    public void kingInTrouble(){
        System.out.println("Doing this move puts your king in check, try again");
    }
   
    public void no_piece_there(){
        System.out.println("\nThere is no piece there to move, try again\n");
    }
    
    public void not_valid_input(){
        System.out.println("\nThis is not a valid input, try again\n");
    }

}
