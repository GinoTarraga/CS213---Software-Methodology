// author Gino Tarraga, Kaival Patel
package chess.pieces;

import chess.board.board;


public class rook extends pieces{
 
    public rook(char color, board board,int rank, int file){
        super(color, board);
        this.type = 'R';
        this.rank = rank;
        this.file = file;
    }

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){

        if(file1 == file2 && rank1 == rank2) return false;

        if(file1 == file2){
            //moving up
            if(rank1 > rank2){
                for(int i = rank1 - 1; (i >= rank2 || i>=0) ; i--){
                    if(this.board.is_spot_open(i,file1)){
                        if(i == rank2)
                            return true;
                    }
                    else
                        return !this.board.is_spot_open(i, file1) && i == rank2 && this.board.get_color_from_spot(rank2, file2) != color;
                }
            }
            //moving down
            else if(rank1 < rank2){
                for(int i = rank1 + 1; (i <= rank2 || i<=7); i++){
                    if(this.board.is_spot_open(i,file1)){
                        if(i == rank2)
                            return true;
                    }
                    else
                        return !this.board.is_spot_open(i, file1) && i == rank2 && this.board.get_color_from_spot(rank2, file2) != color;
                }
            }
        }else if(rank1 == rank2){
            //moving left
            if(file1 > file2){
                for(int i = file1 - 1; (i >= file2 || i >=0); i--){
                    if(this.board.is_spot_open(rank1,i)){
                        if(i == file2)
                            return true;
                    }
                    else
                        return !this.board.is_spot_open(rank1, i) && i == file2 && this.board.get_color_from_spot(rank2, file2) != color;
                }
            }
            //moving right
            else if(file1 < file2){
                for(int i = file1 + 1; (i <= file2 || i <=7) ; i++){
                    if(this.board.is_spot_open(rank1,i)){
                        if(i == file2)
                            return true;
                    }
                    else
                        return !this.board.is_spot_open(rank1, i) && i == file2 && this.board.get_color_from_spot(rank2, file2) != color;
                }
            }
        }

        return false;
    }

    public void move(){
        return;
    }
}
