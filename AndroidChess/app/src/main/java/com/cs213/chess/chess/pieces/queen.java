/**
 * @author Gino Tarraga & Kaival Patel
 */
package com.cs213.chess.chess.pieces;

import com.cs213.chess.chess.board.board;

/**
 * The queen is the most powerful piece.
 * Queen can move in any one straight direction - forward, backward, sideways, or diagonally -
 *      as far as possible as long as she does not move through any or her own pieces.
 *      And, like with all pieces, if the queen captures an opponent's piece her move is over.
 */
public class queen extends pieces{
    /**
     * default constructor
     * @param color color of the piece
     * @param board current board
     * @param rank rank of the piece
     * @param file file of the piece
     */
    public queen(char color, board board,int rank, int file){
        super(color, board);
        this.type = 'Q';
        this.rank = rank;
        this.file = file;
        this.rankss = rank;
        this.filess = file;
    }

    /**
     * checks if the queen can move
     * @param color color
     * @param file1 file1
     * @param rank1 rank1
     * @param file2 file2
     * @param rank2 rank2
     * @param piece piece
     * @return if the piece can move from file1,rank1 to file2 rank2
     */
    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece){

        if(file1 == file2 && rank1 == rank2) return false;


        if(file1 == file2){
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

        //checking for cross moves

        //moving in cross up right
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
        //moving in cross up left
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
        //moving in cross down right
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
        //moving in cross down left
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

    public boolean can_it_move(char color, int file1, int rank1, int file2, int rank2, pieces piece, int xu){

        if(file1 == file2 && rank1 == rank2) return false;


        if(file1 == file2){
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

        //checking for cross moves

        //moving in cross up right
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
        //moving in cross up left
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
        //moving in cross down right
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
        //moving in cross down left
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

    /**
     * move the piece
     */
    public void move(){
        return;
    }
}
