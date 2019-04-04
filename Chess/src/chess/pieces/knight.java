// author Gino Tarraga, Kaival Patel
package chess.pieces;

import chess.board.board;


public class knight extends pieces{
   
    public knight(char color, board board,int rank, int file){
        super(color, board);
        this.type = 'N';
        this.rank = rank;
        this.file = file;
    }

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){

        if(file1 == file2 && rank1 == rank2) return false;


        if(rank1 == 0 && file1 == 0 ){
            if((((rank2 == 1 && file2 == 2) || (rank2 == 2 && file2 == 1)) && (this.board.is_spot_open(rank2,file2)))
                || (((rank2 == 1 && file2 == 2) || (rank2 == 2 && file2 == 1)) && !this.board.is_spot_open(rank2,file2)
                    && this.board.get_color_from_spot(rank2,file2) != color)) return true;
        }
        //top right box
        else if(rank1 == 0 && file1 == 7){
            if((((rank2 == 1 && file2 == 5) || (rank2 == 2 && file2 == 6)) && (this.board.is_spot_open(rank2,file2)))
                    || (((rank2 == 1 && file2 == 5) || (rank2 == 2 && file2 == 6)) && !this.board.is_spot_open(rank2,file2)
                    && this.board.get_color_from_spot(rank2,file2) != color)) return true;
        }
        //bottom right
        else if(rank1 == 7 && file1 == 7){
            if((((rank2 == 6 && file2 == 5) || (rank2 == 5 && file2 == 6)) && (this.board.is_spot_open(rank2,file2)))
                    || (((rank2 == 6 && file2 == 5) || (rank2 == 5 && file2 == 6)) && !this.board.is_spot_open(rank2,file2)
                    && this.board.get_color_from_spot(rank2,file2) != color)) return true;
        }
        //bottom left
        else if(rank1 == 7 && file1 == 0){
            if((((rank2 == 5 && file2 == 1) || (rank2 == 6 && file2 == 2)) && (this.board.is_spot_open(rank2,file2)))
                    || (((rank2 == 5 && file2 == 1) || (rank2 == 6 && file2 == 2)) && !this.board.is_spot_open(rank2,file2)
                    && this.board.get_color_from_spot(rank2,file2) != color)) return true;
        }
        //top row col 2
        else if(rank1 == 0 && file1 == 1){
            if((rank2 == rank1+2 && file2 == file1-1) || (rank2 == rank1+2 && file2 == file1+1) || (rank2 == rank1+1 && file2 == file1+2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //top row col 7
        else if(rank1 == 0 && file1 == 6){
            if((rank2 == rank1+2 && file2 == file1-1) || (rank2 == rank1+2 && file2 == file1+1) || (rank2 == rank1+1 && file2 == file1-2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //bottom row col 2
        else if(rank1 == 7 && file1 == 1){
            if((rank2 == rank1-2 && file2 == file1-1) || (rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1-1 && file2 == file1+2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //bottom row col 7
        else if(rank1 == 7 && file1 == 6){
            if((rank2 == rank1-2 && file2 == file1-1) || (rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1-1 && file2 == file1-2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //2nd row col 1
        else if(rank1 == 1 && file1 == 0){
            if((rank2 == rank1-1 && file2 == file1+2) || (rank2 == rank1+1 && file2 == file1+2) || (rank2 == rank1+2 && file2 == file1+1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //2nd row col 8
        else if(rank1 == 1 && file1 == 7){
            if((rank2 == rank1-2 && file2 == file1-1) || (rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1+2 && file2 == file1-1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //7th row col1
        else if(rank1 == 6 && file1 == 0){
            if((rank2 == rank1+2 && file2 == file1-1) || (rank2 == rank1+2 && file2 == file1+1) || (rank2 == rank1-2 && file2 == file1+1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //7th row col8
        else if(rank1 == 6 && file1 == 7){
            if((rank2 == rank1-2 && file2 == file1-1) || (rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1-2 && file2 == file1-1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //row 2 col 2
        else if(rank1 == 1 && file1 == 1){
            if((rank2 == rank1+1 && file2 == file1+2) || (rank2 == rank1-1 && file2 == file1+2)
                    || (rank2 == rank1+2 && file2 == file1+1) || (rank2 == rank1+2 && file2 == file1-1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //row 2 col 7
        else if(rank1 == 1 && file1 == 6){
            if((rank2 == rank1+1 && file2 == file1-2) || (rank2 == rank1-1 && file2 == file1-2)
                    || (rank2 == rank1+2 && file2 == file1-1) || (rank2 == rank1+2 && file2 == file1+1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //row 7 col 2
        else if(rank1 == 6 && file1 == 1){
            if((rank2 == rank1+1 && file2 == file1+2) || (rank2 == rank1-1 && file2 == file1+2)
                    || (rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1-2 && file2 == file1-1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //row 7 col 7
        else if(rank1 == 6 && file1 == 6){
            if((rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1-2 && file2 == file1-1)
                    || (rank2 == rank1-1 && file2 == file1-2) || (rank2 == rank1+1 && file2 == file1-2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //remaining top row
        else if(rank1 == 0){
            if((rank2 == rank1+2 && file2 == file1+1) || (rank2 == rank1+2 && file2 == file1-1)
                    || (rank2 == rank1+2 && file2 == file1-1) || (rank2 == rank1+2 && file2 == file1+1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //bottom right row
        else if(rank1 == 7){
            if((rank2 == rank1-2 && file2 == file1-1) || (rank2 == rank1-2 && file2 == file1+1)
                    || (rank2 == rank1-1 && file2 == file1-2) || (rank2 == rank1-1 && file2 == file1+2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //left col
        else if(file1 == 0){
            if((rank2 == rank1-2 && file2 == file1+1) || (rank2 == rank1+2 && file2 == file1+1)
                    || (rank2 == rank1+1 && file2 == file1+2) || (rank2 == rank1-1 && file2 == file1+2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //right col
        else if(file1 == 7){
            if((rank2 == rank1-2 && file2 == file1-1) || (rank2 == rank1+2 && file2 == file1-1)
                    || (rank2 == rank1+1 && file2 == file1-2) || (rank2 == rank1-1 && file2 == file1-2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //rest of 2nd row
        else if(rank1 == 1){
            if((rank2 == rank1+2 && file2 == file1 + 1) || (rank2 == rank1+2 && file2 == file1 - 1)
                    || (rank2 == rank1 + 1 && file2 == file1 + 2) || (rank2 == rank1 -1 && file2 == file1 + 2)
                    || (rank2 == rank1 + 1 && file2 == file1 - 2) || (rank2 == rank1 -1 && file2 == file1 - 2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //2nd last row
        else if(rank1 == 6){
            if((rank2 == rank1-2 && file2 == file1 + 1) || (rank2 == rank1-2 && file2 == file1 - 1)
                    || (rank2 == rank1 + 1 && file2 == file1 + 2) || (rank2 == rank1 -1 && file2 == file1 + 2)
                    || (rank2 == rank1 + 1 && file2 == file1 - 2) || (rank2 == rank1 -1 && file2 == file1 - 2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //2nd col
        else if(file1 == 1){
            if((rank2 == rank1+2 && file2 == file1 + 1) || (rank2 == rank1+2 && file2 == file1 - 1)
                    || (rank2 == rank1 + 1 && file2 == file1 + 2) || (rank2 == rank1 -1 && file2 == file1 + 2)
                    || (rank2 == rank1 - 2 && file2 == file1 + 1) || (rank2 == rank1 -2 && file2 == file1 - 1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //2nd last col
        else if(file1 == 6){
            if((rank2 == rank1+2 && file2 == file1 + 1) || (rank2 == rank1+2 && file2 == file1 - 1)
                    || (rank2 == rank1 + 1 && file2 == file1 - 2) || (rank2 == rank1 -1 && file2 == file1 - 2)
                    || (rank2 == rank1 - 2 && file2 == file1 + 1) || (rank2 == rank1 -2 && file2 == file1 - 1)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }
        //in middle
        else if((rank1 >= 2 && rank1 <= 5) && (file1 >= 2 && file1 <= 5)){
            if((rank2 == rank1 -2 && file2 == file1 + 1) || (rank2 == rank1-2 && file2 == file1-1)
                    || (rank2 == rank1-1 && file2 == file1+2) || (rank2 == rank1+1 && file2 == file1+2)
                    || (rank2 == rank1+2 && file2 == file1+1) || (rank2 == rank1+2 && file2 == file1-1)
                    || (rank2 == rank1+1 && file2 == file1-2) || (rank2 == rank1-1 && file2 == file1-2)){
                if(this.board.is_spot_open(rank2,file2) || (!this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) != color))
                    return true;
            }
        }

        return false;

    }

    public void move(){
        return;
    }
}
