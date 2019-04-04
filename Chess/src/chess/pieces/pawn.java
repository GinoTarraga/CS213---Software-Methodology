// author Gino Tarraga, Kaival Patel
package chess.pieces;
import chess.board.*;

public class pawn extends pieces{
   
    public pawn(char color, board board,int rank,int file){
        super(color, board);
        this.type = 'p';
        this.promote = false;
        this.enpassant = false;
        this.rank = rank;
        this.file = file;
    }

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){

        if(file1 == file2 && rank1 == rank2) return false;


        //if color is white
        if(color == 'w'){

            //if enpassant is true
            if(this.board.isEnpassant()){
                this.board.SetEnpassantOff();
                if(rank1 == 3 && file1 == 0){
                    pieces p1 = this.board.get_piece_at(rank1,(file1+1));
                    if(p1 != null && p1.get_color() == 'b' && p1.pieceType() == 'p' && rank2 == rank1-1 && file2 == file1+1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(3,1);
                        return true;
                    }
                }
                else if(rank1 == 3 && file1 == 7){
                    pieces p1 = this.board.get_piece_at(rank1,(file1-1));
                    if(p1 != null && p1.get_color() == 'b' && p1.pieceType() == 'p' && rank2 == rank1-1 && file2 == file1-1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(3,6);
                        return true;
                    }
                }
                else if(rank1 == 3){
                    pieces p1 = this.board.get_piece_at(rank1, (file1+1));
                    pieces p2 = this.board.get_piece_at(rank1, (file1-1));
                    if(p1 != null && p1.get_color() == 'b' && p1.pieceType() == 'p' && rank2 == rank1-1 && file2 == file1+1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(rank1,file1+1);
                        return true;
                    }
                    else if(p2 != null && p2.get_color() == 'b' && p2.pieceType() == 'p' && rank2 == rank1-1 && file2 == file1-1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(rank1,file1-1);
                        return true;
                    }
                }
            }

           
            if(piece.getTimesmoved() == 0){
                if(file1 == file2 && rank2 == (rank1 - 2) && this.board.is_spot_open(rank2,file2) && this.board.is_spot_open((rank1 -1), file2)){
                    this.enpassant = true;
                    return true;
                }
            }
            //otherwise moves only one step up
            if(file1 == file2 && rank2 == (rank1 - 1) && this.board.is_spot_open(rank2,file2)){
                if(rank1 == 1 && rank2 == 0){
                    this.promote = true;
                }
                return true;
            }

            //can move one step diagonally left if taking out an opponent
            if(file1 == (file2 - 1) && rank2 == (rank1 - 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) == 'b'){
                if(rank1 == 1 && rank2 == 0){
                    this.promote = true;
                }
                return true;
            }

            //can move one step diagonally right if taking out an opponent
            if(file1 == (file2 + 1) && rank2 == (rank1 - 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) == 'b'){
                if(rank1 == 1 && rank2 == 0){
                    this.promote = true;
                }
                return true;
            }
        }

        //if color is black
        else if(color == 'b'){

            //if enpassant is true
            if(this.board.isEnpassant()){
                this.board.SetEnpassantOff();
                if(rank1 == 4 && file1 == 0){
                    pieces p1 = this.board.get_piece_at(rank1,(file1+1));
                    if(p1 != null && p1.get_color() == 'w' && p1.pieceType() == 'p' && rank2 == rank1+1 && file2 == file1+1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(4,1);
                        return true;
                    }
                }
                else if(rank1 == 4 && file1 == 7){
                    pieces p1 = this.board.get_piece_at(rank1,(file1-1));
                    if(p1 != null && p1.get_color() == 'w' && p1.pieceType() == 'p' && rank2 == rank1+1 && file2 == file1-1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(4,6);
                        return true;
                    }
                }
                else if(rank1 == 4){
                    pieces p1 = this.board.get_piece_at(rank1, (file1+1));
                    pieces p2 = this.board.get_piece_at(rank1, (file1-1));
                    if(p1 != null && p1.get_color() == 'w' && p1.pieceType() == 'p' && rank2 == rank1+1 && file2 == file1+1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(rank1,file1+1);
                        return true;
                    }
                    else if(p2 != null && p2.get_color() == 'w' && p2.pieceType() == 'p' && rank2 == rank1+1 && file2 == file1-1 && this.board.is_spot_open(rank2,file2)){
                        this.board.set_piece_null(rank1,file1-1);
                        return true;
                    }
                }
            }

            //if moving for the first time it can move two steps
            if(piece.getTimesmoved() == 0){
                if(file1 == file2 && rank2 == (rank1 + 2) && this.board.is_spot_open(rank2,file2) && this.board.is_spot_open((rank1 + 1),file2)){
                    this.enpassant = true;
                    return true;
                }
            }

            //otherwise moves only one step up
            if(file1 == file2 && rank2 == (rank1+1) && this.board.is_spot_open(rank2,file2)){
                if(rank1 == 6 && rank2 == 7){
                    this.promote = true;
                }
                return true;
            }

           
            if(file1 == (file2 + 1) && rank2 == (rank1 + 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) == 'w') {
                if(rank1 == 6 && rank2 == 7){
                    this.promote = true;
                }
                return true;
            }
            //can move one step diagonally right if taking out an opponent
            if(file1 == (file2 - 1) && rank2 == (rank1 + 1) && !this.board.is_spot_open(rank2,file2) && this.board.get_color_from_spot(rank2,file2) == 'w') {
                if(rank1 == 6 && rank2 == 7){
                    this.promote = true;
                }
                return true;
            }
        }

        return false;
    }

}
