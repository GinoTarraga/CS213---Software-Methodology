// author Gino Tarraga, Kaival Patel

package chess.pieces;

import chess.board.board;


public class bishop extends pieces{

    public bishop(char color, board board, int rank, int file){
        super(color, board);
        this.type = 'B';
        this.rank = rank;
        this.file = file;
    }

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){

        if(file1 == file2 && rank1 == rank2) return false;

       
        if(file2 > file1 && rank1 > rank2){
            for(int i = file1 + 1,j = rank1 - 1; (i <= file2 || i <=7);  i++, j--){
                if(this.board.is_spot_open(j,i)){
                    if(i == file2 && j == rank2){
                        return true;
                    }
                }
                else
                    return !this.board.is_spot_open(j, i) && (i == file2 && j == rank2) && this.board.get_color_from_spot(rank2, file2) != color;
            }
        }
       
        else if(file1 > file2 && rank1 > rank2){
            for(int i = file1 - 1,j = rank1 - 1; (i >= file2 || i >=0) ;  i--, j--){
                if(this.board.is_spot_open(j,i)){
                    if(i == file2 && j == rank2){
                        return true;
                    }
                }
                else
                    return !this.board.is_spot_open(j, i) && (i == file2 && j == rank2) && this.board.get_color_from_spot(rank2, file2) != color;
            }
        }
       
        else if(file2 > file1 && rank1 < rank2){
            for(int i = file1 + 1,j = rank1 + 1; (i <= file2 || i <= 7);  i++, j++){
                if(this.board.is_spot_open(j,i)){
                    if(i == file2 && j == rank2){
                        return true;
                    }
                }
                else
                    return !this.board.is_spot_open(j, i) && (i == file2 && j == rank2) && this.board.get_color_from_spot(rank2, file2) != color;
            }
        }
        
        else if(file1 > file2 && rank1 < rank2){
            for(int i = file1 - 1,j = rank1 + 1; (i >= file2 || i >=0) ;  i--, j++){
                if(this.board.is_spot_open(j,i)){
                    if(i == file2 && j == rank2){
                        return true;
                    }
                }
                else
                    return !this.board.is_spot_open(j, i) && (i == file2 && j == rank2) && this.board.get_color_from_spot(rank2, file2) != color;
            }
        }

        return false;
    }

    public void move(){
        return;
    }

}
